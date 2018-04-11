package com.imooc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 封装验证码逻辑
 */
public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX="SESSION_KEY_FOR_CODE";

    /**
     * 创建验证码
     * ServletWebRequest ：spring 工具类，封装了请求和响应
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request)throws Exception;

    /**
     * 校验验证码
     * @param request
     */
    void validate(ServletWebRequest request);
}
