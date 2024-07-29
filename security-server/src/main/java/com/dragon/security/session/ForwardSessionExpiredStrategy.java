package com.dragon.security.session;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 14:49
 */
public class ForwardSessionExpiredStrategy implements SessionInformationExpiredStrategy {
    private String destinationUrl;

    public ForwardSessionExpiredStrategy(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getRequest().getRequestDispatcher(destinationUrl).forward(event.getRequest(), event.getResponse());
    }
}
