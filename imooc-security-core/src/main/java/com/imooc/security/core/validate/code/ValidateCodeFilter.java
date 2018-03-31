package com.imooc.security.core.validate.code;

import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 图形验证码的过滤器
 * <p>
 * OncePerRequestFilter 保证过滤器只会被调用一次
 * InitializingBean 其他参数组装完毕初始化urls这个值
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    /**
     *处理校验错误信息
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * Url匹配工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
     * 存放所有需要校验验证码的URL
     */
    private Map<String,ValidateCodeType> urlMap=new HashMap<>();

    /**
     * 初始化要拦截的URL信息
     * 重写这个InitializingBean接口的方法
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addURLTOMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addURLTOMap(securityProperties.getCode().getSms().getUrl(),ValidateCodeType.SMS);
    }

    private void addURLTOMap(String urlString,ValidateCodeType type) {
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");

            for (String url : urls) {
                urlMap.put(url,type);
            }

        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求（"+request.getRequestURI()+")中的验证码类型"+type);

            try{

                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(request,response));
                logger.info("验证码校验通过");

            }catch (ValidateCodeException exception){
                authenticationFailureHandler.onAuthenticationFailure(request,response,exception);
                return;
            }

        }
        filterChain.doFilter(request,response);
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request){
        ValidateCodeType result=null;
        logger.info(request.getMethod());
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if(pathMatcher.match(url,request.getRequestURI())){
                    result=urlMap.get(url);
                }
            }
        }
        return result;
    }
}

