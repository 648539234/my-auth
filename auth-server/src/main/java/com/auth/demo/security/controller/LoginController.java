package com.auth.demo.security.controller;

import com.auth.demo.common.CommonResult;
import com.auth.demo.security.domain.LoginRequest;
import com.auth.demo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.controller
 * @date 2022/10/26 17:30
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    //处理登录逻辑的请求
    private CommonResult login(@RequestBody LoginRequest loginRequest){
        String token = userService.handleUserLogin(loginRequest.getUsername(), loginRequest.getPassword());
        return CommonResult.success(token);
    }

    @PostMapping("/getUserInfo")
    //获取用户信息的请求,该接口不应当出现在授权服务器中,主要用于测试
    private CommonResult getUserInfo(Authentication authentication){
        return CommonResult.success(authentication.getPrincipal());
    }

    @PostMapping("/logout")
    //处理登录逻辑的请求
    private CommonResult logout(@RequestHeader("Authorization") String authorization){
        String token = userService.handleUserLogout(authorization);
        return CommonResult.success(token);
    }

    @PostMapping("/test/auth")
    //获取用户信息的请求,该接口不应当出现在授权服务器中,主要用于测试
    private CommonResult testAuth(Authentication authentication){
        return CommonResult.success(authentication.getPrincipal());
    }
}
