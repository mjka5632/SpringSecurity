package com.imooc.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author ruotian 查询条件
 */
public class UserQueryCondition {

	private String username;
	private String xxx;
	@ApiModelProperty("年龄起始值")
	private int ageTo;
	@ApiModelProperty("年龄终止值")

	private int age;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getXxx() {
		return xxx;
	}

	public void setXxx(String xxx) {
		this.xxx = xxx;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}

}
