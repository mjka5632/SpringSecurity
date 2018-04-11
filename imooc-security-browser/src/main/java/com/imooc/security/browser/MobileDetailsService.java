package com.imooc.security.browser;

import com.imooc.security.browser.domain.TUser;
import com.imooc.security.browser.service.ValidateRepoMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 手机号验证码登录
 */
@Component
public class MobileDetailsService implements UserDetailsService {
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private ValidateRepoMethodService method;



    @Override
    public UserDetails loadUserByUsername(String telPhone) throws UsernameNotFoundException {
        logger.info(getClass().getSimpleName());
        System.out.println(getClass().getSimpleName());


        TUser user = method.validateRepoMethod(getClass().getSimpleName(),telPhone);
        if (user != null) {
            logger.info("登录用户名："+user.getUsername());
            logger.info("User---->{}",user.toString());
            return user;

        }

        throw new InternalAuthenticationServiceException("无法获取用户信息");

    }


}
