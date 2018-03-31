package com.imooc.security.core.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 封装系统配置文件
 *
 *
 * @ConfigurationProperties
 * 表示读取所有以imooc.security开头的配置项
 */

@ConfigurationProperties(prefix ="imooc.security" )
public class SecurityProperties {
    //读取以imooc.security.browser开头的配置
    private BrowserProperties browser=new BrowserProperties();

    private ValidateCodeProperties code=new ValidateCodeProperties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
