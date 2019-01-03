package com.imooc.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 * 可以实现 AuthenticationSuccessHandler接口达到相同功能
 * <p>
 * why SimpleUrlAuthenticationFailureHandler？
 * 因为他是Spring默认失败处理器，这样可以直接用他的跳转方法
 * <p>
 * Spring默认失败处理方式：
 * <p>
 * 跳转至默认失败页
 */
@Component
public class ImoocAuthenticationFailureHandle extends SimpleUrlAuthenticationFailureHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        logger.info("登录失败");
        /**
         * 设置返回格式
         * Json
         * or
         * 直接跳转
         */
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            //如果该方法在getWriter()方法被调用之前调用，那么响应的字符编码将仅从给出的内容类型中设置。
            // 该方法如果在getWriter()方法被调用之后或者在被提交之后调用，将不会设置响应的字符编码，
            // 在使用http协议的情况中，该方法设 置 Content-type实体报头。
            //设置返回类型为Json
            response.setContentType("application/json;charset=utf-8");
            //设置状态码500：服务器内部异常
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
        } else {
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
