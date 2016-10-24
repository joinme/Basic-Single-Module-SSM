package com.youmeek.common.rpc.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/9/23 16:35
 * @copyright Copyright 2016 . All rights reserved.
 */
public class ApiHttpClient {
	public static Logger logger = LoggerFactory.getLogger(ApiHttpClient.class);
	private static int defaultIdleConnTimeout = '\uea60';
	private static int defaultMaxConnPerHost = 30;
	private static int defaultMaxTotalConn = 80;
	private static int defaultConnectionTimeout = 8000;
	private static final long defaultHttpConnectionManagerTimeout = 3000L;
	private static HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	
	public ApiHttpClient() {
	}
	
	public static String doPostHttpAPIRequest(String url, String postBody, int timeOut) throws Exception {
		logger.debug("================发送数据：url=" + url + ",body=" + postBody + ", timeOut=" + timeOut);
		HttpClient client = new HttpClient(connectionManager);
		new StringBuffer();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(defaultConnectionTimeout);
		client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
		client.getParams().setConnectionManagerTimeout(3000L);
		PostMethod method = null;
		Object in = null;
		Object bin = null;
		Object reader = null;
		Object br = null;
		boolean flag = true;
		long cost = -1L;
		long start = System.currentTimeMillis();
		
		StringBuffer response;
		try {
			method = new PostMethod(url);
			StringRequestEntity e = new StringRequestEntity(postBody, "applicatin/json", "UTF-8");
			method.setRequestEntity(e);
			int flag1 = client.executeMethod(method);
			if(flag1 != 200) {
				logger.error("调用接口网络异常,收到状态码：" + flag1);
//				throw new Exception("3006", "调用接口网络异常：" + flag1);
				throw new Exception("调用接口网络异常：" + flag1);
			}
			
			response = new StringBuffer(method.getResponseBodyAsString());
		} catch (IOException var19) {
			var19.printStackTrace();
			logger.error("调用接口IO异常：" + var19.getMessage());
//			throw new Exception("3005", "调用接口IO异常");
			throw new Exception("调用接口IO异常");
		} finally {
			if(br != null) {
				((BufferedReader)br).close();
			}
			
			if(reader != null) {
				((InputStreamReader)reader).close();
			}
			
			if(bin != null) {
				((BufferedInputStream)bin).close();
			}
			
			if(in != null) {
				((InputStream)in).close();
			}
			
			if(method != null) {
				method.releaseConnection();
			}
			
			cost = System.currentTimeMillis() - start;
		}
		
		return response.toString();
	}
	
	static {
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
		connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);
		IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
		ict.addConnectionManager(connectionManager);
		ict.setConnectionTimeout((long)defaultIdleConnTimeout);
		ict.start();
	}
}

