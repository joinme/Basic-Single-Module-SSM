//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.youmeek.common.aop;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.aop.annotation.Synchronized;
import com.youmeek.common.utils.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class SynchronizedMethodAspectJ {
	Logger logger = LoggerFactory.getLogger(SynchronizedMethodAspectJ.class);
	@Resource
	private RedisUtil redisUtil;
	
	public SynchronizedMethodAspectJ() {
	}
	
	@Pointcut("@annotation(com.youmeek.common.aop.annotation.Synchronized)")
	public void methodSynchronizedPointcut() {
	}
	
	@Around("methodSynchronizedPointcut()")
	public Object doAroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature joinPointObject = (MethodSignature)pjp.getSignature();
		Method method = joinPointObject.getMethod();
		Synchronized syncAnnotation = (Synchronized)method.getAnnotation(Synchronized.class);
		String namespace = syncAnnotation.namespace();
		int keyIndex = syncAnnotation.lockKeyIndex();
		String key = "".equals(syncAnnotation.lockKey())?null:syncAnnotation.lockKey();
		if(key == null) {
			key = keyIndex == -1?null:pjp.getArgs()[keyIndex] + "";
		}
		
		boolean allParamAsKey = syncAnnotation.allParamAsKey();
		if(key == null) {
			key = allParamAsKey?this.getAllParamAsKey(pjp.getArgs()):null;
		}
		
		if(key == null) {
			throw new Exception("锁配置问题，未找到key");
		} else {
			key = namespace + ".Lock." + key;
			long relockTime = syncAnnotation.forceRelockTimeout();
			long waitTimeout = syncAnnotation.waitTimeout();
			long start = System.currentTimeMillis();
			
			while(true) {
				long value = System.currentTimeMillis();
				int setResult = (int)this.redisUtil.setNX(key, value + "");
				Object result = null;
				if(setResult == 1) {
					this.logger.debug("get lock of [" + key + "] succeed.");
					result = pjp.proceed();
					String var27 = this.redisUtil.getString(key);
					if((value + "").equals(var27)) {
						this.redisUtil.delCache(key);
						this.logger.debug("release lock of [" + key + "] succeed nomally.");
					} else {
						this.logger.error("the lock " + key + " has bean reset from " + value + " to " + var27 + "!!!");
					}
					
					return result;
				}
				
				this.logger.debug("get lock failed, the method has bean locked.");
				long lastLockTime = Long.parseLong(this.redisUtil.getString(key));
				if(value - lastLockTime > relockTime) {
					String sb = this.redisUtil.getSet(key, value + "");
					this.logger.debug("force reset the old dead lock from " + lastLockTime + " to " + value + ", the latest value is " + sb + ".");
					if((lastLockTime + "").equals(sb)) {
						result = pjp.proceed();
						this.redisUtil.delCache(key);
						this.logger.debug("release lock of [" + key + "] succeed.");
						return result;
					}
					
					this.logger.debug("sleep 2 seconds to get lock again.");
					Thread.sleep(2000L);
				} else {
					if(waitTimeout != -1L && System.currentTimeMillis() - start >= waitTimeout) {
						StringBuilder var26 = new StringBuilder();
						Object[] arr$ = pjp.getArgs();
						int len$ = arr$.length;
						
						for(int i$ = 0; i$ < len$; ++i$) {
							Object each = arr$[i$];
							var26.append(JSON.toJSONString(each) + "，");
						}
						
						this.logger.error("同步方法" + pjp.getClass().getName() + "." + method.getName() + "()始终未获取到锁，已经超过线程阻塞时间上限" + waitTimeout + "毫秒，本线程即将丢弃！该方法的key是" + key + " 参数是：【" + var26 + "】");
						throw new Exception("同步方法" + key + "始终未获取到锁，已经超过线程阻塞时间上限" + waitTimeout + "毫秒，本线程即将丢弃！");
					}
					
					this.logger.debug("sleep 2 seconds to get lock again.");
					Thread.sleep(2000L);
				}
			}
		}
	}
	
	private String getAllParamAsKey(Object[] params) {
		if(params != null && params.length != 0) {
			StringBuffer modelBuffer = new StringBuffer();
			Object[] arr$ = params;
			int len$ = params.length;
			
			for(int i$ = 0; i$ < len$; ++i$) {
				Object each = arr$[i$];
				modelBuffer.append("." + each);
			}
			
			return modelBuffer.substring(1).toString();
		} else {
			return null;
		}
	}
}
