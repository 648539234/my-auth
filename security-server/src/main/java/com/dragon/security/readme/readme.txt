采用Spring Security + Spring Session实现的认证方案
可以支持多认证方案 (根据不同的登录接口走不同的认证方式,非登录接口通过SessionManagerFilter校验SessionId是否有效)

认证链路（更多认证方式可以额外写多个Filter,对应多个Provider和token）
CustomNameAuthenticationFilter       ->      CustomOtherAuthenticationFilter    ->    SessionManagerFilter
   (只处理/login/password请求,非这个请求跳过)     (只处理/login/password请求,非这个请求跳过)     (其他[非permitAll]请求判断Session是否有效)
            |-NameAuthenticationProvider                 |-OtherAuthenticationProvider

token维护(Spring-Session)
    1.ForwardSuccessHandlerImpl#onAuthenticationSuccess 认证成功会转发到/login/success上
    2./login/success处理的RequestHandler,调用request.getSession()创建Session对象
    3.当响应完成后调用flushBuffer方法时触发SaveToSessionResponseWrapper#saveContext方法将SecurityContext认证结果都存到Session中
    4.SessionRepositoryFilter过滤器会在finally调用SessionRepositoryRequestWrapper#commitSession,将Session进行持久化,通过
    RedisIndexedSessionRepository存放到redis中,还会调用HeaderHttpSessionIdResolver#setSessionId方法将SessionId放到响应头中
