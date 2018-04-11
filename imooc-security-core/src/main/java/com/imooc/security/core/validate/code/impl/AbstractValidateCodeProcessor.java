package com.imooc.security.core.validate.code.impl;

import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.ValidateCodeType;
import com.imooc.security.core.validate.code.sms.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 实现验证码 【基础流程】
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    /**
     * Spring 启动时会查找带有ValidateCodeGenerator(可以前后加其他的字母)接口类的实现
     * 放在Map名中
     */
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators;


    /**
     * 操作Session的工具类
     */
    private SessionStrategy session=new HttpSessionSessionStrategy();


    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request,validateCode);
        send(request,validateCode);

    }

    /**
     * 生成验证码
     * @param request
     * @return
     */
    private C generate(ServletWebRequest request){

        String type=getProcessorType(request);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type+"CodeGenerator");
        return (C) validateCodeGenerator.generateCode(request);

    }

    /**
     * 保存验证码
     * @param request
     * @param validateCode
     */
    protected  void save(ServletWebRequest request, C validateCode){
        session.setAttribute(request,SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase()
        ,validateCode);


    }

    /**
     * 发送验证码由子类实现
     * @param request
     * @param Validate
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request,C Validate) throws Exception;

    /**
     * 根据请求的URL获取验证码类型
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type=StringUtils.substringBefore(getClass().getSimpleName(),"CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 根据请求url获取验证码类型
     * @param request
     * @return
     */
    private String getProcessorType(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

    /**
     * 校验逻辑
     *
     * @param request
     */
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey=getSessionKey(request);
        C codeInSession=(C) session.getAttribute(request,sessionKey);

        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            session.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        session.removeAttribute(request, sessionKey);
    }

    private String getSessionKey(ServletWebRequest request) {
        return  SESSION_KEY_PREFIX+getValidateCodeType(request).toString().toUpperCase();
    }

}
