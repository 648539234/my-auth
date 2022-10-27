package com.auth.demo.security.cache;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.cache
 * @date 2022/10/26 16:30
 */
//此处应当是Redis去获取缓存信息
@Component
public class TokenCache {
    private ConcurrentHashMap<String, UserDetails> tokenCache = new ConcurrentHashMap<>();

    public UserDetails getUserDetails(String token){
        return tokenCache.get(token);
    }

    public void putUserDetails(String token,UserDetails details){
        tokenCache.put(token,details);
    }

    public void removeUserDetails(String token){
        tokenCache.remove(token);
    }
}
