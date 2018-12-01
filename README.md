# springmvc
springmvc

springMVC

	Model		模型
	View		视图
	Controller	控制层
	使得模型层数据在视图层和控制层之间传递
  
	如何在视图层和控制层实现数据的传输
  
		1.@Controller 将所有的返回的string类型，都当成jsp页面名返回。
		2.application.properties配置
			spring.mvc.view.suffix=.jsp	配置视图层jsp文件的后缀为.jsp
			spring.mvc.view.prefix=/jsp/	配置视图层jsp文件的前缀为/jsp/  默认在src/main/webapp下创建jsp文件夹
	
	1、接收数据
  
		1.变量 	接收一个参数string id
		2.Map	接收多个变量Map<>
		3.Model	接收多个变量
		
	2、传送数据
  
		1.Model model.addAttribute("key",value);
		2.HttpServletRequest request.setAttribute("key"，value); 若传输为属性使用${key.属性}获取
		3.HttpSession session.setAttribute("key",value); ${sessionScope.key.属性}
		4.RedirectAttributes ra.addAttribute("key",value); 前台使用${msg}获取
		
		重定向到其他路径传输string类型的"redirect:/url"
		
	3、请求
  
		@RequestMapping
			当请求url带有/{xx}时，方法的参数列表需要有“@PathVariable("xx") type xx”接收，在方法中可以通过xx直接使用值
			当前台传输有?xx=xx时，方法的参数列表需要有“@RequestParam("zz") type zz”接收，在方法中可以通过zz直接使用值
		
		@PostMapping==@RequestMapping(method=RequestMethod.POST)
		@GetMApping==@RequestMapping(method=RequestMethod.GET)
		
	3、实体bean中的属性和jsp页面中表单的name属性必须一一对应才能成功传输
  
		实体类中String name, int age;jsp页面中使用name="name",name="age",保证传值的一一对应
		
	4、requestHeader 获取htttp header
	
	5、文件上传 在controller 使用@RequestParam("file") MultipartFile file接收
  
		在jsp中的写法
			<form method="post"、enctype="multipart/form-data">
			<input type="file">
			</form>
		
	6、JSON
  
		@RequestBody和@RestController意义相同，返回JSON
	
		@jsonIgnore
		
	作业：
  
		1.写3个页面，list，add，delete
		2.新增用户User存到session里面
		3.删除用session删掉
		4.列表显示所有用户
		
	7、redirect和forward的区别
  
		redirect	不能传输数据 多次请求
		forward 	一次请求
		
	8、thymeleaf	稳定、版本更新快、spring紧密
  
		1.语法规则
			基于xml（xml相较于html更严格）
			/*<![CDATA[*/
			
		2.绑定数据
			th:object="${实体类}" th:field="*{属性}"
			th:value/text...="${实体类.属性}"
			
		3.渲染页面
			th:class...
		
		4.如何在thymeleaf说明文件中进行功能搜索：判断奇偶行（odd、even）等
		
	9、ajax局部刷新、异步获取
  
		$.ajax({
			type:"post",
			url:"",
			data:{
				实体类属性:表单属性，
				实体类属性:表单属性
			},
			success:function(data){
			
			}
		});
		
	10、国际化
  
		第一种方法
    
			1.application.properties
				spring.messages.basename=messages
				lang=1
        
			2.运行程序类注入bean
				@Value(value = "${spring.messages.basename}")
				private String basename;// 从application.properties中获取basename的值
				@Bean(name = "messageSource")//注入bean
				public ResourceBundleMessageSource getMessageResource() {
					ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
					messageSource.setBasename(basename);
					return messageSource;
				}
				
				@Value("${lang}")
				private String lang;// 从application.properties中获取lang的值
				@Bean // 根据前台传回的数据判断为中文还是英文模式
				public LocaleResolver localeResolver() {
					FixedLocaleResolver slr = new FixedLocaleResolver();
					if (lang.equals("1")) {
						slr.setDefaultLocale(Locale.CHINA);
					} else if (lang.equals("2")) {
						slr.setDefaultLocale(Locale.US);
					}
					return slr;
				}
        
		第二种方法
    
				@Bean // 设置默认为中文模式
				public LocaleResolver localeResolver() {
					SessionLocaleResolver slr = new SessionLocaleResolver();
					// 设置默认区域,
					slr.setDefaultLocale(Locale.CHINA);
					return slr;
				}
			
		第三种方法
    
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
		
		
	11、springmobile	最大特点：封装
  
		实现一个controller跳转2个页面
    
			1.application.properties
      
			spring.mobile.devicedelegatingviewresolver.enabled=true
			spring.mobile.sitepreference.enabled=true
      
			2.运行程序类注入bean
			@Autowired
			DeviceDelegatingViewResolverFactory factory;
			@Autowired
			ThymeleafViewResolver thymeleafViewResolver;
			@Bean // 判断客户端是pc端还是手机端
			public LiteDeviceDelegatingViewResolver liteViewResolver() {
				LiteDeviceDelegatingViewResolver resolver = factory.createViewResolver(thymeleafViewResolver);
				resolver.setOrder(1);
				return resolver;
			}
      
			3.在templates下创建mobile文件夹，创建与templates文件夹下的同名文件，使用controller访问相同名称的页面，当使用手机访问网页时会通过判断选择手机端的页面
		
		http 	header、操作系统
			
	12、Message
  
		1.创建实体类，重载多个构造方法
    
			public class Result {
				public Result(int code, String msg, Object data) {
					super();
					this.code = code;
					this.msg = msg;
					this.data = data;
				}
				
				public Result(int code, String msg) {
					super();
					this.code = code;
					this.msg = msg;
				}

				public Result() {
					super();
				}
			}
      
		2.创建另一个类，创建静态类返回值为实体类的方法，返回多种构造方法。
    
			public static Result Success() {
				return new Result(1, "操作成功");
			}

			//方法写活
			public static Result Success(String msg) {
				return new Result(1, msg);
			}
			
			public static Result Fail() {
				return new Result(-1, "操作失败");
			}
      
		3.在控制层返回result对象的json到前台页面
    
			@RequestMapping("/de")
			@ResponseBody
			public Result delete() {
				return ResultSate.Success();
			}
			
			@RequestMapping("/del")
			@ResponseBody
			public Result deletel() {	
				return ResultSate.Success("操作有问题");//方法写活
			}

					
	作业 ：
		thymeleaf，添加和更新同一页面
		使用ajax请求实现delete功能
		添加button，切换中英文
		
		
	13、表单校验
  
		邮箱、手机号、年龄范围、姓名非空等
		hibernate->validator做校验
    
			1.导入依赖
				<dependency>
					<groupId>org.hibernate</groupId>
					<artifactId>hibernate-validator</artifactId>
				</dependency>
        
			2.要校验的对象加注解@NotEmpty，@NotEmpty，@Max，@Min(value=)等，写message="xxxx"
				指定环境
					@NotEmpty(group="","")
          
			3.前台获取message
			
	14、springMVC原理
	
		1.原理
		
		2.jsp视图解析器
    
			InternalResourceViewResolver
			thymeleafviewResolver
			
		3.变化xml->注解的演变
    
			web.xml没有了，spring4之后
			xml中的<bean></bean>->@Bean
			注解configuration
			springboot  coc  约定大于配置autoConfiguration
