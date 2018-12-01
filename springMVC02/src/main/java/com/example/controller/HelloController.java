package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.User;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public String hello(Model model){
		model.addAttribute("hel","hello everyone");
		return "user";
	}
	
	@PostMapping("/info")
	public String info(User user,Model model) {
		System.out.println(user.toString());
		model.addAttribute("user", user);
		return "user-info";
	}
}
