package com.youmeek.common.exception;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/10/10 11:41
 * @copyright Copyright 2016 . All rights reserved.
 */

public class BaseException extends Exception {
	private static final long serialVersionUID = -150304443047320288L;
	private String errorCode;
	private String errorMessage;
	private boolean isSuper = false;
	
	public BaseException() {
	}
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public BaseException(String errorCode, String errorMessage) {
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public boolean isSuper() {
		return this.isSuper;
	}
	
	public void setSuper(boolean isSuper) {
		this.isSuper = isSuper;
	}
}
