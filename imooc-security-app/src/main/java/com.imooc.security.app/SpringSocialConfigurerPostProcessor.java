package com.imooc.security.app;

import com.imooc.security.core.social.ImoocSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 加载Bean的处理器
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    /**
     * APP注册特殊的处理方式，为了不影响Web的行为
     * App加载这个类的时候去设置签约地址
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     * imoocSocialSecurityConfig所在位置类
     * @see com.imooc.security.core.social.SocialConfig
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "imoocSocialSecurityConfig")) {
            ImoocSpringSocialConfigurer configurer = (ImoocSpringSocialConfigurer) bean;
            //设置签约地址
            configurer.signupUrl("/social/signUp");
            return configurer;
        }
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
