package com.imooc.security;

//import com.imooc.security.browser.domain.TUser;
//import com.imooc.security.browser.service.ValidateRepoMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 登录逻辑
 */
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

  /*  @Autowired
    private ValidateRepoMethodService method;
//用户名 admin 密码1
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = method.validateRepoMethod(getClass().getSimpleName(), username);
        if (user != null) {
            logger.info("登录用户名：" + user.getUsername());
//            logger.info("密码："+passwordEncoder.encode(user.getPassword()));
            logger.info("密码：" + user.getPassword());
            logger.info("User---->{}", user.toString());

            return new User(user.getUsername(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
            return null;
        }

        throw new RuntimeException("not found user");
    }




    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("社交登录ID：" + userId);
//        return new SocialUser(userId,user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return new SocialUser(userId, "1", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


    *//**
     * 密码是否匹配
     *
     * @param args
     *//*
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean a = bCryptPasswordEncoder.matches("1", "$2a$10$aZyzW0Fv/4SYAyhKsIcGueTDIjBf0Xfg45KCgpbcXRKaezaiUDIJq");
        System.out.println(a);

    }
*/


    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("表单登录用户名:" + username);
        return buildUser(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        logger.info("设计登录用户Id:" + userId);
        return buildUser(userId);
    }

    private SocialUserDetails buildUser(String userId) {
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode("123456");
        logger.info("数据库密码是:"+password);
        return new SocialUser(userId, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));
    }

}
