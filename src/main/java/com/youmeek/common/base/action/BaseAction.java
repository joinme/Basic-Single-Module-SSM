package com.youmeek.common.base.action;

import com.alibaba.fastjson.JSONObject;
import com.youmeek.common.sso.manager.MockSessionManager;
import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.sso.model.User;
import com.youmeek.common.utils.SpringUtil;
import com.youmeek.common.utils.Util;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/9 17:53
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Repository
public class BaseAction  implements ServletContextAware {
	@Resource
	private MockSessionManager mockSessionManager;
	
	protected ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public String getRealPath() {
		return this.servletContext.getRealPath("/");
	}
	
	public Object getConfig(HttpServletRequest request, String key) {
		return RequestContextUtils.getWebApplicationContext(request).getServletContext().getAttribute(key);
	}
	
	public Object getConfig(String key) {
		return SpringUtil.getContext().getAttribute(key);
	}
	
	protected WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
	}
	
	protected <T> T getBean(Class<T> clazz) {
		return this.getWebApplicationContext().getBean(clazz);
	}
	
	protected Object getBean(String name) {
		return this.getWebApplicationContext().getBean(name);
	}
	
	protected <T> T getBean(String name, Class<T> clazz) {
		return this.getWebApplicationContext().getBean(name, clazz);
	}
	
	
	@ExceptionHandler({Exception.class, Exception.class})
	public void actionExceptionHandler(Throwable ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject jsonObject = null;
		if(ex instanceof InvocationTargetException) {
			ex = ((InvocationTargetException)ex).getTargetException();
		}
		ex.printStackTrace();
		if(request.getRequestURI().contains("/service/")) {
			jsonObject = new JSONObject();
			jsonObject.put("result", "fail");
			jsonObject.put("message", "网络繁忙, 请稍候再试");
			jsonObject.put("isTip", "true");
			jsonObject.put("dealwith", "SUPERJS");
			jsonObject.put("isGoLogin", "false");
			this.responseWrite(response, jsonObject);
		} else {
			response.sendRedirect(Util.getRealBasepath(request) + "/public/500.html");
		}
		
	}
	
	protected void responseWrite(HttpServletResponse response, JSONObject jsonObject) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write(jsonObject.toJSONString());
	}
	
	protected User getUserInfo(String sessionId) throws Exception {
		MockSession mockSession = this.mockSessionManager.getMockSession(sessionId);
		if(mockSession == null) {
			throw new Exception("没有找到登录用户");
		} else {
			return mockSession.getUser();
		}
	}
	
	public String getSessionId(HttpServletRequest request) {
		return (String)request.getAttribute("hjbJsessionId");
	}
	
	public String getCurrUID(HttpServletRequest request) {
		return (String)request.getAttribute("hjbUserUID");
	}
	
	public User getLoginUserFromSession(HttpServletRequest request) throws Exception {
		Object session = request.getAttribute("hjbSessionInRequest");
		return session == null?null:((MockSession)session).getUser();
	}
	
	public MockSession getSessionByUid(String uid) {
		String sessionId = this.mockSessionManager.getSessionIDByUidFromCache(uid);
		return sessionId == null?null:this.mockSessionManager.getMockSession(sessionId);
	}
	public MockSession getSession(HttpServletRequest request) {
		Object session = request.getAttribute("hjbSessionInRequest");
		if(session != null) {
			this.mockSessionManager.getSessionIDByUidFromCache(((MockSession)session).getUid());
			return (MockSession)session;
		} else {
			return null;
		}
	}
}
