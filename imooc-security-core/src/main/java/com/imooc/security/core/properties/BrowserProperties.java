package com.imooc.security.core.properties;

/**
 * Browser（浏览器）
 * 模块配置项
 */
public class BrowserProperties {
    //session
    private SessionProperties session= new SessionProperties();
    //默认注册页
    private String signUpUrl = "/imooc-signUp.html";
    //默认处理
    private String loginPage = "/imooc-Sign.html";
    //默认登录结果返回Json
    private LoginType loginType = LoginType.JSON;
    //记住我Token保存的时效
    private int rememberMeSeconds = 3600;

    private String signOutUrl;

    public String getSignOutUrl() {
        return signOutUrl;
    }

    public void setSignOutUrl(String signOutUrl) {
        this.signOutUrl = signOutUrl;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
