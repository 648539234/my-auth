package com.dragon.security.property;

import org.springframework.stereotype.Component;

/**
 * 所有用到的URL,这些URL都会对应一个handler处理类去处理响应结果给前端（转发）
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/26 16:34
 */
@Component
public class SecurityUrlProperty {
    private String accessDenyUrl = "/access/deny";
    private String toLoginUrl = "/to/login";
    private String loginSuccessUrl = "/login/success";
    private String loginFailUrl = "/login/fail";
    private String loginPasswordUrl = "/login/password";
    private String loginOtherUrl = "/login/other";
    private String logoutUrl = "/logout";
    private String logoutSuccessUrl = "/logout/success";
    private String invalidSessionUrl = "/invalid/session";

    public String getAccessDenyUrl() {
        return accessDenyUrl;
    }

    public void setAccessDenyUrl(String accessDenyUrl) {
        this.accessDenyUrl = accessDenyUrl;
    }

    public String getToLoginUrl() {
        return toLoginUrl;
    }

    public void setToLoginUrl(String toLoginUrl) {
        this.toLoginUrl = toLoginUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getLoginFailUrl() {
        return loginFailUrl;
    }

    public void setLoginFailUrl(String loginFailUrl) {
        this.loginFailUrl = loginFailUrl;
    }

    public String getLoginPasswordUrl() {
        return loginPasswordUrl;
    }

    public void setLoginPasswordUrl(String loginPasswordUrl) {
        this.loginPasswordUrl = loginPasswordUrl;
    }

    public String getLoginOtherUrl() {
        return loginOtherUrl;
    }

    public void setLoginOtherUrl(String loginOtherUrl) {
        this.loginOtherUrl = loginOtherUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    public String getInvalidSessionUrl() {
        return invalidSessionUrl;
    }

    public void setInvalidSessionUrl(String invalidSessionUrl) {
        this.invalidSessionUrl = invalidSessionUrl;
    }
}
