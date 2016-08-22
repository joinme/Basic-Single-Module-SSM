package com.youmeek.common.filter;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.sso.manager.MockSessionManager;
import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.sso.utils.SessionUtil;
import com.youmeek.common.utils.Encode;
import com.youmeek.common.utils.SecurityUtil;
import com.youmeek.common.utils.SpringUtil;
import com.youmeek.common.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 10:32
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Component
public class CheckLoginFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String cookieDomain = null;
	private boolean cookieSecure = false;
	private String[] passPaths = null;
	private String domain = null;
	private String loginPage = null;
	@Resource
	private MockSessionManager mockSessionManager;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext servletContext = filterConfig.getServletContext();
		this.passPaths = ((String[])servletContext.getAttribute("paths"));
		//获取期望用户在浏览器地址栏中输入的域名地址， 如 www.yanhong.net /  127.0.0.1, 如不是自己期望的 则在filter中就会拦截回去
		this.domain = SpringUtil.getContextAttrAsString("domain");
		this.loginPage = SpringUtil.getContextAttrAsString("loginPage");
		int index = this.domain.indexOf(".");
		this.cookieDomain = index > -1 ? this.domain.substring(index):null;
		this.cookieSecure = this.cookieDomain != null;
		
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String requestURI = request.getRequestURI();
		String referer = request.getHeader("Referer");
		this.logger.debug("path--------------------------" + requestURI);
		// contextPath(项目名) + servletPath（项目名后面的url） = requestUrl
		String contextPath = request.getContextPath();
		String basePath = Util.getRealBasepath(request);
		String loginPage = basePath + "/" + this.loginPage;
		String browserAgent = request.getHeader("User-Agent");
		if(browserAgent != null) {
			browserAgent = browserAgent.replaceAll("（", "(").replaceAll("）", ")").replace("WIFI", "").replace("2G", "").replace("3G+", "");
		}
		//获取当前的cookies
		Cookie[] cookies = request.getCookies();
		String jsessionID = null;
		String seed = null;
		String uid = null;
		if(cookies != null) {
			int cookiesLength = cookies.length;
			for (Cookie cookie : cookies) {
				if("_C1".equals(cookie.getName())) {
					jsessionID = cookie.getValue();
				} else if("_C2".equals(cookie.getName())) {
					seed = cookie.getValue();
				} else if("_C3".equals(cookie.getName())) {
					uid = cookie.getValue();
				}
			}
		}
//		判断 jsessionID  seed 是否为空
		if(!Util.isNullOrBlank(jsessionID) && !Util.isNullOrBlank(seed)) {
			//判断 seed 和 browserAgent (h2和agent) 编码能否得出 jsessionID（h1）
			String seedAgent = Encode.shaAndMd5Encode(seed, browserAgent, ",o2`lEydgry)A5:$7*qW*jv*`pX=");
			if(!seedAgent.equals(jsessionID)) {
				//如果不能 就清 cookie 记录错误日志 返回错误信息并跳转登录页面
				this.clear(response);
				this.logger.error("A/B NOT MATCH!!!!!!!!! expacted A=" + seedAgent);
				this.loggerFakeRequest(request, browserAgent, jsessionID, seed, uid);
				this. jumpOrTip(requestURI, contextPath, loginPage, request, response, filterChain, basePath);
				return;
			}
			//判断 uid（h3） 是否 为空
			if(!Util.isNullOrBlank(uid)) {
//				根据 jsessionID 从缓存中取出 当前用户的session对象
				MockSession mockSession = this.mockSessionManager.getMockSession(jsessionID);
//				不存在的话 可能是 超时  或者 未登录
				if(mockSession == null) {
//					就清 cookie 记录错误日志 返回错误信息并跳转登录页面
					this.clear(response);
					this.logger.debug("-----------登录超时或未登录1,跳转登录页面");
					this.jumpOrTip(requestURI, contextPath, loginPage, request, response, filterChain, basePath);
					return;
				}
//				存在的话  加密 缓存 session的 uid  jessionId
				String uidJsessionid = Encode.shaAndMd5Encode(mockSession.getUid(), mockSession.getJsessionID(), "Lc~\\=>BWz-Ed\'<)z>7F)n(S^3A<U1oC)N\'/Zp?EvxzDe={rthyern~Cqb}1?");
//				判断缓存中的是否和 此次请求中的 uid（h3）相等
				if(!uidJsessionid.equals(uid)) {
//					如果不相等 就清 cookie 记录错误日志 返回错误信息并跳转登录页面
					this.clear(response);
					this.logger.error("encoded C and AB NOT MATCH!!!!!!!!! expacted C=" + uidJsessionid);
					this.loggerFakeRequest(request, browserAgent, jsessionID, seed, uidJsessionid);
					this.jumpOrTip(requestURI, contextPath, loginPage, request, response, filterChain, basePath);
					return;
				}
				//将 mockSession 放入 localThread 里面 存放
				SessionUtil.bindSession(mockSession);
				//将 userid mockSession 放入 request attr 里面 便于 下一个filter 获取
				request.setAttribute("hjbUserUID", mockSession.getUid());
				request.setAttribute("hjbSessionInRequest", mockSession);
				this.mockSessionManager.getSessionIDByUidFromCache(mockSession.getUid());
			}
		} else {
			seed = Util.randonString(32).toUpperCase();
			jsessionID = Encode.shaAndMd5Encode(seed, browserAgent, ",o2`lEydgry)A5:$7*qW*jv*`pX=");
			Util.setHttpOnlyCookie(response, "_C1", jsessionID, "/", this.cookieDomain, -1L, this.cookieSecure);
			Util.setHttpOnlyCookie(response, "_C2", seed, "/", this.cookieDomain, -1L, this.cookieSecure);
		}
		
		SessionUtil.bindSessionId(jsessionID);
		request.setAttribute("hjbJsessionId", jsessionID);
		if(this.isPass(contextPath, requestURI)) {
			request.setAttribute("pass", "1");
			filterChain.doFilter(request, servletResponse);
		} else if(request.getAttribute("hjbSessionInRequest") == null) {
			this.logger.debug("-----------登录超时或未登录2，跳转页面或前台跳转");
			this.jumpOrTip(requestURI, contextPath, loginPage, request, response, filterChain, basePath);
		} else {
			filterChain.doFilter(request, servletResponse);
		}
	}
	
	private boolean isIndex(String contextPath, String requestURI) {
		return requestURI.equals(contextPath) || requestURI.equals(contextPath + "/") || 
				contextPath.endsWith("index.html") || requestURI.equals(contextPath + "/index") || 
				requestURI.equals(contextPath + "/index/");
	}
	
	private boolean isPass(String contextPath, String requestURI) {
		if(!requestURI.endsWith(".js") && !requestURI.endsWith(".css") && !this.isIndex(contextPath, requestURI)) {
			for (String passPath : passPaths) {
				if(requestURI.startsWith(contextPath + passPath)) {
					return true;
				}
			}
			/*
			String[] arr$ = this.passPaths;
			int len$ = arr$.length;
			
			for(int i$ = 0; i$ < len$; ++i$) {
				String each = arr$[i$];
				if(requestURI.startsWith(contextPath + each)) {
					return true;
				}
			}
			*/
			return false;
		} else {
			return true;
		}
	}
	
	private void returnFailedMsg(HttpServletRequest request, HttpServletResponse response, String msg, boolean isTip, boolean isGotoLogin) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		HashMap<String, String> map = new HashMap<>();
		map.put("result", "fail");
		map.put("message", msg);
		map.put("isTip", isTip + "");
		map.put("dealwith", "SUPERJS");
		map.put("isGoLogin", isGotoLogin + "");
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(map));
	}
	
	private void jumpOrTip(String requestURI, String contextPath, String loginPage, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String basePath) throws IOException, ServletException {
		if(this.isPass(contextPath, requestURI)) {
			request.setAttribute("pass", "1");
			filterChain.doFilter(request, response);
		} else if(requestURI.contains("/service/")) {
			this.returnFailedMsg(request, response, "", false, true);
		} else {
			StringBuilder sb = new StringBuilder();
			if(!request.getParameterMap().isEmpty()) {
				Map<String, String[]> parameterMap = request.getParameterMap();
				for (String key : parameterMap.keySet()) {
					if (!"goto".equals(key)) {
						sb.append("&" + key + "=" + SecurityUtil.xssStrim(request.getParameter(key + "")));
					}
				}
				
				/*
				Iterator url = request.getParameterMap().keySet().iterator();
				while (url.hasNext()) {
					Object key = url.next();
					if (!"goto".equals(key)) {
						sb.append("&" + key + "=" + SecurityUtil.xssStrim(request.getParameter(key + "")));
					}
				}
				*/
				
			}
			
			String redirectUrl = loginPage + "?goto=" + basePath + request.getRequestURI();
			if(sb.length() > 0) {
				redirectUrl = redirectUrl + "?" + sb.toString().substring(1);
			}
			
			this.logger.debug("---即将跳转登录页面：" + redirectUrl);
			response.sendRedirect(redirectUrl);
		}
	}
	
	
	private void clear(HttpServletResponse response) {
		Util.setHttpOnlyCookie(response, "_C1", "", "/", this.cookieDomain, -1L, this.cookieSecure);
		Util.setHttpOnlyCookie(response, "_C2", "", "/", this.cookieDomain, -1L, this.cookieSecure);
		Util.setHttpOnlyCookie(response, "_C3", "", "/", this.cookieDomain, -1L, this.cookieSecure);
	}
	
	private void loggerFakeRequest(HttpServletRequest request, String browserAgent, String jsessionID, String seed, String uid) {
		this.logger.error("--------------------客户端伪造了cookie，--------------------------requestURI=" + request.getRequestURI());
		this.logger.error("客户端IP:" + Util.getRealIP(request));
		this.logger.error("浏览器标头:" + browserAgent);
		this.logger.error("请求URI(POST方法，见参数的_method):" + request.getMethod() + " @ " + request.getRequestURI());
		this.logger.error("cookie中（JSESSIONID_COOKIE_AKEY）:" + jsessionID);
		this.logger.error("cookie中（JSESSIONID_COOKIE_BKEY）:" + seed);
		this.logger.error("cookie中（JSESSIONID_COOKIE_CKEY）:" + uid);
		StringBuilder sb = new StringBuilder();
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		for (String key : parameterMap.keySet()) {
			sb.append(key + "=");
			sb.append(request.getParameter(key + "") + ", ");
		}
		/*
		for (Map.Entry<String, String[]> paramEntry : parameterMap.entrySet()) {
			String key = paramEntry.getKey();
			sb.append(key + "=");
			sb.append(request.getParameter(key + "") + ", ");
		}
		
		
		Iterator i$ = request.getParameterMap().keySet().iterator();
		while(i$.hasNext()) {
			Object key = i$.next();
			sb.append(key + "=");
			sb.append(request.getParameter(key + "") + ", ");
		}
		*/
		this.logger.error("请求参数:" + sb);
	}
	
	@Override
	public void destroy() {
		
	}
}
