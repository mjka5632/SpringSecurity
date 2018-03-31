package com.imooc.web.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.imooc.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.User.UserDetailView;
import com.imooc.dto.User.UserSimpleView;
import com.imooc.dto.UserQueryCondition;

/**
 * 
 * @author ruotian
 *
 */
@RequestMapping("/user")
@RestController
public class UserController {
	@DeleteMapping("/{id:\\d+}")
	public void deleteUser(@PathVariable String id){
		System.out.println("id  "+id);

	}
	@PutMapping("/{id:\\d+}")
	@JsonView(UserSimpleView.class)
	public User updateUser(@Valid @RequestBody User user,BindingResult errors) {
		if (errors.hasErrors()) {
			//errors.getFieldError().getDefaultMessage()只能打出一个错误
//			System.out.println("----------resource------" + resource.getFieldError().getDefaultMessage());
			//打印所有的错误
			errors.getAllErrors().stream().forEach(error -> {
			/*
				可以得到哪个字段有问题，error转成 FieldError
			FieldError fieldError= (FieldError) resource;
				String msg=fieldError.getField()+" "+resource.getDefaultMessage();
				System.out.println(msg);*/
				System.out.println(error.getDefaultMessage());
			});
		}
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthDay());
		user.setId("1");

		return user;

	}

	@PostMapping
	@JsonView(UserSimpleView.class)
	public User createUser(@Valid @RequestBody User user) {
//		if(resource.hasErrors()) {
//			System.out.println("----------resource------"+resource.getFieldError().getDefaultMessage());
//		}
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getBirthDay());
		user.setId("1");

		return user;

	}
	/**
	 * 
	 * @param condition
	 * @param pageable Spring-Data里的对象，对分页有很好的支持
	 * @return
	 */
	@JsonView(UserSimpleView.class)
	@GetMapping
	@ApiOperation("用户查询服务")
	public List<User> query(UserQueryCondition condition,@PageableDefault(page=12,size=5,sort="name,desc")Pageable pageable){
		List<User> list = new ArrayList<>();
		//反射原理打印，ToStringStyle.MULTI_LINE_STYLE 打印出来的时候是多行
		System.out.println(ReflectionToStringBuilder.toString(condition,ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getSort());
		System.out.println(pageable.getPageSize());
		list.add(new User());
		list.add(new User());
		list.add(new User());

		return list;
	}
	@JsonView(UserDetailView.class)
	@GetMapping("/{id:\\d+}")
	public User getInfo(@PathVariable @ApiParam("用户ID") String id) {
//		throw new UserNotExistException(id);
		System.out.println("进入Getinfo 服务");
		User user = new User();
		user.setUsername("tom");
		return user;
		
	}
}
