package com.youmeek.common.sso.utils;

import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.sso.model.User;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/29 18:03
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class SessionUtil {
	private static final ThreadLocal<String> sessionId = new ThreadLocal<>();
	private static final ThreadLocal<MockSession> session = new ThreadLocal<>();
	
	public SessionUtil() {
	}
	
	public static MockSession getSession() {
		return (MockSession)session.get();
	}
	
	public static void bindSession(MockSession mocsession) {
		session.set(mocsession);
	}
	
	public static String getSessionId() {
		return (String)sessionId.get();
	}
	
	public static void bindSessionId(String sid) {
		sessionId.set(sid);
	}
	
	public static User getLoginUser() {
		return getSession().getUser();
	}
	
//	public static CompanyUser getLoginCompanyUser() {
//		return getSession().getCompanyUser();
//	}
}
