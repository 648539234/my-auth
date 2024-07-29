package com.dragon.security.config;

import com.dragon.security.session.SessionProperty;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @implNote start with 2024/7/29 16:45
 */
@Configuration
//maxInactiveIntervalInSeconds控制Session最大不活动时间(调用接口会刷新Session有效期)
//redisNamespace是Redis中Session的命名空间
//redisFlushMode控制Session何时同步到Redis,ON_SAVE每次请求结束写入Redis,IMMEDIATE只要Session被修改立刻写入Redis
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1500,redisNamespace = "SECURITY:SESSION", redisFlushMode = RedisFlushMode.ON_SAVE)
public class SessionConfig implements InitializingBean {

    @Autowired
    private SessionProperty sessionProperty;

    @Autowired
    private RedisIndexedSessionRepository redisIndexedSessionRepository;

    /**
     * set-Cookie的生成方式(这个不用管)
     * @return
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName(sessionProperty.getSessionKey());
        defaultCookieSerializer.setUseHttpOnlyCookie(true);
        defaultCookieSerializer.setUseSecureCookie(true);
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }

    @Bean
    public HeaderHttpSessionIdResolver httpSessionStrategy(){
        //底层会添加一个响应头,参考setSessionId方法
        return new HeaderHttpSessionIdResolver(sessionProperty.getSessionKey());
    }

    //Session内容放到redis序列化的方法
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new JdkSerializationRedisSerializer();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisIndexedSessionRepository.setRedisKeyNamespace("SECURITY:SESSION");
    }
}
