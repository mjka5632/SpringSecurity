package com.imooc.security.core.properties;

import lombok.Data;

/**
 * 图形验证码的配置
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {
    private int width = 67;
    private int height = 23;

    /**
     * 构造的时候定义长度
     * 为了不受父类的影响
     */
    public ImageCodeProperties() {
        setLength(4);
    }

}
