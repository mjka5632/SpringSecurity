package com.imooc.security.browser.dao;

import com.imooc.security.browser.domain.TUser;
//import com.imooc.security.browser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<TUser,Integer>{
    TUser findByUsername(String username);
}
