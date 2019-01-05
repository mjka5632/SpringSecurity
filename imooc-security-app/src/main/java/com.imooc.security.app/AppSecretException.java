package com.imooc.security.app;

/**
 * APP封装异常信息
 */
public class AppSecretException extends RuntimeException {

    public AppSecretException(String message) {
        super(message);
    }
}
