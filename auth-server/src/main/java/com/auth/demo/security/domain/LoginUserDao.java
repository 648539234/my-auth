package com.auth.demo.security.domain;

import java.util.List;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.security.domain
 * @date 2022/10/26 17:50
 * 用户信息数据库实体类
 */
public class LoginUserDao {
    private String userId; //用户Id
    private String username; //用户名
    private String password; //密码
    private List<String> permissions; //权限

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
