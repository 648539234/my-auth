package com.auth.demo.security.config;

import com.auth.demo.security.exceptionhandler.RestAuthenticationEntryPoint;
import com.auth.demo.security.exceptionhandler.RestfulAccessDeniedHandler;
import com.auth.demo.security.filter.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package PACKAGE_NAME
 * @date 2022/10/26 16:12
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationTokenFilter tokenFilter;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RestfulAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //关闭csrf,关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous() //允许登录
                .antMatchers("/oauth/**").permitAll() //允许OAuth2相关接口
                .anyRequest().authenticated(); //其他全部拦截

        //此处配置权限可以通过访问数据库权限表，然后遍历添加权限
        //http.antMatchers("/xxx").access("hasAuthority('xxx')");

        //在认证用户名密码签名加上处理token的过滤器
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置认证失败和权限不足的异常处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 从DB获取用户信息
        auth.userDetailsService(userDetailServiceImpl);
    }
}
