package com.auth.demo.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.domain
 * @date 2022/10/26 17:45
 * 用户信息
 */
public class LoginUser implements UserDetails {

    private LoginUserDao loginUserDao;

    public void setLoginUserDao(LoginUserDao loginUserDao) {
        this.loginUserDao = loginUserDao;
    }

    public LoginUserDao getLoginUserDao() {
        return loginUserDao;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return loginUserDao.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return loginUserDao.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUserDao.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
