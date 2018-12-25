package com.imooc.security.app.validate.code.impl;

import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeRepository;
import com.imooc.security.core.validate.code.ValidateCodeType;
import com.imooc.security.core.validate.code.sms.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    /**
     * 操作Redis的工具类
     */
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void save(ServletWebRequest webRequest, ValidateCode validateCode, ValidateCodeType validateCodeType) {
        redisTemplate.opsForValue().set(buildKey(webRequest, validateCodeType), validateCode, 200, TimeUnit.MINUTES);
    }

    /**
     * 构建key
     *
     * @param webRequest
     * @param validateCodeType
     * @return
     */
    private String buildKey(ServletWebRequest webRequest, ValidateCodeType validateCodeType) {
        String deviceId = webRequest.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头上携带deviceId参数");
        }
        return "code:" + validateCodeType.toString().toLowerCase() + ":" + deviceId;
    }

    @Override
    public ValidateCode get(ServletWebRequest webRequest, ValidateCodeType validateCodeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(webRequest, validateCodeType));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest webRequest, ValidateCodeType validateCodeType) {
        redisTemplate.delete(buildKey(webRequest, validateCodeType));

    }

}
