package com.imooc.security.core.properties;

/**
 * 验证码相关配置
 */
public class ValidateCodeProperties {
    /**
     * 图形验证码
     */
    private ImageCodeProperties image=new ImageCodeProperties();

    /**
     * 验证码
     */
    private SmsCodeProperties sms=new SmsCodeProperties();

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }


    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
