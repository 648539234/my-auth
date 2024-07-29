package com.dragon.security.auth.filter.provider;

import com.dragon.security.auth.filter.token.PasswordAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 只处理PasswordAuthenticationToken,因为其他认证的Filter也会走,但处理只处理对应的认证
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 15:27
 */
public class NameAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //用户信息会之前先调用UserDetailsService#loadUserByUsername获取
        //用户密码校验,校验失败抛出AuthenticationException

        //父类校验会根据userDetails中的password(密文)和authentication中的password(明文),通过PasswordEncoder进行比对
        super.additionalAuthenticationChecks(userDetails, authentication);

        //指定userDetails是通过用户密码登录成功的
    }
}
