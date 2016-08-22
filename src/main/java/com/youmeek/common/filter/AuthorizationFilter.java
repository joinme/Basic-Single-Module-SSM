package com.youmeek.common.filter;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.sso.manager.ResourceManager;
import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/29 18:15
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Component
public class AuthorizationFilter implements Filter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private ResourceManager resourceManager;
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String pass = (String)request.getAttribute("pass");
		if("1".equals(pass)) {
			filterChain.doFilter(request, servletResponse);
		} else {
			Object mock = request.getAttribute("hjbSessionInRequest");
			if(mock == null) {
				this.returnFailedMsg(request, response, "您已登录超时，请重新登录。", true);
			} else {
				String requestURI = request.getRequestURI();
//				此处是获取 http method + @ + requestURI 为了权限验证  权限以此种格式存在数据库中的
				String resourceURI = Util.getRealMethod(request) + "@" + requestURI;
				this.logger.debug("auth-----" + resourceURI);
				Set<String> res = null;
				MockSession session = (MockSession)mock;
				try {
					res = new HashSet<>(resourceManager.getResourcesByUserId(session.getUid()));
				} catch (Exception e) {
					this.logger.error("获取用户资源失败");
					e.printStackTrace();
				}
				
				if(!this.auth(res, resourceURI)) {
					response.sendError(404, "矮油，木有找到请求的资源！");
				} else {
					filterChain.doFilter(request, servletResponse);
				}
			}
		}
	}
	
	
	public boolean auth(Set<String> res, String resourceURI) {
		if(res != null && !res.isEmpty()) {
//			遍历 resource
			for (String re : res) {
				if (this.pathMatcher.match(re.trim(), resourceURI)) {
					return true;
				}
				this.logger.info("------------------------------认证失败：URI=" + resourceURI + ", 当前用户已有的资源=" + res);
				this.logger.info("------------------------------开发人员检查该角色是否已配置此URI-------------------------------");
			}
			/*
			Iterator i$ = res.iterator();
			String each;
			do {
				if(!i$.hasNext()) {
					this.logger.info("------------------------------认证失败：URI=" + resourceURI + ", 当前用户已有的资源=" + res);
					this.logger.info("------------------------------开发人员检查该角色是否已配置此URI-------------------------------");
					return false;
				}
				
				each = (String)i$.next();
			} while(!this.pathMatcher.match(each.trim(), resourceURI));
			return true;
			*/
		}
		return false;
	}
	
	private void returnFailedMsg(HttpServletRequest request, HttpServletResponse response, String msg, boolean isGotoLogin) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		HashMap map = new HashMap();
		map.put("result", "fail");
		map.put("message", msg);
		map.put("dealwith", "SUPERJS");
		map.put("isGoLogin", isGotoLogin + "");
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(map));
	}
	
	@Override
	public void destroy() {
		
	}
}
