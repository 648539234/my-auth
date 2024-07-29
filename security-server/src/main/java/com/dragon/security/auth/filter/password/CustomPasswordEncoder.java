package com.dragon.security.auth.filter.password;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 16:03
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        //密码写入的时候会自动encode
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(StringUtils.isEmpty(encodedPassword)){
            return false;
        }
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes(StandardCharsets.UTF_8)));
    }
}
