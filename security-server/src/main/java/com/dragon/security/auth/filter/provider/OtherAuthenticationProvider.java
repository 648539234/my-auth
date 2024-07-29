package com.dragon.security.auth.filter.provider;

import com.dragon.security.auth.filter.token.PasswordAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 15:51
 */
public class OtherAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    public OtherAuthenticationProvider(boolean hideUserNotFoundException) {
        super.setHideUserNotFoundExceptions(hideUserNotFoundException);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //可以通过调用外部接口来拉取用户信息,不需要写UserDetailsService接口了
        return null;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //自定义认证
    }


}
