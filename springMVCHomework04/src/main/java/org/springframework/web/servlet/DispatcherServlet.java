package org.springframework.web.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.annotation.MyController;
import org.springframework.annotation.MyRequestMapping;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/*", loadOnStartup = 1
, initParams = {@WebInitParam(name = "base-package", value = "cn.com.taiji.controller")})
public class DispatcherServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 扫描的基包
     */
    private String basePackage = "";
    
    /**
     * 扫描的基包下所有的带包路径的全限定名称
     */
    private List<String> packageClassNames = new ArrayList<>();
    /**
     * 实例存放在map中
     */
    private Map<String, Object> instanceMap = new HashMap<>();
    /*
     * key=url value=method
     */
    private Map<String, Method> urlMethodMap = new HashMap<>();
    
    /**
     * key=方法,value=类全限定名
     */
    private Map<Method, String> methodStringMap = new HashMap<>();
    
    /**
     * 扫描基包
     */
    
    @Override
    public void init(ServletConfig config) throws ServletException {

        try {
            //获取包路径
            basePackage = config.getInitParameter("base-package");
            
            scanBasePackage(basePackage);
            //把加了出借Controller,Service,Repository实例化
            instance(packageClassNames);
            //bean注册--IOC和注入
            //autowired();
            //获取controller，执行controller类的某个方法
            handlerUrlMapping();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
    
    private void instance(List<String> packageNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("controllers is "+packageNames);
    	if (packageNames.size() < 1) {
            return;
        }
        for (String packageName : packageNames) {
            Class c = Class.forName(packageName);
            //如果类上有Controller注解
            if (c.isAnnotationPresent(MyController.class)) {
                //获取注解
                    instanceMap.put(packageName, c.newInstance());
                    //nameMap.put(packageName, controllerName);
            }
        }
    }
    
    private void handlerUrlMapping() throws ClassNotFoundException {
        if (packageClassNames.size() < 1) {
            return;
        }
        for (String name : packageClassNames) {
            Class c = Class.forName(name);
            //遍历所有Controller类
            if (c.isAnnotationPresent(MyController.class)) {
                //该类的所有方法
                Method[] methods = c.getMethods();
                StringBuilder baseUrl = new StringBuilder();
                //再坚持该类上是否有RequestMapping注解
                if (c.isAnnotationPresent(MyRequestMapping.class)) {
                    MyRequestMapping requestMapping = (MyRequestMapping) c.getAnnotation(MyRequestMapping.class);
                    baseUrl.append(requestMapping.value());
                }
                //遍历该类的所有方法，如果方法上有RequestMapping注解就拼装url，最后把url和方法放在一个map中
                for (Method m : methods) {
                    if (m.isAnnotationPresent(MyRequestMapping.class)) {
                        MyRequestMapping requestMapping = m.getAnnotation(MyRequestMapping.class);
                        baseUrl = baseUrl.append(requestMapping.value());

                        urlMethodMap.put(baseUrl.toString(), m);
                        methodStringMap.put(m, name);
                    }
                }
            }
        }
        System.out.println("===============methodStringMap===============");
        System.out.println(urlMethodMap);
        System.out.println("===============methodStringMap===============");
        System.out.println(methodStringMap);
    }

  
    private void scanBasePackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        File basePackageFile = new File(url.getPath());
        File[] childFIle = basePackageFile.listFiles();
        for (File file : childFIle) {
            if (file.isDirectory()) {
                scanBasePackage(basePackage + "." + file.getName());
            } else if (file.isFile()) {
            	packageClassNames.add(basePackage + "." + file.getName().split("\\.")[0]);
            }
        }
        System.out.println(packageClassNames);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.replace(contextPath, "");

        System.out.println("comming");
        
        Method method = urlMethodMap.get(path);
        if (method != null) {
            String packageName = methodStringMap.get(method);
            method.setAccessible(true);
            try {
                Object result = method.invoke(instanceMap.get(packageName),req,resp);
                resp.setCharacterEncoding("utf-8");
                resp.getWriter().print("<html><body>"+result+"</body></html>");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

	
}
