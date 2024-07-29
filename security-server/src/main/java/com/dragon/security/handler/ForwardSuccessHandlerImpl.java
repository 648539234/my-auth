package com.dragon.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 10:07
 */
public class ForwardSuccessHandlerImpl extends ForwardAuthenticationSuccessHandler {
    /**
     * 定义认证成功后的转发URL
     * @param forwardUrl
     */
    public ForwardSuccessHandlerImpl(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //自定义处理认证成功后的逻辑
        if(request.getParameter("redirectUrl")!=null){
            response.sendRedirect(request.getParameter("redirectUrl"));
            return;
        }
        //父类就是做转发逻辑
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
