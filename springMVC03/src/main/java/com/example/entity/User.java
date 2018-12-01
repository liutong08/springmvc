package com.example.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer id;
	@NotEmpty(message="姓名不能为空")
	private String name;
	@Min(value=18,message="未满18周岁")
	@Max(value=100,message="岁数太大")
	private Integer age;
	@NotEmpty(message="手机号码不能为空")
	private String mobile;
	private String path;
}
