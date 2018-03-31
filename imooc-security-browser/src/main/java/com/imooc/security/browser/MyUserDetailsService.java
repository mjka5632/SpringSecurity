package com.imooc.security.browser;

import com.imooc.security.browser.dao.UserRepository;
import com.imooc.security.browser.domain.TUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TUser user = userRepository.findByUsername(username);
//        if (girl != null) {
//            logger.info("success");
//        }
//        System.out.println(girl.getAge());
//        logger.info(girl.toString());
        //此处应该跟数据库获取
//        logger.info("登录用户名："+user.getUsername());
//        logger.info("密码："+passwordEncoder.encode(user.getPassword()));
                logger.info("登录用户名："+username);
        logger.info("密码："+passwordEncoder.encode("1"));
        return new User(username,passwordEncoder.encode("1"), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        //passwordEncoder.encode("123456")应该为注册的时候写入数据库的
//        return User(user.getUsername(),passwordEncoder.encode(user.getUsername()), true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//        return user;
    }


}
