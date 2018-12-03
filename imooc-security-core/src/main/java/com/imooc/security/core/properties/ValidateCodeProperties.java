package com.imooc.security.core.properties;

import lombok.Data;

/**
 * 验证码相关配置
 */
@Data
public class ValidateCodeProperties {
    /**
     * 图形验证码
     */
    private ImageCodeProperties image=new ImageCodeProperties();

    /**
     * 验证码
     */
    private SmsCodeProperties sms=new SmsCodeProperties();

}
