package com.imooc.security.browser;

import com.imooc.security.browser.domain.TUser;
import com.imooc.security.browser.service.ValidateRepoMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 用户名密码登录
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ValidateRepoMethodService method;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = method.validateRepoMethod(getClass().getSimpleName(), username);
        if (user != null) {
            logger.info("登录用户名：" + user.getUsername());
//            logger.info("密码："+passwordEncoder.encode(user.getPassword()));
            logger.info("密码：" + user.getPassword());
            logger.info("User---->{}", user.toString());

            return new User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        }

        user.setUsername("查无此人");


        logger.info("User---->{}", user.toString());
        return user;
    }

    /**
     * 密码是否匹配
     *
     * @param args
     */
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean a = bCryptPasswordEncoder.matches("1", "$2a$10$aZyzW0Fv/4SYAyhKsIcGueTDIjBf0Xfg45KCgpbcXRKaezaiUDIJq");
        System.out.println(a);

    }
}
