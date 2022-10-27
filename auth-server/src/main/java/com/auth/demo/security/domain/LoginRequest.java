package com.auth.demo.security.domain;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.domain
 * @date 2022/10/26 19:30
 */
public class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
