package com.auth.demo.oauth.config;

import com.auth.demo.oauth.interceptor.RedirectCodeInterceptor;
import com.auth.demo.security.config.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.oauth.config
 * @date 2022/10/27 10:05
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private RedirectCodeInterceptor codeInterceptor;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //配置client_id
                .withClient("client")
                //配置client-secret
                .secret(passwordEncoder.encode("client"))
                //配置访问token的有效期
                .accessTokenValiditySeconds(3600)
                //配置刷新token的有效期
                .refreshTokenValiditySeconds(864000)
                //配置redirect_uri，用于授权成功后跳转
                .redirectUris("http://www.baidu.com")
                //配置申请的权限范围
                .scopes("all")
                /**
                 * 配置grant_type，表示授权类型
                 * authorization_code: 授权码
                 * password： 密码
                 * refresh_token: 更新令牌
                 */
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }

    @Override
    //配置OAuth2认证服务器相关处理逻辑
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //密码模式时需要配置认证管理器
                .reuseRefreshTokens(false) //同一个refresh_token是否可以重复使用
                .userDetailsService(userDetailServiceImpl)
                .addInterceptor(codeInterceptor) //所有认证处理的拦截器,可以用于修改重定向的选择
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); //支持POST

        //自定义授权页，传给前端scope
        Map<String,String> params = new HashMap<>();
        params.put("/oauth/confirm_access","/oauth/approve/confirm");
        endpoints.getFrameworkEndpointHandlerMapping().setMappings(params);
    }

    @Override
    //处理客户端(三方系统)调用OAuth2相关权限配置
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许校验token和获取token信息
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("isAuthenticated()");
    }
}
