package com.dragon.security.auth.filter.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 14:00
 */
public class OtherAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String loginFrom;

    public OtherAuthenticationToken(Object principal, Object credentials,String loginFrom) {
        super(principal, credentials);
        this.loginFrom = loginFrom;
    }

    public String getLoginFrom() {
        return loginFrom;
    }
}
