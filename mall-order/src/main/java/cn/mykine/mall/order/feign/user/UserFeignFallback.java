package cn.mykine.mall.order.feign.user;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.UserDTO;
import cn.mykine.mall.common.exception.BusinessException;

public class UserFeignFallback implements UserFeign{


    @Override
    public BaseResponse<UserDTO> getUser(Long userId) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
    
}
