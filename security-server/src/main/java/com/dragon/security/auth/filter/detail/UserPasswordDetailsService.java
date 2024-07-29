package com.dragon.security.auth.filter.detail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 15:31
 */
@Component
public class UserPasswordDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名获取用户信息
        return null;
    }
}
