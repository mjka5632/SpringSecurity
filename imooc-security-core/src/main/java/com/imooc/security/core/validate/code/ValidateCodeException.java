package com.imooc.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;


/**
 * 图形验证码的过滤器
 * OncePerRequestFilter 保证过滤器只会被调用一次
 */
public class ValidateCodeException extends AuthenticationException {



    public ValidateCodeException(String msg) {
        super(msg);
    }
}

