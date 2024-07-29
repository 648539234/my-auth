package com.dragon.security.auth.filter.token;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 11:15
 */
public class CustomAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, CustomAuthenticationDetails>{
    @Override
    public CustomAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CustomAuthenticationDetails(context);
    }
}
