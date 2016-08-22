package com.youmeek.common.sso.model;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 10:37
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MockSession implements Serializable {
	private static final long serialVersionUID = 5808302936326283212L;
	@Id
	private String jsessionID;
	private String uid;
	private int userType;
	private Date loginTime;
	private String loginIP;
	private String reasonOfKick;
	private Set<String> roleIds;
	private Map<String, Object> attributesMap;
	private User user;
	
	public MockSession() {
	}
	
	public String getJsessionID() {
		return this.jsessionID;
	}
	
	public void setJsessionID(String jsessionID) {
		this.jsessionID = jsessionID;
	}
	
	public String getUid() {
		return this.uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public Date getLoginTime() {
		return this.loginTime;
	}
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getLoginIP() {
		return this.loginIP;
	}
	
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
	public String getReasonOfKick() {
		return this.reasonOfKick;
	}
	
	public void setReasonOfKick(String reasonOfKick) {
		this.reasonOfKick = reasonOfKick;
	}
	
	public Set<String> getRoleIds() {
		return this.roleIds;
	}
	
	public void setRoleIds(Set<String> roleIds) {
		this.roleIds = roleIds;
	}
	
	public Map<String, Object> getAttributesMap() {
		return this.attributesMap;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getUserType() {
		return this.userType;
	}
	
	public void setUserType(int userType) {
		this.userType = userType;
	}
	
	public static long getSerialversionuid() {
		return 5808302936326283212L;
	}
	
	public void addAttribute(String key, Object value) {
		if(this.attributesMap == null) {
			this.attributesMap = new HashMap();
		}
		
	}
	
	public void removeAttribute(String key) {
		if(this.attributesMap != null) {
			this.attributesMap.remove(key);
		}
		
	}
}
