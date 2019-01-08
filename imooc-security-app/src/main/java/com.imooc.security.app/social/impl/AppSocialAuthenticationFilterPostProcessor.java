package com.imooc.security.app.social.impl;

import com.imooc.security.core.social.SocialAuthenticationFilterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 处理APPSocial登录成功的方式
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterProcessor {
    /**
     * 认证成功处理
     */
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {

        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);

    }
}
