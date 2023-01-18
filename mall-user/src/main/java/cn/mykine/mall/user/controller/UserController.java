package cn.mykine.mall.user.controller;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.UserDTO;
import cn.mykine.mall.user.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping(value = "/hello")
    public String sayHello(HttpServletRequest request){
            return "hello world~~~";
    }

    @GetMapping(value = "/user/{id}")
    public BaseResponse<UserDTO> getUser(@PathVariable(value = "id") Long userId) {
        UserDTO user = userService.getUser(userId);
        return BaseResponse.success(user);
    }

    @PostMapping(value = "/user")
    public BaseResponse updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return BaseResponse.success();
    }

}
