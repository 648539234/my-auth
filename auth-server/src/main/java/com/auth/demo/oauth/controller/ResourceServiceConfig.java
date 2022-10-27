package com.auth.demo.oauth.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.oauth.controller
 * @date 2022/10/27 16:53
 */
@Configuration
@EnableResourceServer //基于OAuth2开启资源服务器配置
public class ResourceServiceConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                //此处资源访问必须要有access_token,和security上面配置的WebSecurityConfig不一样,就算登录了没有access_token此处也会直接拦截
                .and().requestMatchers().antMatchers("/user/test/auth");
    }
}
