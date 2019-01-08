package com.imooc.security.app;

import com.imooc.security.app.social.AppSignUpUtils;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.support.SocialUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * APP 第三方签约（绑定和未绑定之间的状态）
 */
@RestController
public class AppSecurityController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private AppSignUpUtils signUpUtils;

    /**
     * 第三方签约（绑定和未绑定之间的状态）
     *
     * @param request
     * @return
     */
    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_SIGNUP)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {

        SocialUserInfo userInfo = new SocialUserInfo();

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        //第三方信息保存redis中
        signUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
        return userInfo;
    }
}
