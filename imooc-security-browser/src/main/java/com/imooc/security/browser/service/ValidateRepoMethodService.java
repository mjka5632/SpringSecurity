package com.imooc.security.browser.service;

import com.imooc.security.browser.dao.UserRepository;
import com.imooc.security.browser.domain.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通过ClassName
 * 选择数据库查询方式
 */
@Service
public class ValidateRepoMethodService {
    @Autowired
    private UserRepository userRepository;


    public TUser validateRepoMethod(String className, String param) {

        if ("MobileDetailsService".equals(className)) {
            TUser userByPassword = userRepository.findByTelPhone(param);
            return userByPassword;

        } else if ("MyUserDetailsService".equals(className)) {
            TUser userByPassword = userRepository.findByUsername(param);
            return userByPassword;
        }
        throw new RuntimeException(".........className........");

    }


}
