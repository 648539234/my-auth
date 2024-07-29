package com.dragon.security.auth.filter;

import com.dragon.security.auth.filter.token.OtherAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 10:19
 */
public class CustomOtherAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomOtherAuthenticationFilter(String filterProcessUrl,
                                           AuthenticationSuccessHandler successHandler,
                                           AuthenticationFailureHandler failureHandler,
                                           AuthenticationManager authenticationManager) {
        super.setPostOnly(true);
        super.setFilterProcessesUrl(filterProcessUrl);
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationFailureHandler(failureHandler);
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //自定义其他方式认证生成一个认证Token
        OtherAuthenticationToken token = new OtherAuthenticationToken(request.getParameter("username"),null,"NO-PASS");
        return this.getAuthenticationManager().authenticate(token);
    }
}
