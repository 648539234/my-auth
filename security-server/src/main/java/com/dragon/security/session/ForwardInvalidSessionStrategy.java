package com.dragon.security.session;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 14:40
 */
public class ForwardInvalidSessionStrategy implements InvalidSessionStrategy{
    private String destinationUrl;

    public ForwardInvalidSessionStrategy(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher(destinationUrl).forward(request, response);
    }
}
