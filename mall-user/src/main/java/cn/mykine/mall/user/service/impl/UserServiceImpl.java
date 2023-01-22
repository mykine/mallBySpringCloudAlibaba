package cn.mykine.mall.user.service.impl;


import cn.mykine.mall.common.dto.UserDTO;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.JSONUtil;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.user.config.JedisHelper;
import cn.mykine.mall.user.mapper.UserMapper;
import cn.mykine.mall.user.model.UserDO;
import cn.mykine.mall.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    UserMapper userMapper;

    @Autowired
    JedisHelper jedisHelper;

//    @Override
//    public UserDTO getUser(Long id) {
//        String redisKey = "user:"+id;
//
//        String userCache = jedisHelper.get(redisKey);
//        if( userCache != null ){
//            UserDTO result = JSONUtil.parseObject(userCache,UserDTO.class);
//            return result;
//        }
//
//        UserDO userDO = userMapper.selectUserById(id);
//        Assert.notNull(userDO);
//        UserDTO result = ObjectTransformer.transform(userDO, UserDTO.class);
//        jedisHelper.set(redisKey,JSONUtil.toJSONString(result),300);
//        return result;
//    }

    @Cacheable(cacheNames = "user:",key = "#id")//缓存没有时才会更新
//    @CacheEvict(cacheNames = "user:",key = "#id")//先删除缓存再更新缓存
//    @CachePut(cacheNames = "user:",key = "#id")//缓存无论有没有都会更新
    @Override
    public UserDTO getUser(Long id) {
        UserDO userDO = userMapper.selectUserById(id);
        Assert.notNull(userDO);
        UserDTO result = ObjectTransformer.transform(userDO, UserDTO.class);
        return result;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        UserDO userDO = ObjectTransformer.transform(userDTO, UserDO.class);
        int result = userMapper.updateUser(userDO);
        return Assert.singleRowAffected(result);
    }
}
