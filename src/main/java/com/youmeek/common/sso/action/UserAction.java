package com.youmeek.common.sso.action;

import com.youmeek.common.Constant;
import com.youmeek.common.base.action.BaseAction;
import com.youmeek.common.base.model.BaseModel;
import com.youmeek.common.sso.manager.MockSessionManager;
import com.youmeek.common.sso.manager.UserManager;
import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.sso.model.User;
import com.youmeek.common.utils.Encode;
import com.youmeek.common.utils.Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/9 17:51
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Controller
public class UserAction extends BaseAction {
	
	@Resource
	private UserManager userManager;
	@Resource
	private MockSessionManager mockSessionManager;
	
	private String cookieDomain = null;
	
	@RequestMapping(value = "/login/service/do", method = RequestMethod.POST)
//	@ResponseBody
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
		BaseModel baseModel = userManager.queryLoginUser(user);
		ModelAndView modelAndView = new ModelAndView();
		String result = baseModel.getResult();
		if (!"success".equals(result)) {
			modelAndView.addObject("errorMsg", baseModel.getMessage());
			modelAndView.setViewName("redirect:../../login.html");
			return modelAndView;
		}
		user = (User) baseModel.getMessage();
		MockSession session = mockSessionManager.createUserMockSession(user, super.getSessionId(request), Util.getRealIP(request));
		mockSessionManager.updateSession(session);
		mockSessionManager.updateUIDwithSession(user.getUserId(), super.getSessionId(request));
		
		request.getSession().setAttribute("user", user);
		
		String str = Encode.shaAndMd5Encode(user.getUserId(), super.getSessionId(request), "Lc~\\=>BWz-Ed'<)z>7F)n(S^3A<U1oC)N'/Zp?EvxzDe={rthyern~Cqb}1?");
		boolean secure = false;
		if(cookieDomain == null){
			String domain = (String) super.getConfig("domain");
			int index = domain.indexOf(".");
			//-1 非域名环境
			cookieDomain = index>-1?domain.substring(index):null;
		}
		//生产环境强制secure
		secure = cookieDomain!=null;
		Util.setHttpOnlyCookie(response, "_C3", str, "/", cookieDomain, -1, secure);
		/*
		return successMsg;//成功
		*/
		modelAndView.addObject("user", user);
//			modelAndView.setViewName("showUser.jsp");
//		modelAndView.setViewName("redirect:../../show.jsp");
		modelAndView.setViewName("show.jsp");
		return modelAndView;
	}
	
	/**
	 * 登录状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/service/status", method = RequestMethod.GET)
	@ResponseBody
	public BaseModel isLogined(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		String dateString = new DateTime().toString("yyyy-MM-dd");
		int value = platformVisitManager.getPcPV(dateString);
		if (value == 0) {
			platformVisitManager.savePcPV(dateString, 1);
		} else {
			platformVisitManager.savePcPV(dateString, ++value);
		}
		*/
		if (super.getSession(request) != null && super.getLoginUserFromSession(request) != null) {
			BaseModel model = new BaseModel(Constant.SUCCESS, super.getLoginUserFromSession(request).getUserName());
			model.addData("lastLogin", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(super.getLoginUserFromSession(request).getLastloginTime()));
			return model;
		}
		return new BaseModel(Constant.FAIL, null);
	}
	
	/**
	 * 安全退出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "login/service/out", method = RequestMethod.GET)
//	@ResponseBody
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MockSession session = super.getSession(request);
		User sessionUser = super.getLoginUserFromSession(request);
		if(session != null){
			mockSessionManager.removeSession(session.getJsessionID());
			mockSessionManager.removeUIDwithSession(session.getUid());
			request.getSession().removeAttribute("user");
		}
		
		if(cookieDomain == null){
			String domain = (String) super.getConfig("domain");
			int index = domain.indexOf(".");
			cookieDomain = index>-1?domain.substring(index):null;
		}
		boolean secure = cookieDomain!=null;
		Util.setHttpOnlyCookie(response, Constant.JSESSIONID_COOKIE_AKEY, "", "/", cookieDomain, -1, secure);
		Util.setHttpOnlyCookie(response, Constant.JSESSIONID_COOKIE_BKEY, "", "/", cookieDomain, -1, secure);
		Util.setHttpOnlyCookie(response, Constant.JSESSIONID_COOKIE_CKEY, "", "/", cookieDomain, -1, secure);
		Util.setNoCacheHeader(response);
		/*
		if(session == null){
			return new BaseModel(Constant.SUCCESS, 1);
		}
		return sessionUser != null ? new BaseModel(Constant.SUCCESS, 2) : new BaseModel(Constant.SUCCESS, 3);
		*/
		
		return "../../index.jsp";
	}
	
}
