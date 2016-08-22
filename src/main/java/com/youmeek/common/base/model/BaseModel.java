package com.youmeek.common.base.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/8/9 18:14
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class BaseModel  implements Serializable {
	private static final long serialVersionUID = -8117718605726713675L;
	@JSONField(
			serialize = false
	)
	private Map setterMap;
	@JSONField(
			serialize = false
	)
	private Date createTime;
	@JSONField(
			serialize = false
	)
	private Date updateTime;
	@JSONField(
			serialize = false
	)
	private String createUser;
	@JSONField(
			serialize = false
	)
	private String updateUser;
	private String result;
	private Object message;
	private Map data;
	
	public <T, E> Map<T, E> getSetterMap() {
		return this.setterMap;
	}
	
	public synchronized void initSetterMap() {
		if(null == this.setterMap) {
			this.setterMap = new HashMap();
		}
		
	}
	
	public synchronized void initData() {
		if(null == this.data) {
			this.data = new HashMap();
		}
		
	}
	
	public <T, E> Map<T, E> getData() {
		return this.data;
	}
	
	public Object getDataValue(String key) {
		return this.data == null?null:this.data.get(key);
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getCreateUser() {
		return this.createUser;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getUpdateUser() {
		return this.updateUser;
	}
	
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public <T, E> void setSetterMap(Map<T, E> setterMap) {
		this.setterMap = setterMap;
	}
	
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public Object getMessage() {
		return this.message;
	}
	
	public void setMessage(Object message) {
		this.message = message;
	}
	
	public BaseModel() {
	}
	
	public BaseModel(String result, String msg) {
		this.result = result;
		this.message = msg;
	}
	
	public BaseModel(String result, Object msg) {
		this.result = result;
		this.message = msg;
	}
	
	public synchronized void addData(String key, Object value) {
		this.initData();
		this.data.put(key, value);
	}
	
	public boolean isSuccess() {
		return "success".equals(this.result);
	}
	
	public void setData(Map data) {
		this.data = data;
	}
}
