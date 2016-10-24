package com.youmeek.common.rpc.util;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.rpc.annotation.API;
import com.youmeek.common.rpc.annotation.ApiParam;
import com.youmeek.common.rpc.exception.ApiConfigurationException;
import com.youmeek.common.rpc.exception.ApiValidationException;
import com.youmeek.common.rpc.model.HttpApiResult;
import com.youmeek.common.rpc.model.HttpRPC;
import com.youmeek.common.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/10/9 11:26
 * @copyright Copyright 2016 . All rights reserved.
 */

public class RpcUtil {
	static Logger logger = LoggerFactory.getLogger(RpcUtil.class);
	
	public RpcUtil() {
	}
	
	public static HttpApiResult localRpc(int type, HttpRPC rpc, String serviceCode, String version, Object handler, Class clazz) {
		HttpApiResult result = new HttpApiResult();
		
		try {
			Method[] methods = clazz.getDeclaredMethods();
			int length = methods.length;
			
			for(int i = 0; i < length; ++i) {
				Method method = methods[i];
				API api = (API)method.getAnnotation(API.class);
				if(api != null && api.serviceCode().equals(serviceCode) && api.version().equals(version)) {
					Class[] paramClazz = method.getParameterTypes();
					Annotation[][] annos = method.getParameterAnnotations();
					Object[] params = new Object[paramClazz.length];
					String[] names = api.bodyMapping();
					
					for(int parami = 0; parami < paramClazz.length; ++parami) {
						SimpleDateFormat rtn = null;
						ApiParam apiAnno = null;
						if(annos != null && annos.length > parami) {
							Annotation[] value = annos[parami];
							int annoLen = value.length;
							
							for(int annoi = 0; annoi < annoLen; ++annoi) {
								Annotation eachAnno = value[annoi];
								if(eachAnno instanceof ApiParam) {
									apiAnno = (ApiParam)eachAnno;
									if(!Util.isNullOrBlank(apiAnno.dateFormatter())) {
										rtn = new SimpleDateFormat(apiAnno.dateFormatter());
									}
								}
							}
						}
						
						String paramValue = null;
						if(type == 1) {
							paramValue = rpc.getArgs().get("" + parami) + "";
						} else if(type == 2) {
							paramValue = rpc.getArgs().get(names[parami]) + "";
						}
						
						if(paramValue != null && !"null".equals(paramValue)) {
							if(paramClazz[parami].equals(String.class)) {
								params[parami] = paramValue;
							} else if(!paramClazz[parami].equals(Boolean.TYPE) && !paramClazz[parami].equals(Boolean.class)) {
								if(!paramClazz[parami].equals(Integer.TYPE) && !paramClazz[parami].equals(Integer.class)) {
									if(!paramClazz[parami].equals(Character.TYPE) && !paramClazz[parami].equals(Character.class)) {
										if(!paramClazz[parami].equals(Float.TYPE) && !paramClazz[parami].equals(Float.class)) {
											if(!paramClazz[parami].equals(Long.TYPE) && !paramClazz[parami].equals(Long.class)) {
												if(!paramClazz[parami].equals(Double.TYPE) && !paramClazz[parami].equals(Double.class)) {
													if(paramClazz[parami].equals(BigDecimal.class)) {
														params[parami] = new BigDecimal(paramValue);
													} else if(paramClazz[parami].equals(Date.class)) {
														if(rtn == null) {
															logger.error("日期参数的Api接口未指定格式化字符串! serviceCode=" + serviceCode + ", version=" + version);
															throw new ApiConfigurationException("3008", "API Business Configuration Error,Please Contact us!");
														}
														
														params[parami] = paramValue == null?null:rtn.parse(paramValue);
													} else {
														params[parami] = paramValue == null?null: JSON.parseObject(paramValue, paramClazz[parami]);
													}
												} else {
													params[parami] = Double.valueOf(Double.parseDouble(paramValue));
												}
											} else {
												params[parami] = Long.valueOf(Long.parseLong(paramValue));
											}
										} else {
											params[parami] = Float.valueOf(Float.parseFloat(paramValue));
										}
									} else {
										params[parami] = Character.valueOf(paramValue.charAt(0));
									}
								} else {
									params[parami] = Integer.valueOf(Integer.parseInt(paramValue));
								}
							} else {
								params[parami] = Boolean.valueOf(Boolean.parseBoolean(paramValue));
							}
						} else {
							if(apiAnno != null && ApiParam.API_VD_TYPE.PARAM_NOT_NULL.equals(apiAnno.validationType())) {
								if(type == 1) {
									throw new ApiValidationException("The Param Index of " + (parami + 1) + " Are Required!");
								}
								
								if(type == 2) {
									throw new ApiValidationException("The Param Named " + names[parami] + " Are Required!");
								}
							}
							
							params[parami] = null;
						}
					}
					
					Class returnType = method.getReturnType();
					Object methodResult = method.invoke(handler, params);
					result.setResult("success");
					if(!returnType.equals(Void.TYPE)) {
						if(returnType.equals(String.class)) {
							result.setData(methodResult + "");
						} else {
							result.setData(methodResult);
						}
					}
					
					return result;
				}
			}
		} catch (Exception var24) {
			Exception ex = var24;
			var24.printStackTrace();
			logger.error("API本地服务发生异常： serviceCode=," + serviceCode + "version=" + version + ",clazz=" + clazz.getName() + " ERROR:" + var24.getMessage());
			if(var24 instanceof InvocationTargetException) {
				ex = (Exception)((InvocationTargetException)var24).getTargetException();
			}
			
			if(ex instanceof ApiValidationException) {
				result.setResult("fail");
				result.setErrorCode("3007");
				result.setErrorMessage(((ApiValidationException)ex).getErrorMessage());
			} else if(ex instanceof ApiConfigurationException) {
				logger.error(((ApiConfigurationException)ex).getErrorMessage());
				result.setResult("fail");
				result.setErrorCode(((ApiConfigurationException)ex).getErrorCode());
				result.setErrorMessage(((ApiConfigurationException)ex).getErrorMessage());
			} else {
				logger.error(ex.getMessage());
				result.setResult("fail");
				result.setErrorCode("3002");
				result.setErrorMessage("API Business Exception.");
			}
			
			return result;
		}
		
		result.setResult("fail");
		result.setErrorCode("3004");
		result.setErrorMessage("The API Not Be Found At The Business Server,Please Contact us!");
		return result;
	}
}
