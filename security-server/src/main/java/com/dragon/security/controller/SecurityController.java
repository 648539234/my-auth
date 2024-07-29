package com.dragon.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理认证过程中转发过来的url进行处理
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 16:15
 */
@RestController
public class SecurityController {

    @RequestMapping("/login/success")
    public String loginSuccess(HttpServletRequest request){
        //认证通过后AbstractUserDetailsAuthenticationProvider会自动将认证的用户信息放到SecurityContextHolder的Authentication中,这里可以直接获取
        //注意此处的Authentication和认证前的token不一样,认证后会生成一个新的Authentication替换老的Authentication
        //参考源码AbstractUserDetailsAuthenticationProvider#authenticate
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null){
            return request.getSession().getId(); //对应token的值
        }
        return "NULL";
    }
}
