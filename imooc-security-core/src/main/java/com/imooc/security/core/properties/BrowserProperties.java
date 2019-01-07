package com.imooc.security.core.properties;

import lombok.Data;

/**
 * Browser（浏览器）
 * 模块配置项
 */
@Data
public class BrowserProperties {
    //session
    private SessionProperties session = new SessionProperties();
    //默认注册页
    private String signUpUrl = "/imooc-signUp.html";
    //默认处理
    private String loginPage = "/imooc-Sign.html";
    //默认登录结果返回Json
    private LoginType loginType = LoginType.JSON;
    //记住我Token保存的时效
    private int rememberMeSeconds = 3600;

    private String signOutUrl;

}
