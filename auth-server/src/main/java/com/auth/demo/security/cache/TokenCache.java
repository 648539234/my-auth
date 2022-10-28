package com.auth.demo.security.cache;

import com.auth.demo.security.domain.LoginUser;
import com.auth.demo.security.domain.LoginUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

    public TokenCache(){
        LoginUser user = new LoginUser();
        LoginUserDao dao = new LoginUserDao();
        dao.setUsername("admin");
        dao.setPassword(new BCryptPasswordEncoder().encode("admin"));
        dao.setPermissions(Arrays.asList("userAdmin"));
        user.setLoginUserDao(dao);
        tokenCache.put("123456", user); //初始化一个登录用户方便测试
    }

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
