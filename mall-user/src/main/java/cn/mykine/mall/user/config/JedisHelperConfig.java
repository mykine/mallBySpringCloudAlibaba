package cn.mykine.mall.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JedisHelperConfig {

    @Value("${redis.cluster.hostports}")
    public String redisClusterHostPorts;

    @Value("${redis.cluster.passwrod}")
    public String passwrod;

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public JedisHelper createJedisHelper(){
        return new JedisHelper(redisClusterHostPorts,passwrod);
    }

}
