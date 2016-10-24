package com.youmeek.common.rpc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/9/23 16:05
 * @copyright Copyright 2016 . All rights reserved.
 */
public class HttpRPC {
	private String serviceCode;
	private String version;
	private String beanName;
	private String clazz;
	private String interfaceClazz;
	private Map<String, Object> args = new HashMap<>();
	
	public HttpRPC() {
	}
	
	public String getBeanName() {
		return this.beanName;
	}
	
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public String getClazz() {
		return this.clazz;
	}
	
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public Map<String, Object> getArgs() {
		return this.args;
	}
	
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}
	
	public String getInterfaceClazz() {
		return this.interfaceClazz;
	}
	
	public void setInterfaceClazz(String interfaceClazz) {
		this.interfaceClazz = interfaceClazz;
	}
	
	public String getServiceCode() {
		return this.serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
}