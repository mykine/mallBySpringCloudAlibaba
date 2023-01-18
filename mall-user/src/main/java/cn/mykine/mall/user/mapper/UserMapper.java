package cn.mykine.mall.user.mapper;

import cn.mykine.mall.user.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户ID查询用户信息
     *
     * @param id
     * @return
     */
    UserDO selectUserById(Long id);

    /**
     * 更新用户信息
     * 
     * @param userInfo
     * @return
     */
    int updateUser(UserDO userInfo);
}
