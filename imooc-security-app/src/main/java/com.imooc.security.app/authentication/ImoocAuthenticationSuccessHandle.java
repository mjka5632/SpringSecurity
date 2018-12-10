package com.imooc.security.app.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理器
 *可以实现 AuthenticationSuccessHandler接口达到相同功能
 *
 * why 继承SavedRequestAwareAuthenticationSuccessHandler？
 * 因为他是Spring默认成功处理器，这样可以直接用他的跳转方法
 *
 * Spring默认处理成功方式：
 *
 * 跳转至你第一次请求的URL
 */
@Component
public class ImoocAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //Spring Mvc启动时会自动注册一个ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 封装配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        /*
           设置
          返回格式Json
          or
          直接跳转
         */
        if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
        //如果该方法在getWriter()方法被调用之前调用，那么响应的字符编码将仅从给出的内容类型中设置。
        // 该方法如果在getWriter()方法被调用之后或者在被提交之后调用，将不会设置响应的字符编码，
        // 在使用http协议的情况中，该方法设 置 Content-type实体报头。
        //设置返回类型为Json
        response.setContentType("application/json;charset=utf-8");
        //Authentication对象转为String格式的字符串，作为json返回出去
        response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            //调用父类跳转方法
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
