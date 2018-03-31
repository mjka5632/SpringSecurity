package com.imooc.security.core.validate.code.sms;

/**
 * 模拟短信供应商发送请求
 */
public interface SmsCodeSender {
    void Send(String mobile,String code);
}
