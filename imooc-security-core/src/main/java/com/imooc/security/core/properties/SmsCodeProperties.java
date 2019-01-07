package com.imooc.security.core.properties;

import lombok.Data;

/**
 * 图形验证码的配置
 */
@Data
public class SmsCodeProperties {
    private int length = 6;
    private int expireIn = 60;
    //请求哪些url会出现验证码
    private String url;

}
