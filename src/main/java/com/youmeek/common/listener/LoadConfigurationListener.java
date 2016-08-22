package com.youmeek.common.listener;

import com.youmeek.common.Constant;
import com.youmeek.common.utils.SpringUtil;
import com.youmeek.common.utils.Util;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.UnknownHostException;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/27 17:12
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class LoadConfigurationListener implements ServletContextListener {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String configFile = "/properties/config.properties";
//	public static final String configFile = "/config.propertis";
	// env.properties中必须有一个runtime.global属性标识当前运行环境
	public static final String baseEnvFile = "/env.properties";
	
	private ApplicationContext applicationContext;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.debug("loadConfiguration...");
		// -------------------------------权限配置文件
		PropertiesConfiguration authConfig = new PropertiesConfiguration();
		authConfig.setEncoding("utf-8");
		authConfig.setURL(this.getClass().getResource(configFile));
		authConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
		ConfigurationUtils.dump(authConfig, System.out);
		
		// ------------------------------其他配置文件
		/* TODO 加载 密钥 key
		String key = null;
		try {
			authConfig.load();
			key = Util.readKeyFile(authConfig.getString("keyName"));
			if (key == null) {
				logger.error("加载配置文件" + authConfig.getString("keyName") + "失败！系统即将退出！");
				System.exit(0);
			}
		} catch (Exception e) {
			logger.error("加载配置文件" + configFile + "异常，启动失败！系统即将退出！");
			e.printStackTrace();
			System.exit(0);
		}
		*/
		
		// ------------------------------机器信息
		try {
			authConfig.load();
			String ip = Util.getLocalRealIp();
			String hostname = Util.getLocalRealHostname();
			/*TODO 发送错误消息用
			ErrorUtil.setIp(ip);
			ErrorUtil.setHostName(hostname);
			*/
			SpringUtil.setContextAttr("host", ip);
			SpringUtil.setContextAttr("hostname", hostname);
		} catch (UnknownHostException e) {
			logger.error("系统启动获取主机信息异常！" + e.getMessage());
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		
		// ---------------------数据字典配置
//		TODO 密钥 key
//		SpringUtil.setContextAttr("apipublicKey", key);
		SpringUtil.setContextAttr(Constant.PRIVILEGE_PASS_PATH, authConfig.getStringArray("paths"));
		SpringUtil.setContextAttr("domain", authConfig.getString("domain"));
		SpringUtil.setContextAttr("loginPage", authConfig.getString("loginPage"));
		
//		SpringUtil.setContextAttr("systemNo", authConfig.getString("system"));
//		SpringUtil.setContextAttr("dailyCheckWarnEmail",
//				authConfig.getString("dailyCheckWarnEmail").replaceAll(";", ","));
//		SpringUtil.setContextAttr("dailyCheckWarnSms", authConfig.getString("dailyCheckWarnSms").replaceAll(";", ","));
//		SpringUtil.setContextAttr("apiUrl", CommonConstant.godParams.getProperty("apiUrl"));
		
		/* 多系统 统一 更新 各自的配置信息
		ServletContextRefreshManager serletContextReFreshManager = SpringUtil
				.getBean(ServletContextRefreshManager.class);
		serletContextReFreshManager.reloadConfiguration();
		*/
		
		/*servletContextEvent.getServletContext().setAttribute("mobileMaxCount", authConfig.getInt("mobileMaxCount"));
		Constant.appParam.put("mobileMaxCount", authConfig.getInt("mobileMaxCount"));
		*/
		/* TODO redis key
		Map<String, RedisQueueConsumer> map = SpringUtil.getBeans(RedisQueueConsumer.class);
		for (String each : map.keySet()) {
			map.get(each).initQueue();
		}
		*/
		
		
		/*
		//获取spring上下文！  
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		//获取到bean了，你就可以任意搞它了，想怎么搞就怎么搞
		applicationContext.getBean("UserService");
		//.............
		*/
		
		
		logger.debug("load completed...");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.debug("shutting down...");
	}
}
