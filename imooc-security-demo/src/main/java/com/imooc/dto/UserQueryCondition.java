package com.imooc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ruotian 查询条件
 */
@Data
public class UserQueryCondition {

	private String username;
	private String xxx;
	@ApiModelProperty("年龄起始值")
	private int ageTo;
	@ApiModelProperty("年龄终止值")
	private int age;


}
