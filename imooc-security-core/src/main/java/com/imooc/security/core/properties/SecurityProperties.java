package com.imooc.security.core.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 封装系统配置文件
 *
 * @ConfigurationProperties 表示读取所有以imooc.security开头的配置项
 */

@ConfigurationProperties(prefix = "imooc.security")
@Data
public class SecurityProperties {
    //读取以imooc.security.browser开头的配置
    private BrowserProperties browser = new BrowserProperties();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();


}
