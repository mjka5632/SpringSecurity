package com.imooc.security.core.validate.code.sms;
/**
 * 模拟短信供应商发送请求
 */
public class DefaultSmsCodeSender implements SmsCodeSender {


    @Override
    public void Send(String mobile, String code) {
        System.out.println("向手机"+mobile+"发送短信验证码"+code);
    }
}
