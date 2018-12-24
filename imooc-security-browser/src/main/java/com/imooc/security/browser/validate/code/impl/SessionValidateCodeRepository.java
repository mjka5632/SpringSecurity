package com.imooc.security.browser.validate.code.impl;

import com.imooc.security.core.validate.code.ValidateCodeRepository;
import com.imooc.security.core.validate.code.ValidateCodeType;
import com.imooc.security.core.validate.code.sms.ValidateCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

public class SessionValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 操作session工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    @Override
    public void save(ServletWebRequest webRequest, ValidateCode validateCode, ValidateCodeType validateCodeType) {

        sessionStrategy.setAttribute(webRequest, getSessionKey(validateCodeType), validateCode);
    }

    /**
     * 构建session的key
     *
     * @param validateCodeType
     * @return
     */
    private String getSessionKey(ValidateCodeType validateCodeType) {

        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();

    }

    @Override
    public ValidateCode get(ServletWebRequest webRequest, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(webRequest, getSessionKey(validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest webRequest, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(webRequest, getSessionKey(validateCodeType));
    }
}
