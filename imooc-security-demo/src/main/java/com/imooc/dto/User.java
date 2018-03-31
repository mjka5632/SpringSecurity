package com.imooc.dto;

import java.util.Date;

import com.imooc.validator.MyConstraint;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.Past;

public class User {
	@MyConstraint(message = "校验测试")
	private String username;

	@NotBlank(message = "密码不为null")
	private String password;
	
	private String id;
	@Past(message = "生日必须为过去的时间")
	private Date birthDay;
	
	@JsonView(UserSimpleView.class)
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public interface UserSimpleView {
	};

	public interface UserDetailView extends UserSimpleView {
	};
	
	@JsonView(UserSimpleView.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@JsonView(UserSimpleView.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonView(UserDetailView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
