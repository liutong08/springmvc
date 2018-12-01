package com.example.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.entity.User;
import com.example.util.Result;
import com.example.util.ResultSate;

@Controller
public class ResultController {

	// 跳转message页面
	@RequestMapping("/mes")
	public String mes() {
		return "message";
	}

	// 在页面显示u的信息
	@RequestMapping("/json")
	@ResponseBody
	public User newuser() {
		User u = new User();
		u.setId(1);
		u.setName("张三");
		u.setAge(25);
		u.setMobile("156546545");
		return u;
	}

	@RequestMapping("/de")
	@ResponseBody
	public Result delete() {
//		Map map = new HashMap();
//		map.put("code", 1);
//		map.put("msp", "success");
//		
		return ResultSate.Success();
	}

	@RequestMapping("/del")
	@ResponseBody
	public Result deletel() {
//		Map map = new HashMap();
//		map.put("code", 1);
//		map.put("msp", "success");
//		
		return ResultSate.Success("操作有问题");
	}
	
	


	@RequestMapping("/changeSessionLanauage") // 通过"http://localhost:8080/changeSessionLanauage?lang=xx"来指定当前页面的语言模式
	public String changeSessionLanauage(HttpServletRequest request, String lang) {

		if ("zh".equals(lang)) {
			// 代码中即可通过以下方法进行语言设置
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					new Locale("zh", "CN"));
		} else if ("en".equals(lang)) {
			// 代码中即可通过以下方法进行语言设置
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
					new Locale("en", "US"));
		}
		return "message";
	}

	@RequestMapping("/mo")
	public String momo() {
		return "auto";
	}

}
