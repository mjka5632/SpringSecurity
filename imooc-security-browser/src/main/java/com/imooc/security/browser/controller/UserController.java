package com.imooc.security.browser.controller;

import com.imooc.security.browser.dao.UserRepository;
//import com.imooc.security.browser.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class  UserController {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
  /*  @GetMapping
    public User login(){
        User girl = userRepository.getOne(1);
        if (girl != null) {
            logger.info("success");
        }
        System.out.println(girl.toString());
        userDetailsService.loadUserByUsername(girl);
    }*/
}
