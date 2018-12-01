package com.example.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.example.group.Add;
import com.example.group.Update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@NotNull(groups= {Add.class},message="后台验证：ID不能为空！")
	private Integer id;
	@NotEmpty(groups= {Add.class,Update.class},message="后台验证：姓名不能为空！")
	private String name;
	@Min(groups= {Add.class,Update.class},value=18,message="后台验证：年龄未满18周岁！")
	@Max(groups= {Add.class,Update.class},value=100,message="后台验证：岁数太大！")
	@NotNull(groups= {Add.class},message="后台验证：年龄不能为空！")
	private Integer age;
	@NotEmpty(groups= {Add.class,Update.class},message="后台验证：手机号码不能为空！")
	private String mobile;
	private String path;
}
