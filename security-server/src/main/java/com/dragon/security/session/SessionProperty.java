package com.dragon.security.session;

import org.springframework.stereotype.Component;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 14:34
 */
@Component
public class SessionProperty {
    private String sessionKey = "Authorization";

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
