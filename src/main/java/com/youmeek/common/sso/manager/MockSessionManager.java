package com.youmeek.common.sso.manager;

import com.youmeek.common.aop.annotation.Cache;
import com.youmeek.common.sso.model.MockSession;
import com.youmeek.common.sso.model.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 10:47
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Component
public class MockSessionManager {
	public MockSessionManager() {
	}
	
	public MockSession createUserMockSession(User user, String jSessionID, String ip) throws Exception {
		MockSession session = new MockSession();
		session.setUid(user.getUserId());
		session.setJsessionID(jSessionID);
		session.setLoginTime((new DateTime()).toDate());
		session.setLoginIP(ip);
		session.setUser(user);
		HashSet roles = new HashSet();
		roles.add(user.getUserType() + "");
		session.setRoleIds(roles);
		session.setUserType(user.getUserType());
		return session;
	}
	/*
	public MockSession createCompanyUserMockSession(CompanyUser compabyUser, String jSessionID, String ip) throws Exception {
		MockSession session = new MockSession();
		session.setUid(compabyUser.getUserId());
		session.setJsessionID(jSessionID);
		session.setLoginTime((new DateTime()).toDate());
		session.setLoginIP(ip);
		session.setCompanyUser(compabyUser);
		HashSet roles = new HashSet();
		roles.add(compabyUser.getUserType() + "");
		session.setRoleIds(roles);
		session.setUserType(compabyUser.getUserType());
		return session;
	}
	*/
	
	
	@Cache(namespace = "yanhong:privilege.mocksession",method = Cache.CACHE_METHOD.Query,keyIndex = 0,second = 1800)
	public MockSession getMockSession(String jSessionID) {
		return null;
	}
	
	@Cache(namespace = "yanhong:privilege.mocksession", method = Cache.CACHE_METHOD.SaveOrUpdateParam, index = 0, second = 1800)
	public void updateSession(MockSession mockSession) {
	}
	
	@Cache(namespace = "yanhong:privilege.mocksession", method = Cache.CACHE_METHOD.Delete, keyIndex = 0)
	public void removeSession(String jSessionID) {
	}
	
	@Cache(
			namespace = "yanhong:privilege.uidFindSession",
			method = Cache.CACHE_METHOD.SaveOrUpdateParam,
			keyIndex = 0,
			index = 1,
			second = 1800
	)
	public void updateUIDwithSession(String uid, String jsessionID) {
	}
	
	@Cache(
			namespace = "yanhong:privilege.uidFindSession",
			method = Cache.CACHE_METHOD.Query,
			keyIndex = 0,
			second = 1800
	)
	public String getSessionIDByUidFromCache(String uid) {
		return null;
	}
	
	@Cache(
			namespace = "yanhong:privilege.uidFindSession",
			method = Cache.CACHE_METHOD.Delete,
			keyIndex = 0
	)
	public void removeUIDwithSession(String uid) {
	}
	
	
	
	
}
