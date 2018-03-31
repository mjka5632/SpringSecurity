package com.imooc.security.core.validate.code;


import com.imooc.security.core.properties.SecurityConstants;

/**
 * 验证码类型
 */
public enum  ValidateCodeType {
    /**
     * 手机短信
     */
    SMS{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    IMAGE{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     * 校验时从请求中取出参数的名字
     * @return
     */
    public abstract String getParamNameOnValidate();

}
