package com.youmeek.common.rpc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.youmeek.common.rpc.model.HttpApiResult;
import com.youmeek.common.rpc.model.HttpRPC;
import com.youmeek.common.utils.Encode;
import com.youmeek.common.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/9/23 16:11
 * @copyright Copyright 2016 . All rights reserved.
 */
public class ApiUtil {
	public static Logger logger = LoggerFactory.getLogger(ApiUtil.class);
	
	public ApiUtil() {
	}
	
	public static HttpApiResult<String> callHttpApiWithTimeOut(String baseUrl, String serviceCode, String version, 
	                                                           String systemFlag, int timeout, Object... args) throws Exception {
		if(!Util.isNullOrBlank(baseUrl) && !Util.isNullOrBlank(serviceCode) && !Util.isNullOrBlank(version) 
				&& !Util.isNullOrBlank(systemFlag)) {
			if(baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			
			HttpRPC rpc = null;
			HashMap<String, Object> params;
			if(args != null && args.length != 0) {
				params = new HashMap<>();
				
				for(int i = 0; i < args.length; ++i) {
					if(args[i] instanceof String) {
						params.put(i + "", args[i] + "");
					} else {
						params.put(i + "", JSON.toJSONString(args[i], SerializerFeature.DisableCircularReferenceDetect));
					}
				}
				rpc = new HttpRPC();
				rpc.setArgs(params);
			}
			String result = null;
			try {
				String backResult = JSON.toJSONString(rpc);
				String sign = sign(serviceCode, version, systemFlag, backResult, "UTF-8");
				logger.debug("================签名数据：serviceCode=" + serviceCode + ", version=" + version + ", systemFlag=" + systemFlag + ",body=" + backResult + ",签名结果=" + sign);
				result = ApiHttpClient.doPostHttpAPIRequest(baseUrl + "/" + version + "/" + serviceCode + "?flag=" + systemFlag + "&_=" + sign, backResult, timeout < 10000?10000:timeout);
				logger.debug("================API " + serviceCode + "/" + version + " 明文调用返回结果:" + result);
			} catch (Exception var11) {
				var11.printStackTrace();
				logger.error("客户端调用API服务时发生异常。" + var11.getMessage());
				throw new RuntimeException("客户端调用API服务时发生异常。");
			}
			
			if(result == null) {
				return null;
			} else {
				HttpApiResult var13 = (HttpApiResult)JSON.parseObject(result, HttpApiResult.class);
				return convertStringData(var13);
			}
		} else {
			throw new RuntimeException("调用API参数不全！");
		}
	}
	
	public static HttpApiResult<String> callHttpApi(String baseUrl, String serviceCode, String version, String systemFlag, Object... args) throws Exception {
		return callHttpApiWithTimeOut(baseUrl, serviceCode, version, systemFlag, 10000, args);
	}
	/*
	public static HttpApiResult<String> callHttpApiProWithTimeOut(String baseUrl, String serviceCode, String version, String systemFlag, String publicKey, int timeout, Object... args) throws Exception {
		if(!Util.isNullOrBlank(baseUrl) && !Util.isNullOrBlank(serviceCode) && !Util.isNullOrBlank(version) && !Util.isNullOrBlank(systemFlag) && !Util.isNullOrBlank(publicKey)) {
			if(baseUrl.endsWith("/")) {
				baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
			}
			
			HttpRPC rpc = null;
			HashMap params;
			if(args != null && args.length != 0) {
				params = new HashMap();
				
				for(int result = 0; result < args.length; ++result) {
					if(args[result] instanceof String) {
						params.put(result + "", args[result] + "");
					} else {
						params.put(result + "", JSON.toJSONString(args[result], new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect}));
					}
				}
				
				rpc = new HttpRPC();
				rpc.setArgs(params);
			} else {
				params = null;
			}
			
			String var13 = null;
			
			try {
				String backResult = JSON.toJSONString(rpc);
				backResult = RSAUtil.encode(backResult, publicKey, 1);
				String sign = sign(serviceCode, version, systemFlag, backResult, "UTF-8");
				var13 = APIHttpClient.doPostHttpAPIRequest(baseUrl + "/pro/" + version + "/" + serviceCode + "?flag=" + systemFlag + "&_=" + sign, backResult, timeout < 10000?10000:timeout);
			} catch (Exception var12) {
				var12.printStackTrace();
				throw new RuntimeException("客户端调用API服务时发生异常。");
			}
			
			if(Util.isNullOrBlank(var13)) {
				return null;
			} else {
				HttpApiResult var14 = (HttpApiResult) JSON.parseObject(RSAUtil.decodeByPublicKey(var13, publicKey, 1), HttpApiResult.class);
				return convertStringData(var14);
			}
		} else {
			throw new RuntimeException("调用API参数不全！");
		}
	}
	*/
	/*
	public static HttpApiResult<String> callHttpApiPro(String baseUrl, String serviceCode, String version, String systemFlag, String publicKey, Object... args) throws Exception {
		return callHttpApiProWithTimeOut(baseUrl, serviceCode, version, systemFlag, publicKey, 10000, args);
	}
	*/
	public static String sign(String serviceCode, String version, String flag, String body, String charset) throws UnsupportedEncodingException {
		return Encode.shaAndMd5Encode(version.trim() + serviceCode.trim() + body.trim(), flag, "I{`in$5~|,A~}\'3(\'jPS2i]=w)v->bi.UdS8B>2k", charset);
	}
	
	private static HttpApiResult<String> convertStringData(HttpApiResult<String> backResult) {
		if(backResult.getData() == null) {
			return backResult;
		} else {
			HttpApiResult rtn = new HttpApiResult();
			rtn.setErrorCode(backResult.getErrorCode());
			rtn.setErrorMessage(backResult.getErrorMessage());
			rtn.setResult(backResult.getResult());
			rtn.setData(backResult.getData());
			return rtn;
		}
	}
}

