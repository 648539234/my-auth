package com.auth.demo.oauth.controller;

import com.auth.demo.common.CommonResult;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.oauth.controller
 * @date 2022/10/27 14:07
 */
@RestController
@RequestMapping("/oauth")
@SessionAttributes({AuthServerController.AUTHORIZATION_REQUEST_ATTR_NAME})
public class AuthServerController {
    static final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";

    @RequestMapping("/approve/confirm")
    public CommonResult<String> getAccessConfirmation(Map<String,Object> model, HttpServletRequest request, HttpServletResponse response){
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get(AuthServerController.AUTHORIZATION_REQUEST_ATTR_NAME);
        Set<String> set = authorizationRequest.getScope();
        return CommonResult.success(Strings.join(set, ','));
    }

}
