package com.youmeek.common.rpc.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/9/23 16:00
 * @copyright Copyright 2016 . All rights reserved.
 */
public class HttpApiResult<T> {
	private String result;
	private String errorCode;
	private String errorMessage;
	@JSONField( serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect} )
	private T data;
	
	public String getResult() {
		return this.result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public T getData() {
		return this.data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public HttpApiResult() {
	}
	
	public HttpApiResult(String result, String errorCode, String errorMsg) {
		this.result = result;
		this.errorCode = errorCode;
		this.errorMessage = errorMsg;
	}
	
	public boolean isSuccess() {
		return "success".equals(this.result);
	}
}

