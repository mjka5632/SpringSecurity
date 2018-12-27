package com.imooc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * 设置app-social 认证成功的处理方式
 * 原因：browser：默认第三方登录成功跳转到访问页面
 *          App：会产生token
 */
public interface SocialAuthenticationFilterProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
