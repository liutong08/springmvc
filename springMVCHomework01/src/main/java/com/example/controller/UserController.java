package com.example.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.User;

@Controller
public class UserController {
	private static List<User> list = new ArrayList<User>();

	// 列表显示
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
//		list.add(new User(1, "zhangsan", 25, "18752462187", "D:/ico/1.jpg"));
//		list.add(new User(2, "lisi", 23, "1873254987", "D:/ico/2.jpg"));
//		list.add(new User(3, "wangwu", 26, "14523659874", "D:/ico/3.jpg"));
		HttpSession session = request.getSession();
		session.setAttribute("userList", list);
		return "list";
	}

	// 添加用户
	@RequestMapping("/add")
	public String add(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			User user) {
		HttpSession session = request.getSession();

		String path = request.getSession().getServletContext().getRealPath("ico/");
		String filename = file.getOriginalFilename();

		System.out.println(path + "---" + filename);
		File targetFile = new File(path);
		if (!targetFile.exists()) {
			System.out.println("创建：" + targetFile.isDirectory());
			System.out.println(targetFile.mkdirs());
		}
		try {
			file.transferTo(new File(path + filename));
			System.out.println(file.getSize());
			System.out.println(path + filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.setPath("ico/" + filename);

		list.add(new User(user.getId(), user.getName(), user.getAge(), user.getMobile(), user.getPath()));
		session.setAttribute("userList", list);
		return "list";
	}

	// 返回用户列表页面
	@RequestMapping("/backtoList")
	public String backtoList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("userList", list);
		return "list";
	}

	// 更新用户
	@RequestMapping("/update")
	public String update(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			User user) {
		HttpSession session = request.getSession();
		if (!file.isEmpty()) {

			String path = request.getSession().getServletContext().getRealPath("ico/");
			String filename = file.getOriginalFilename();

			System.out.println(path + "---" + filename);
			File targetFile = new File(path);
			if (!targetFile.exists()) {
				System.out.println("创建：" + targetFile.isDirectory());
				System.out.println(targetFile.mkdirs());
			}
			try {
				file.transferTo(new File("ico/" + filename));
				System.out.println(file.getSize());
				System.out.println(path + filename);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Iterator<User> it = list.iterator();
			while (it.hasNext()) {
				User u = it.next();
				if (u.getId() == user.getId()) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
					u.setName(user.getName());
					u.setAge(user.getAge());
					u.setMobile(user.getMobile());
					u.setPath("ico/" + filename);
				}
			}
		}else {
			Iterator<User> it = list.iterator();
			while (it.hasNext()) {
				User u = it.next();
				if (u.getId() == user.getId()) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
					u.setName(user.getName());
					u.setAge(user.getAge());
					u.setMobile(user.getMobile());
				}
			}
		}
		session.setAttribute("userList", list);
		return "list";
	}

	// 带id跳转更新页面
	@RequestMapping("/gotoUpdate/{id}")
	public String gotoUpdate(@PathVariable("id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId() == id) {
				session.setAttribute("user", u);
			}
		}
		return "update";
	}

	// 带id删除
	@RequestMapping("/gotoDelete/{id}")
	public String gotoDelete(@PathVariable("id") int id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId() == id) {
				it.remove();
			}
		}
		session.setAttribute("msg", "success");
		return "delete";
	}

	// 跳转添加页面
	@RequestMapping("/gotoAdd")
	public String gotoAdd() {
		return "add";
	}

	// 跳转更新页面
	@RequestMapping("/gotoUpdate")
	public String gotoUpdate() {
		return "update";
	}
}
