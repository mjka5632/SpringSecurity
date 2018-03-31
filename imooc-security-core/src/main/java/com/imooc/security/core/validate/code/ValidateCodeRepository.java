package com.imooc.security.core.validate.code;

import com.imooc.security.core.validate.code.sms.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param request
     * @param validateCode
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode validateCode, ValidateCodeType validateCodeType);

    /**
     * 得到验证码
     * @param request
     * @param validateCodeType
     */
    ValidateCode  get(ServletWebRequest request, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     * @param request
     * @param validateCodeType
     */
    void remove(ServletWebRequest request, ValidateCodeType validateCodeType);

}
