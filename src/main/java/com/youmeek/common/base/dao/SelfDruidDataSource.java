package com.youmeek.common.base.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.youmeek.common.utils.AESUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 17:32
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class SelfDruidDataSource extends DruidDataSource{
	
	public static final String JDBC_URL_KEY = "jdbc.url";
	public static final String JDBC_USERNAME_KEY = "jdbc.username";
	public static final String JDBC_PASSWORD_KEY = "jdbc.pwd";
	public static final String JDBC_MAXCONN_KEY = "jdbc.maxconn";
	public static Logger logger = LoggerFactory.getLogger(SelfDruidDataSource.class);
	private final String key = "aPnXcS1B23hJb365";
	
	
	public SelfDruidDataSource() {
	}
	
	public synchronized void setUrl(String url) {
//		this.jdbcUrl = AESUtil.Decrypt(url, key, 2);
		this.jdbcUrl = url;
	}
	
	
	public synchronized void setUsername(String username) {
		//可以加两遍密， conns加一次  然后 key对应的value加一次
//		this.username = AESUtil.Decrypt(username, key, 2);
		this.username = username;
	}
	
	public synchronized void setPwd(String password) {
//		this.password = AESUtil.Decrypt(password, key, 2);
		this.password = password;
	}
	
	public synchronized void setConns(String url) {
		try {
			/* 可放在远程服务器上 解密
			HttpRequestModel e = new HttpRequestModel(AESUtil.Decrypt(url, key, 2).trim(), "GET", HttpResultType.STRING);
			e.setCharset("UTF-8");
			e.setFixResponseCharset("UTF-8");
			HttpResponseModel resp = HttpClientUtil.doHttpRequest(e);
			if(resp.getHttpStatus() != 200) {
				logger.error("加载配置文件失败，系统启动失败。");
				System.exit(0);
			}
			*/
			Properties p = new Properties();
			p.load(IOUtils.toInputStream(StringUtils.join(AESUtil.Decrypt(url, key, 2).split(";"), "\n")));
			this.setUrl(p.getProperty("jdbc.url"));
			this.setUsername(p.getProperty("jdbc.username"));
			this.setPwd(p.getProperty("jdbc.pwd"));
			
		} catch (Exception var7) {
			logger.error("---------------remote方式初始化dataSource失败，系统即将停止，请联系管理员--------------\n message:" + var7.getMessage());
			var7.printStackTrace();
		}
		
	}
	
	/* 另一种 简单 加密方法
	public synchronized void setUrl(String url) {
		this.jdbcUrl = AESUtil.Decrypt(url, "aPnXcS1B23hJb365", 2);
	}
	
	public synchronized void setUsername(String username) {
		this.username = AESUtil.Decrypt(username, "aPnXcS1B23hJb365", 2);
	}
	
	public synchronized void setPwd(String password) {
		this.password = AESUtil.Decrypt(password, "aPnXcS1B23hJb365", 2);
	}
	*/
	
	
}
