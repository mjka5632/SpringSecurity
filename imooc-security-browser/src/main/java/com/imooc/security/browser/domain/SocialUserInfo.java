package com.imooc.security.browser.domain;

import lombok.Data;

/**
 * 社交用户信息
 */
@Data
public class SocialUserInfo {

    private String providerId;

    private String providerUserId;

    private String nickname;

    private String headimg;

}
