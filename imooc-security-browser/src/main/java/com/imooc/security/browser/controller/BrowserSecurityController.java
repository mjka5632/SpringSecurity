package com.imooc.security.browser.controller;

import com.imooc.security.browser.domain.SimpleResponse;
import com.imooc.security.browser.domain.SocialUserInfo;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;

//import com.imooc.security.browser.domain.User;

@RestController
public class BrowserSecurityController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    //跳转之前，Spring Security会把请求信息缓存到HttpSessionRequestCahe这个session里
    private RequestCache requestCache = new HttpSessionRequestCache();
    //spring工具做跳转
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    //配置信息
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 需要身份验证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)//401
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //通过HttpSessionRequestCache拿到请求信息
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            //得到请求地址
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的地址" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }

        return new SimpleResponse("访问的服务需要身份认证，请引导到登录页");

    }

    /**
     * 获取社交用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {

        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());

        return userInfo;
    }
}
