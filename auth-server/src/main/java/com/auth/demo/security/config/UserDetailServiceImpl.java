package com.auth.demo.security.config;

import com.auth.demo.security.domain.LoginUser;
import com.auth.demo.security.domain.LoginUserDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.config
 * @date 2022/10/26 18:18
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService, InitializingBean {

    private Map<String, LoginUser> map = new HashMap<>(); //此处逻辑应当由数据库实现,此处为假逻辑

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //TODO 从数据库中获取用户信息
        LoginUser loginUser = map.get(username);
        if(loginUser==null){
            throw new RuntimeException("用户名或密码错误");
        }

        return loginUser;
    }

    @Override //此处造假数据用于初始化
    public void afterPropertiesSet() throws Exception {
        LoginUserDao user1 = new LoginUserDao();
        user1.setUsername("admin");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //此处不依赖注入 防止循环依赖
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setPermissions(Arrays.asList("userAdmin"));
        LoginUser loginUser = new LoginUser();
        loginUser.setLoginUserDao(user1);
        map.put("admin", loginUser);
    }
}
