package com.imooc.security.core.properties;

/**
 * Browser（浏览器）
 * 模块配置项
 */
public class BrowserProperties {
    //默认处理
    private String loginPage="/imooc-Sign.html";
    //默认登录结果返回Json
    private LoginType loginType=LoginType.JSON;
    //记住我Token保存的时效
    private int rememberMeSeconds=3600;


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
