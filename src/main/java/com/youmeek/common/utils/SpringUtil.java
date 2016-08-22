//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.youmeek.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Map;

@Component
public class SpringUtil implements ServletContextAware {
	private static ServletContext context;
	
	public SpringUtil() {
	}
	
	private static WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(context);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getWebApplicationContext().getBean(clazz);
	}
	
	public static Object getBean(String name) {
		return getWebApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(String name, Class<T> clazz) {
		return getWebApplicationContext().getBean(name, clazz);
	}
	
	public static <T> Map<String, T> getBeans(Class<T> clazz) {
		return getWebApplicationContext().getBeansOfType(clazz);
	}
	
	public void setServletContext(ServletContext servletContext) {
		context = servletContext;
	}
	
	public static ServletContext getContext() {
		return context;
	}
	
	public static void setContext(ServletContext context) {
		context = context;
	}
	
	public static Object getContextAttr(String key) {
		return getContext().getAttribute(key);
	}
	
	public static String getContextAttrAsString(String key) {
		return (String)getContext().getAttribute(key);
	}
	
	public static void setContextAttr(String key, Object value) {
		getContext().setAttribute(key, value);
	}
}
