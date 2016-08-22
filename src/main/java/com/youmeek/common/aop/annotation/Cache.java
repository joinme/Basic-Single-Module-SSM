package com.youmeek.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 11:00
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cache {
	int SECONDS_QUERYNOREFRESH = 246801357;
	
	String namespace();
	
	String key() default "";
	
	int keyIndex() default -1;
	
	boolean allParamAsKey() default false;
	
	int index() default -1;
	
	int second() default 0;
	
	Cache.CACHE_METHOD method();
	
	enum CACHE_METHOD {
		Query,
		SaveOrUpdateResult,
		SaveOrUpdateParam,
		Delete;
		
		CACHE_METHOD() {
		}
	}
	
	
}
