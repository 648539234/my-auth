package com.dragon.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 10:14
 */
public class ForwardFailHandlerImpl extends ForwardAuthenticationFailureHandler {

    /**
     * 定义认证失败后的转发URL
     * @param forwardUrl
     */
    public ForwardFailHandlerImpl(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //自定义处理认证失败后的逻辑
        //例如打印日志
        super.onAuthenticationFailure(request, response, exception);
    }
}
