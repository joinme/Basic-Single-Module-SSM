package com.youmeek.common.rpc.exception;

import com.youmeek.common.exception.BaseException;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/10/10 11:45
 * @copyright Copyright 2016 . All rights reserved.
 */
public class ApiConfigurationException extends BaseException {
	public ApiConfigurationException() {
	}
	
	public ApiConfigurationException(String errorMessage) {
		super(errorMessage);
		this.setErrorMessage(errorMessage);
	}
	
	public ApiConfigurationException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}
	
	public ApiConfigurationException(Exception e) {
		super(e.getMessage(), e);
	}
	
	public ApiConfigurationException(String errorMessage, Exception e) {
		super(errorMessage, e);
	}
}