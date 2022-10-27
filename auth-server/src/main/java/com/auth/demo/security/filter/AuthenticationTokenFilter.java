package com.auth.demo.security.filter;

import com.auth.demo.security.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.filter
 * @date 2022/10/26 16:23
 * 过滤器用于将token
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenCache tokenCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Token"); //获取请求头的token
        if(!StringUtils.hasText(token)){
            //如果没有token不需要报错,因为后面FilterSecurityInterceptor过滤器会拦截掉需要认证的请求但是没有认证(SecurityContext中没有用户信息)
            filterChain.doFilter(request,response);
            return;
        }

        UserDetails loginUser = tokenCache.getUserDetails(token);

        if(loginUser == null){ //如果已经登出走该逻辑
            filterChain.doFilter(request,response);
            return;
        }

        //上下文放入认证信息(Authentication实现类)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
