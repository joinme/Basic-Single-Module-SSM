package com.youmeek.common.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/10/10 10:30
 * @copyright Copyright 2016 . All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface ApiParam {
	ApiParam.API_VD_TYPE validationType() default ApiParam.API_VD_TYPE.VALIDATION_DISABLED;
	
	String dateFormatter() default "";
	
	public static enum API_VD_TYPE {
		PARAM_NOT_NULL,
		VALIDATION_DISABLED;
		
		private API_VD_TYPE() {
		}
	}
}
