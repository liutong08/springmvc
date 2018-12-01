package com.example.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.User;

@Controller // 将返回的string类型通过扫描找到对应的jsp文件
public class HelloController {

	@RequestMapping("/hello") // 当页面访问/hello时跳转到hello页面
	public String hello() {
		return "hello";
	}

	@RequestMapping("/user") // 当页面访问/hello时跳转到user页面
	public String user() {
		return "user";
	}

	@RequestMapping("/uplo") // 当页面访问/hello时跳转到upload页面
	public String upload() {
		return "upload";
	}

	@RequestMapping("/up") // 重定向redirect:xxx至@RequestMapping("xxx")页面
	public String up() {
		return "redirect:/hello";
	}

	@RequestMapping("/redirectTest") // 使用RedirectAttribute传输数据到前台
	public String redirectTest(RedirectAttributes ra) {
		ra.addAttribute("msg", "success");
		return "redirect:hello";// 重定向至请求路径hello,传递信息写在地址栏中
	}

	@PostMapping("/info") // @PostMapping==@RequestMapping(method=RequestMethod.POST)
	public String info(User user, HttpServletRequest request) {
		System.out.println(user.toString());// 从前台页面自动匹配实体类属性
		request.setAttribute("user", user);// 使用request传输实体类
		return "user-info";// 传输至user-info.jsp页面
	}

	@PostMapping("/info1/{age}") // 当地址后有/{xx}时，方法需要使用@PathVariable("xx") type xx，接收并按照前台传递的类型进行接受
	public String info1(@PathVariable("age") int age, User user, Model model) {
		System.out.println(age);
		System.out.println(user.toString());
		model.addAttribute("user", user);// 使用model将对象返回到前台
		return "user-info";
	}

	@RequestMapping("/upload") // 文件的上传使用MultipartFile
	// @RequestParam获取前台传输的属性，required表示是否是必要的
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, Model model) {
		String path = "F:/data/";// 给定文件存放的地址
		String filename = file.getOriginalFilename();// 获取上传文件的名称

		System.out.println(path + "---" + filename);
		File targetFile = new File("F:/data/");
		if (!targetFile.exists()) {// 判断指定目录是否存在
			System.out.println("创建：" + targetFile.isDirectory());// 判断是否为文件夹
			System.out.println(targetFile.mkdirs());// 创建多层目录
		}
		try {
			file.transferTo(new File(path + filename));// 将图片写入指定位置
			System.out.println(file.getSize());
			model.addAttribute("msg1", path + filename);// 像前台传输model
			model.addAttribute("msg", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "upload-result";
	}
}
