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

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface API {
	String serviceCode();
	
	String version();
	
	String[] bodyMapping() default {};
}