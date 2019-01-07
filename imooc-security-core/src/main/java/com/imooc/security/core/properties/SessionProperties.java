package com.imooc.security.core.properties;

import lombok.Data;

@Data
public class SessionProperties {
    /**
     * 允许最大存在的sessions数，默认为1
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求
     * 默认false:不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin = true;
    /**
     * session失效时跳转的地址
     */
    private String invalidSessionUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;

}
