package com.imooc.security.core.validate.code;

import com.imooc.security.core.validate.code.sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码仓库操作
 */
public interface ValidateCodeRepository {
    /**
     * 保存
     *
     * @param webRequest
     * @param validateCode
     * @param validateCodeType
     */
    void save(ServletWebRequest webRequest, ValidateCode validateCode, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param webRequest
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest webRequest, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param webRequest
     * @param validateCodeType
     */
    void remove(ServletWebRequest webRequest, ValidateCodeType validateCodeType);

}
