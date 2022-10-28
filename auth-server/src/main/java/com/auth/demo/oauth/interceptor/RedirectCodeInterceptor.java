package com.auth.demo.oauth.interceptor;

import com.auth.demo.common.CommonResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.oauth.config
 * @date 2022/10/27 17:33
 * 重定向转json输出
 */
@Component
public class RedirectCodeInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if("/oauth/authorize".equals(request.getRequestURI())&&modelAndView.getView() instanceof RedirectView) {
            String redirectUrl = ((RedirectView)modelAndView.getView()).getUrl();
            CommonResult result = CommonResult.success(redirectUrl);
            response.setContentType("application/json;charset=UTF-8");

            Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
            ObjectMapper mapper = builder.build();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            ObjectWriter writer = mapper.writer();
            writer.writeValue(response.getOutputStream(), result);
        }
    }
}
