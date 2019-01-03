package com.imooc.security.core.support;

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
