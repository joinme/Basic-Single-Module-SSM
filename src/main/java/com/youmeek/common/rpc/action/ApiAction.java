package com.youmeek.common.rpc.action;

import com.alibaba.fastjson.JSON;
import com.youmeek.common.rpc.model.HttpApiResult;
import com.youmeek.common.rpc.model.HttpRPC;
import com.youmeek.common.rpc.util.ApiUtil;
import com.youmeek.common.rpc.util.RpcUtil;
import com.youmeek.common.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/9/23 15:56
 * @copyright Copyright 2016 . All rights reserved.
 */
@Controller
public class ApiAction implements ApplicationContextAware {
	
	private ApplicationContext context;
	private static final Logger logger = LoggerFactory.getLogger(ApiAction.class);
	
	@RequestMapping(value = {"/public/api"}, method = {RequestMethod.POST})
	@ResponseBody
	public HttpApiResult apiHandlerBasedOnIndex(HttpServletRequest request, HttpServletResponse response,
	                                            @RequestParam(value = "_", required = true) String sign,
	                                            @RequestParam(value = "flag", required = true) String systemNo,
	                                            @RequestBody String requestBody) {
		return this.doApiHandlerBasedOnIndex(request, response, sign, systemNo, requestBody);
	}
	
	
	private HttpApiResult doApiHandlerBasedOnIndex(HttpServletRequest request, HttpServletResponse response, 
	                                               @RequestParam(value = "_",required = true) String sign, 
	                                               @RequestParam(value = "flag",required = true) String systemNo, 
	                                               @RequestBody String requestBody) {
		HttpApiResult result = new HttpApiResult();
		result.setResult("fail");
		result.setErrorCode("3003");
		result.setErrorMessage("Illegal API Argument or Business Exception.");
		
		try {
			if(!Util.isNullOrBlank(requestBody)) {
				HttpRPC httpRPC = (HttpRPC) JSON.parseObject(requestBody.trim(), HttpRPC.class);
				if(!sign.equals(ApiUtil.sign(httpRPC.getServiceCode(), httpRPC.getVersion(), systemNo, requestBody.trim(), "UTF-8"))) {
					logger.error("------API内部请求验签失败！！！");
					logger.error("------来源地址：" + request.getRequestURL());
					logger.error("------请求URL：" + request.getRequestURL());
					logger.error("------请求报文体：" + requestBody);
					return result;
				} else {
					Class clazz = Class.forName(httpRPC.getClazz());
					Object handler = this.context.getBean(httpRPC.getBeanName(), clazz);
					logger.debug("invoke local HTTP API: serviceCode=" + httpRPC.getServiceCode() + ", version=" + httpRPC.getVersion() + ", handler=" + handler + ", clazz=" + clazz);
					return RpcUtil.localRpc(1, httpRPC, httpRPC.getServiceCode(), httpRPC.getVersion(), handler, clazz);
				}
			} else {
				return result;
			}
		} catch (Exception var10) {
			var10.printStackTrace();
			return result;
		}
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
}
