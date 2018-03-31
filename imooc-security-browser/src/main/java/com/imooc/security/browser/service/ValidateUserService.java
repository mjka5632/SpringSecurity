package com.imooc.security.browser.service;

import com.imooc.security.browser.dao.UserRepository;
import com.imooc.security.browser.domain.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ValidateUserService {
    @Autowired
    private UserRepository userRepository;

    public TUser validatorUser(String username){
        TUser user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        return null;
    }




}

