package com.imooc.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;


/**
 * 验证码的错误处理器
 */
public class ValidateCodeException extends AuthenticationException {


    public ValidateCodeException(String msg) {
        super(msg);
    }
}

