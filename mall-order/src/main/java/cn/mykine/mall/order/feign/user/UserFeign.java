package cn.mykine.mall.order.feign.user;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.UserDTO;
import cn.mykine.mall.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "mall-user",
        fallback = UserFeignFallback.class,
        configuration = FeignConfig.class
)
public interface UserFeign {

    @GetMapping("/user/{id}")
    BaseResponse<UserDTO> getUser(@PathVariable(value = "id") Long userId);


}
