package com.example;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mobile.DeviceDelegatingViewResolverFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.example.util.MyLocaleResolver;

@SpringBootApplication
public class SpringMvc03Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvc03Application.class, args);
	}

//	@Autowired
//	DeviceDelegatingViewResolverFactory factory;
//
//	@Autowired
//	ThymeleafViewResolver thymeleafViewResolver;
//
//	@Bean // 判断客户端是pc端还是手机端
//	public LiteDeviceDelegatingViewResolver liteViewResolver() {
//		LiteDeviceDelegatingViewResolver resolver = factory.createViewResolver(thymeleafViewResolver);
//		resolver.setOrder(1);
//		return resolver;
//	}

	@Value(value = "${spring.messages.basename}")
	private String basename;// 从application.properties中获取basename的值

	@Bean(name = "messageSource")//注入bean
	public ResourceBundleMessageSource getMessageResource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(basename);
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}

//	@Value("${lang}")
//	private String lang;// 从application.properties中获取lang的值
//
//	@Bean // 根据前台传回的数据判断为中文还是英文模式
//	public LocaleResolver localeResolver() {
//		FixedLocaleResolver slr = new FixedLocaleResolver();
//		if (lang.equals("1")) {
//			slr.setDefaultLocale(Locale.CHINA);
//		} else if (lang.equals("2")) {
//			slr.setDefaultLocale(Locale.US);
//		}
//		return slr;
//	}
	
	

//	@Bean // 设置默认为中文模式
//	public LocaleResolver localeResolver() {
//		SessionLocaleResolver slr = new SessionLocaleResolver();
//		// 设置默认区域,
//		slr.setDefaultLocale(Locale.CHINA);
//		return slr;
//	}
}
