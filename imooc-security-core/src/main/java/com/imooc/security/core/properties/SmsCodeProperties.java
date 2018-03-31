package com.imooc.security.core.properties;

/**
 * 图形验证码的配置
 */
public class SmsCodeProperties {
    private int length = 6;
    private int expireIn = 60;
    //请求哪些url会出现验证码
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
