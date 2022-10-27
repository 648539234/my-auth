package com.auth.demo.security.service;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.service
 * @date 2022/10/26 17:34
 */

import com.auth.demo.security.cache.TokenCache;
import com.auth.demo.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService{

    @Autowired
    private TokenCache cache;

    @Autowired
    private AuthenticationManager authenticationManager; //用于登录认证处理

    //处理登录逻辑
    public String handleUserLogin(String username,String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(authentication == null){
            throw new RuntimeException("登录失败");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String uuid = UUID.randomUUID().toString();
        cache.putUserDetails(uuid, loginUser);
        return uuid;
    }

    //处理登出逻辑
    public String handleUserLogout(String token){
        cache.removeUserDetails(token);
        return "登出成功";
    }
}
