package com.example.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.example.entity.User;
import com.example.group.Add;
import com.example.group.Update;
import com.example.util.Result;
import com.example.util.ResultSate;

@Controller
public class UserController {
	private static List<User> list = new ArrayList<User>();

	// 列表显示
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		HttpSession session = request.getSession();
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

	// 带id,thymeleaf跳转更新页面
	@RequestMapping("/gotoUpdateth/{id}")
	public String gotoUpdateth(@PathVariable("id") Integer id, Model model) {
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId().equals(id)) {
				model.addAttribute("user", u);
			}
		}
		return "add";
	}

	// 跳转添加页面
	@RequestMapping("/gotoAdd")
	public String gotoAdd(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "add";
	}

	// ajax跳转删除
	@ResponseBody
	@RequestMapping(value = "/gotoDel", method = RequestMethod.POST)
	public Result gotoDel(User user, HttpServletRequest request) {
		System.out.println(user.toString());
		int id = user.getId();
		Iterator<User> it = list.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId().equals(id)) {
				it.remove();
			}
		}
		return ResultSate.Success();
	}

	// 添加用户
	@RequestMapping("/add")
	public String add(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			@Validated({Add.class}) User user, BindingResult result, Model model) {
		HttpSession session = request.getSession();
		String str;// 创建跳转页面String
		if (result.hasFieldErrors()) {// 判断如果有错误信息
			user.setId(null);
			str = "add";// 跳转新的新增页面
		} else {// 如果没有错误信息
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
					file.transferTo(new File(path + filename));
					System.out.println(file.getSize());
					System.out.println(path + filename);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setPath("ico/" + filename);
			}
			list.add(user);
			session.setAttribute("userList", list);
			str = "list";// 跳转用户列表页面
		}
		return str;
	}

	// 更新用户
	@RequestMapping("/update")
	public String update(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			@Validated({Update.class}) User user, BindingResult result, Model model) {
		HttpSession session = request.getSession();
		String str;// 创建跳转页面String
		if (result.hasFieldErrors()) {// 判断如果有错误信息
			str = "add";// 跳转新的新增页面
		} else {// 如果没有错误信息
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
					file.transferTo(new File(path + filename));
					System.out.println(file.getSize());
					System.out.println(path + filename);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Iterator<User> it = list.iterator();
				while (it.hasNext()) {
					User u = it.next();
					if (u.getId().equals(user.getId())) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
						u.setName(user.getName());
						u.setAge(user.getAge());
						u.setMobile(user.getMobile());
						u.setPath("ico/" + filename);
					}
				}

			} else {
				Iterator<User> it = list.iterator();
				while (it.hasNext()) {
					User u = it.next();
					if (u.getId().equals(user.getId())) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
						u.setName(user.getName());
						u.setAge(user.getAge());
						u.setMobile(user.getMobile());
					}
				}

			}
			str = "list";
		}
		session.setAttribute("userList", list);
		return str;
	}

	// 添加用户
//	@RequestMapping("/add")
//	public String add1(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
//			@Validated User user, BindingResult result, Model model) {
//		HttpSession session = request.getSession();
//
//		String str;// 创建跳转页面String
//		
////		List<FieldError> errors = result.getFieldErrors(); // 获取vaildated验证，以list封装
////		System.out.println(errors);// 打印验证所有信息
//
//		if (result.hasFieldErrors()) {// 判断如果有错误信息
////			User user1 = new User();// 创建新的User，覆盖前台传过来的User，避免因为id问题跳转更新页面
////			model.addAttribute("user", user1);
//			user.setId(null);
////
////			String[] err = new String[errors.size()];// 创建数组存放错误信息
////			for (int i = 0; i < errors.size(); i++) {// 进行遍历
////				String message = errors.get(i).getDefaultMessage();// 获取遍历的错误信息
////				System.out.println(message);// 输出错误信息
////				err[i] = message;// 将错误信息赋值给数组中
////			}
////			System.out.println(Arrays.toString(err));
////			model.addAttribute("error", err);// 像前台传数组err
//			str = "add";// 跳转新的新增页面
//		} else {// 如果没有错误信息
//			if (!file.isEmpty()) {
//				String path = request.getSession().getServletContext().getRealPath("ico/");
//				String filename = file.getOriginalFilename();
//				System.out.println(path + "---" + filename);
//				File targetFile = new File(path);
//				if (!targetFile.exists()) {
//					System.out.println("创建：" + targetFile.isDirectory());
//					System.out.println(targetFile.mkdirs());
//				}
//				try {
//					file.transferTo(new File(path + filename));
//					System.out.println(file.getSize());
//					System.out.println(path + filename);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				user.setPath("ico/" + filename);
//			}
//			list.add(user);
//			session.setAttribute("userList", list);
//			str = "list";// 跳转用户列表页面
//		}
//		return str;
//	}

//	// 更新用户
//	@RequestMapping("/update")
//	public String update1(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
//			@Validated User user, BindingResult result, Model model) {
//		HttpSession session = request.getSession();
//
//		String str;// 创建跳转页面String
////		List<FieldError> errors = result.getFieldErrors(); // 获取vaildated验证，以list封装
////		System.out.println(errors);// 打印验证所有信息
////
////		if (result.hasFieldErrors()) {// 判断如果有错误信息
////			Iterator<User> it = list.iterator();
////			while (it.hasNext()) {
////				User u = it.next();
////				if (u.getId().equals(user.getId())) {
////					System.out.println(u.toString());
////					model.addAttribute("user", u);
////				}
////			}
////			String[] err = new String[errors.size()];// 创建数组存放错误信息
////			for (int i = 0; i < errors.size(); i++) {// 进行遍历
////				String message = errors.get(i).getDefaultMessage();// 获取遍历的错误信息
////				System.out.println(message);// 输出错误信息
////				err[i] = message;// 将错误信息赋值给数组中
////			}
////			System.out.println(Arrays.toString(err));
////			model.addAttribute("error", err);// 像前台传数组err
////			str = "add";// 跳转新的新增页面
////		} else {// 如果没有错误信息
//
//		if (!file.isEmpty()) {
//			String path = request.getSession().getServletContext().getRealPath("ico/");
//			String filename = file.getOriginalFilename();
//
//			System.out.println(path + "---" + filename);
//			File targetFile = new File(path);
//			if (!targetFile.exists()) {
//				System.out.println("创建：" + targetFile.isDirectory());
//				System.out.println(targetFile.mkdirs());
//			}
//			try {
//				file.transferTo(new File(path + filename));
//				System.out.println(file.getSize());
//				System.out.println(path + filename);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			Iterator<User> it = list.iterator();
//			while (it.hasNext()) {
//				User u = it.next();
//				if (u.getId().equals(user.getId())) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
//					u.setName(user.getName());
//					u.setAge(user.getAge());
//					u.setMobile(user.getMobile());
//					u.setPath("ico/" + filename);
//				}
//			}
//
//		} else {
//			Iterator<User> it = list.iterator();
//			while (it.hasNext()) {
//				User u = it.next();
//				if (u.getId().equals(user.getId())) {// 通过遍历，通过id找到要修改的User，对其属性进行修改
//					u.setName(user.getName());
//					u.setAge(user.getAge());
//					u.setMobile(user.getMobile());
//				}
//			}
//
//		}
//		str = "list";
////		}
//		session.setAttribute("userList", list);
//		return str;
//	}

//	// 带id跳转更新页面
//	@RequestMapping("/gotoUpdate/{id}")
//	public String gotoUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Iterator<User> it = list.iterator();
//		while (it.hasNext()) {
//			User u = it.next();
//			if (u.getId().equals(id)) {
//				session.setAttribute("user", u);
//			}
//		}
//		return "update";
//	}

//	// 带id删除
//	@RequestMapping("/gotoDelete/{id}")
//	public String gotoDelete(@PathVariable("id") Integer id, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		Iterator<User> it = list.iterator();
//		while (it.hasNext()) {
//			User u = it.next();
//			if (u.getId().equals(id)) {
//				String name = u.getName();
//				session.setAttribute("name", name);
//				it.remove();
//			}
//		}
//		session.setAttribute("msg", "删除成功");
//		return "delete";
//	}

}
