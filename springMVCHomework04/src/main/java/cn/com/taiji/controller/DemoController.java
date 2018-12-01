package cn.com.taiji.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.annotation.MyController;
import org.springframework.annotation.MyRequestMapping;

@MyController
@MyRequestMapping("/hello")
public class DemoController {
	@MyRequestMapping("/index")
	public String hello(HttpServletRequest request,HttpServletResponse response) {
		return "hello";
	}
}
