package cn.mykine.mall.user.config;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.HashSet;
import java.util.Set;

/**
 * 封装基于Jedis客户端操作redis的助手类
 * */
@Slf4j
public class JedisHelper {

    private JedisCluster jedis;

    public JedisHelper(String hostAndPorts,String password){
        log.info("JedisHelper构造函数执行!!! hostAndPorts={},password={}",hostAndPorts,password);
        if( null != jedis ){
            return;
        }
        String[] hostPortArr = hostAndPorts.split(",");
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();

        for (String item:hostPortArr
             ) {
            String[] hostAndPort = item.split(":");
            jedisClusterNodes.add(new HostAndPort(hostAndPort[0], Integer.valueOf(hostAndPort[1])));
        }
        jedis = new JedisCluster(jedisClusterNodes, null, password);
    }

    public void init(){
        log.info("JedisHelper执行init方法!!!");
    }

    public void destroy(){
        log.info("JedisHelper执行destroy方法!!!");
        if( null != jedis ){
            jedis.close();
        }
    }

    public String get(String key){
       return jedis.get(key);
    }

    public void set(String key,String value,int expiredTime){
        SetParams p = new SetParams();
        p.ex(expiredTime);
        jedis.set(key,value,p);
    }

}
