//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.youmeek.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Synchronized {
	String namespace();
	
	String lockKey() default "";
	
	int lockKeyIndex() default -1;
	
	boolean allParamAsKey() default false;
	
	long waitTimeout() default -1L;
	
	long forceRelockTimeout() default 600000L;
}
