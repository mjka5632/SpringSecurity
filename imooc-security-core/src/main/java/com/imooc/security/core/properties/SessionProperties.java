package com.imooc.security.core.properties;

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

    public int getMaximumSessions() {
        return maximumSessions;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public boolean isMaxSessionsPreventsLogin() {
        return maxSessionsPreventsLogin;
    }

    public void setMaxSessionsPreventsLogin(boolean maxSessionsPreventsLogin) {
        this.maxSessionsPreventsLogin = maxSessionsPreventsLogin;
    }

    public String getInvalidSessionUrl() {
        return invalidSessionUrl;
    }

    public void setInvalidSessionUrl(String invalidSessionUrl) {
        this.invalidSessionUrl = invalidSessionUrl;
    }
}
