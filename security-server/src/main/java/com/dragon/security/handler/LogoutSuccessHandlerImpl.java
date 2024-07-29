package com.dragon.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 14:12
 */
public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {

    public LogoutSuccessHandlerImpl(String defaultTargetUrl) {
        super.setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getRequestDispatcher(this.getDefaultTargetUrl()).forward(request,response); //重定向到默认URL
    }
}
