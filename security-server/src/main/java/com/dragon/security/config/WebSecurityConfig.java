package com.dragon.security.config;

import com.dragon.security.auth.filter.CustomNameAuthenticationFilter;
import com.dragon.security.auth.filter.CustomOtherAuthenticationFilter;
import com.dragon.security.auth.filter.password.CustomPasswordEncoder;
import com.dragon.security.auth.filter.provider.NameAuthenticationProvider;
import com.dragon.security.auth.filter.provider.OtherAuthenticationProvider;
import com.dragon.security.auth.filter.token.CustomAuthenticationDetailSource;
import com.dragon.security.handler.ForwardFailHandlerImpl;
import com.dragon.security.handler.ForwardSuccessHandlerImpl;
import com.dragon.security.handler.LogoutSuccessHandlerImpl;
import com.dragon.security.property.SecurityUrlProperty;
import com.dragon.security.session.ForwardInvalidSessionStrategy;
import com.dragon.security.session.ForwardSessionExpiredStrategy;
import com.dragon.security.session.SessionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/26 15:59
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /** 静态资源列表 */
    public static final String STATIC_RESOURCE = "/images/**,/swagger-ui.html,/webjars/**,/v2/**,/swagger-resources/**,/doc.html";
    /** 禁止请求的URL */
    public static final String PERMIT_ALL_URL = "/common/**,/login/**,/logout/**,/sso/**,/rsa/**";

    @Autowired
    private SecurityUrlProperty securityUrlProperty;

    @Autowired
    private SessionProperty sessionProperty;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //静态资源不需要认证
        web.ignoring().antMatchers(STATIC_RESOURCE.split(","));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); //允许跨域,包括下面这些安全校验全部都是在前后不分离的前提下做的
        //禁用X-Frame-Options响应头,浏览器可以其他页面中通过<iframe>标签渲染我们的页面
        http.headers().frameOptions().disable();
        //禁用X-XSS-Protection响应头,浏览器不会对页面进行XSS攻击过滤
        http.headers().xssProtection().disable();
        http.csrf().disable(); //禁用csrf校验
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll(); //OPTIONS的HTTP请求无需认证
        http.authorizeRequests().antMatchers(PERMIT_ALL_URL.split(",")).permitAll();//允许所有人访问这个URL无需认证
        http.authorizeRequests().anyRequest().authenticated(); //其他请求都需要认证

        //认证失败的处理,返回响应403,设置错误页面(也可以不设置错误页面),可以重写AccessDeniedHandler接口实现
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage(securityUrlProperty.getAccessDenyUrl());
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        //未认证的处理策略,没有登录认证来访问,可以重写AuthenticationEntryPoint接口来实现
        LoginUrlAuthenticationEntryPoint point = new LoginUrlAuthenticationEntryPoint(securityUrlProperty.getToLoginUrl());
        point.setUseForward(true);
        http.exceptionHandling().authenticationEntryPoint(point);

        //认证成功处理,实现AuthenticationSuccessHandler接口即可,这里做转发
        ForwardSuccessHandlerImpl successHandler = new ForwardSuccessHandlerImpl(securityUrlProperty.getLoginSuccessUrl());
        //认证失败处理,实现AuthenticationFailHandler接口即可,这里做转发
        ForwardFailHandlerImpl failHandler = new ForwardFailHandlerImpl(securityUrlProperty.getLoginFailUrl());

        SessionRegistry sessionRegistry = new SessionRegistryImpl();

        //登录密码认证处理,只处理/login/password的URL请求,其他请求不做认证处理
        CustomNameAuthenticationFilter loginFilter
                = new CustomNameAuthenticationFilter(securityUrlProperty.getLoginPasswordUrl(),
                successHandler,failHandler,this.authenticationManager(),new CustomAuthenticationDetailSource());
        loginFilter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy(sessionRegistry));
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class); //替换用户密码登录认证的Filter

        //其他认证处理,只处理/login/password的URL请求,其他请求不做认证处理
        CustomOtherAuthenticationFilter otherFilter
                = new CustomOtherAuthenticationFilter(securityUrlProperty.getLoginPasswordUrl(),
                successHandler,failHandler,this.authenticationManager());
        otherFilter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy(sessionRegistry));
        http.addFilterAfter(otherFilter, CustomNameAuthenticationFilter.class); //替换用户密码登录认证的Filter

        //登出逻辑使用默认的即可
        LogoutSuccessHandlerImpl logoutSuccessHandler = new LogoutSuccessHandlerImpl(securityUrlProperty.getLoginOtherUrl());
        //关闭Session会话,清空token
        LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessHandler, new SecurityContextLogoutHandler(),new CookieClearingLogoutHandler(sessionProperty.getSessionKey()));
        http.addFilterAt(logoutFilter, LogoutFilter.class);

        //设置Session会话管理,SessionManagerFilter
        http.sessionManagement()
                .invalidSessionStrategy(new ForwardInvalidSessionStrategy(securityUrlProperty.getInvalidSessionUrl()))
                .sessionAuthenticationFailureHandler(failHandler) //session认证失败策略
                .maximumSessions(1) //同ConcurrentSessionControlAuthenticationStrategy每个用于最多允许多少Session
                .maxSessionsPreventsLogin(false) //当会话超过最大数量时将旧的会话给失效,true会拒绝创建新的会话
                //会话超时处理
                .expiredSessionStrategy(new ForwardSessionExpiredStrategy(securityUrlProperty.getInvalidSessionUrl()))
                .sessionRegistry(sessionRegistry);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        NameAuthenticationProvider provider = new NameAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new CustomPasswordEncoder());
        auth.authenticationProvider(provider);

        OtherAuthenticationProvider otherProvider = new OtherAuthenticationProvider(false);
        auth.authenticationProvider(otherProvider);
    }

    /** 复合的Session会话安全策略,sessionRegistry会话注册器 */
    private CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy(SessionRegistry sessionRegistry){
        List<SessionAuthenticationStrategy> delegateList = new ArrayList<>();
        //设置同一个认证用户最大的会话数量,一个认证的用户只允许有1个会话
        ConcurrentSessionControlAuthenticationStrategy sessionAuthenticationStrategy
                = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
        sessionAuthenticationStrategy.setMaximumSessions(1);
        //当会话超过最大数量时将旧的会话给失效,true会拒绝创建新的会话
        sessionAuthenticationStrategy.setExceptionIfMaximumExceeded(false);
        delegateList.add(sessionAuthenticationStrategy);

        //固定SessionId,当认证成功后会修改SessionId,从而固定Session会话
        SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
        delegateList.add(sessionFixationProtectionStrategy);

        //用于认证时将会话注册到SessionRegistry中,以便跟踪和控制活跃的用户
        RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy
                = new RegisterSessionAuthenticationStrategy(sessionRegistry);
        delegateList.add(registerSessionAuthenticationStrategy);

        return new CompositeSessionAuthenticationStrategy(delegateList);
    }
}
