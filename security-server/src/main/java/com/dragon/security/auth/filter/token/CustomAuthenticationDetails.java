package com.dragon.security.auth.filter.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 11:16
 */
public class CustomAuthenticationDetails {
    private final String remoteAddress;
    private final String sessionId;

    public CustomAuthenticationDetails(HttpServletRequest request) {
        this.remoteAddress = request.getRemoteAddr();
        HttpSession session = request.getSession(false);
        this.sessionId = session != null ? session.getId() : null;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getSessionId() {
        return sessionId;
    }
}
