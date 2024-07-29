package com.dragon.security.auth.filter;

import com.dragon.security.auth.filter.token.PasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 10:19
 */
public class CustomNameAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomNameAuthenticationFilter(String filterProcessUrl,
                                          AuthenticationSuccessHandler successHandler,
                                          AuthenticationFailureHandler failureHandler,
                                          AuthenticationManager authenticationManager,
                                          AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource) {
        super.setPostOnly(true);
        super.setFilterProcessesUrl(filterProcessUrl);
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationFailureHandler(failureHandler);
        super.setAuthenticationManager(authenticationManager);
        super.setAuthenticationDetailsSource(authenticationDetailsSource);

        //自定义获取用户名和密码的请求字段
        super.setPasswordParameter("password");
        super.setUsernameParameter("username");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //通过请求参数获取用户名和密码
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //基于密钥对password进行解密处理,验证图形或短信验证码,在登录前会先发起接口生成验证码(不会认证),此处就会对session进行存储了
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute("code");
        //比较验证码,验证码不对就抛出异常

        PasswordAuthenticationToken authToken = new PasswordAuthenticationToken(username, password,"WEB");
        setDetails(request, authToken); //放入认证的额外信息,这里看情况是否配置了AuthenticationDetailsSource调用

        return this.getAuthenticationManager().authenticate(authToken);
    }
}
