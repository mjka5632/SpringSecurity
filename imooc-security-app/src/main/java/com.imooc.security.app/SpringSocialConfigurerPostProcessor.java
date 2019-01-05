package com.imooc.security.app;

import com.imooc.security.core.social.ImoocSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 *加载Bean之后的处理器
 */
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "imoocSocialSecurityConfig")) {
            ImoocSpringSocialConfigurer configurer = (ImoocSpringSocialConfigurer) bean;
            //设置注册地址
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }
}
