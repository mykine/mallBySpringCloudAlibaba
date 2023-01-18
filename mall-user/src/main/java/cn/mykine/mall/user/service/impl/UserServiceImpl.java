package cn.mykine.mall.user.service.impl;


import cn.mykine.mall.common.dto.UserDTO;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.user.mapper.UserMapper;
import cn.mykine.mall.user.model.UserDO;
import cn.mykine.mall.user.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDTO getUser(Long id) {
        UserDO userDO = userMapper.selectUserById(id);
        Assert.notNull(userDO);
        return ObjectTransformer.transform(userDO, UserDTO.class);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        UserDO userDO = ObjectTransformer.transform(userDTO, UserDO.class);
        int result = userMapper.updateUser(userDO);
        return Assert.singleRowAffected(result);
    }
}
