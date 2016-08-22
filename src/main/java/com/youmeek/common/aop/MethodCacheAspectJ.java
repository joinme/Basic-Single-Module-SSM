package com.youmeek.common.aop;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.aop.annotation.Cache;
import com.youmeek.common.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 11:07
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

@Aspect
//@Component
public class MethodCacheAspectJ {
	Logger logger = LoggerFactory.getLogger(MethodCacheAspectJ.class);
	@Resource
	RedisUtil redisUtil;
	
	public MethodCacheAspectJ() {
	}
	
	@Pointcut("@annotation(com.youmeek.common.aop.annotation.Cache)")
	public void methodCachePointcut() {
	}
	
	@Around("methodCachePointcut()")
	public Object doAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();
		Cache cacheAnnotation = method.getAnnotation(Cache.class);
		Cache.CACHE_METHOD methodName = cacheAnnotation.method();
		String namespace = cacheAnnotation.namespace();
		int second = cacheAnnotation.second();
		int pojoIndex = cacheAnnotation.index();
		int keyIndex = cacheAnnotation.keyIndex();
		String key = "".equals(cacheAnnotation.key()) ? null : cacheAnnotation.key();
		if (key == null) {
			key = keyIndex == -1 ? null : pjp.getArgs()[keyIndex] + "";
		}
		
		if (key == null) {
			key = pojoIndex == -1 ? null : this.getPOJOKey(pjp.getArgs()[pojoIndex]);
		}
		
		boolean allParamAsKey = cacheAnnotation.allParamAsKey();
		if (key == null) {
			key = allParamAsKey ? this.getAllParamAsKey(pjp.getArgs()) : null;
		}
		
		if (key == null) {
			throw new Exception("缓存配置问题，未找到key");
		} else {
			Object returnObj = null;
			if (methodName.equals(Cache.CACHE_METHOD.Query)) {
				returnObj = this.getCachedObj(namespace, key, method, pjp, second);
				this.logger.debug("query cache " + namespace + "." + key + " result：" + returnObj);
			} else if (methodName.equals(Cache.CACHE_METHOD.Delete)) {
				this.deleteModel(namespace, key);
				this.logger.debug("delete cache " + namespace + "." + key);
			} else if (methodName.equals(Cache.CACHE_METHOD.SaveOrUpdateParam)) {
				this.saveOrUpdateParam(namespace, key, pjp.getArgs()[pojoIndex], second);
				this.logger.debug("save or update param at " + pojoIndex + " to cache with：" + namespace + "." + key);
			} else if (methodName.equals(Cache.CACHE_METHOD.SaveOrUpdateResult)) {
				returnObj = this.saveOrUpdateResult(namespace, key, method.getReturnType(), pjp, second);
				this.logger.debug("save or update result to cache with：" + namespace + "." + key);
			}
			
			if (returnObj == null) {
				returnObj = pjp.proceed();
			}
			
			return returnObj;
		}
	}
	
	private String getPOJOKey(Object model) throws Exception {
		if (model == null) {
			return null;
		} else {
			Field[] fields = model.getClass().getDeclaredFields();
			if (fields != null) {
				for (int i = 0; i < fields.length; ++i) {
					Field field = fields[i];
					if (field.isAnnotationPresent(Id.class)) {
						Method m = model.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), new Class[0]);
						Object keyId = m.invoke(model, new Object[0]);
						return keyId + "";
					}
				}
			}
			
			return null;
		}
	}
	
	private String getAllParamAsKey(Object[] params) {
		if (params != null && params.length != 0) {
			StringBuffer modelBuffer = new StringBuffer();
			Object[] arr$ = params;
			int len$ = params.length;
			
			for (int i$ = 0; i$ < len$; ++i$) {
				Object each = arr$[i$];
				modelBuffer.append("." + each);
			}
			
			return modelBuffer.substring(1).toString();
		} else {
			return null;
		}
	}
	
	private Object getCachedObj(String namespace, String keyPart, Method method, ProceedingJoinPoint pjp, int seconds) throws Throwable {
		String key = null;
		key = namespace + "." + keyPart.toString();
		return this.setCacheObject(pjp, this.redisUtil.getString(key), key, method, seconds);
	}
	
	private Object setCacheObject(ProceedingJoinPoint pjp, String cacheObjStr, String key, Method method, int seconds) throws Throwable {
		Class valueType = method.getReturnType();
		Object cacheObj = null;
		if (cacheObjStr == null) {
			if (pjp.getArgs() != null) {
				cacheObj = pjp.proceed(pjp.getArgs());
			} else {
				cacheObj = pjp.proceed();
			}
			
			if (seconds == 246801357) {
				return cacheObj;
			}
			
			if (cacheObj == null) {
				return null;
			}
			
			if (valueType == String.class) {
				this.redisUtil.saveOrUpdateString(key, cacheObj + "", seconds);
				this.logger.debug("save " + key + " in " + seconds + " seconds succeed. value is" + cacheObj);
			} else {
				this.redisUtil.saveOrUpdateString(key, JSON.toJSONString(cacheObj), seconds);
				this.logger.debug("save " + key + " in " + seconds + " seconds succeed. value is" + cacheObj);
			}
		} else {
			if (String.class == valueType) {
				cacheObj = cacheObjStr;
			} else if (List.class == method.getReturnType()) {
				Type returnType = method.getGenericReturnType();
				if (returnType instanceof ParameterizedType) {
					Type[] types = ((ParameterizedType) returnType).getActualTypeArguments();
					cacheObj = JSON.parseArray(cacheObjStr, (Class) types[0]);
				} else {
					cacheObj = JSON.parseObject(cacheObjStr, valueType);
				}
			} else {
				cacheObj = JSON.parseObject(cacheObjStr, valueType);
			}
			
			if (seconds != 246801357) {
				this.redisUtil.expire(key, seconds);
			}
		}
		
		return cacheObj;
	}
	
	private void saveOrUpdateParam(String namespace, String key, Object model, int second) throws Exception {
		if (model instanceof String) {
			this.setCacheByNamespace(namespace, key, model + "", second);
		} else {
			this.setCacheByNamespace(namespace, key, JSON.toJSONString(model), second);
		}
		
	}
	
	private Object saveOrUpdateResult(String namespace, String key, Class valueType, ProceedingJoinPoint pjp, int second) throws Throwable {
		Object result = pjp.proceed();
		String valueString = null;
		if (valueType == String.class) {
			valueString = result + "";
		} else {
			valueString = JSON.toJSONString(result);
		}
		
		this.setCacheByNamespace(namespace, key, valueString, second);
		return result;
	}
	
	private void deleteModel(String namespace, String keyPart) throws Exception {
		if (!this.isContains(keyPart)) {
			this.redisUtil.delCache(namespace + "." + keyPart);
		} else {
			Set keys = this.redisUtil.keys(namespace + "." + keyPart);
			if (keys != null && !keys.isEmpty()) {
				Iterator i$ = keys.iterator();
				
				while (i$.hasNext()) {
					String each = (String) i$.next();
					this.redisUtil.delCache(each);
				}
			}
			
		}
	}
	
	private void setCacheByNamespace(String namespace, Object keyId, String model, int second) throws Exception {
		if (keyId != null && namespace != null) {
			this.redisUtil.saveOrUpdateString(namespace + "." + keyId, model, second);
		}
		
	}
	
	private boolean isContains(String keys) {
		return keys.contains("*") ? true : (keys.contains("?") ? true : (keys.contains("[") ? true : keys.contains("]")));
	}
}
