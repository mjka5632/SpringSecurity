package com.imooc.security.app;

import com.imooc.security.app.social.openid.OpenIdAuthenticationSecurityConfig;
import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableResourceServer
/**
 * @EnableResourceServer 当做资源服务器
 */
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private AuthenticationFailureHandler imoocAuthenticationFailureHandler;

    @Autowired
    private SpringSocialConfigurer imoocSocialSecurityConfig;

    @Autowired
    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                //登录页面跳转
                //.loginPage("/imooc-Sign.html")
                //跳转
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                //登录提交的地址
                //实际在设置UsernamePasswordAuthenticationFilter这个过滤器的处理路径DEFAULT_LOGIN_PROCESSING_URL_FORM
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                //处理成功返回
                .successHandler(imoocAuthenticationSuccessHandler)
                //处理失败返回
                .failureHandler(imoocAuthenticationFailureHandler);
        //导入配置
        http.apply(validateCodeSecurityConfig)
                .and()
                //Social
                .apply(openIdAuthenticationSecurityConfig)
                //--------------身份认证
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(imoocSocialSecurityConfig)
                .and()
                //--------对请求做授权(下面都是对授权的配置)
                .authorizeRequests()
                //url无需认证
                .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,
                        securityProperties.getBrowser().getSignUpUrl(),
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSignOutUrl(),
                        SecurityConstants.DEFAULT_SESSION_INVALID,
                        SecurityConstants.DEFAULT_USER_INFO,
                        SecurityConstants.DEFAULT_USER_REGIST,
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+ "/*",
                        SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                        SecurityConstants.DEFAULT_SOCIAL_SIGNUP
                                ).permitAll()
                //任何请求
                .anyRequest()

                //都需要做身份认证
                .authenticated()
                .and()
                //跨站请求防护
                .csrf().disable()

        ;
    }
}
