package com.youmeek.common.base.dao;

import com.alibaba.druid.stat.DruidStatService;
import com.alibaba.druid.support.http.ResourceSerlvet;
import com.alibaba.druid.support.http.util.IPRange;
import com.alibaba.druid.util.StringUtils;
import com.youmeek.common.utils.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 15:58
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class SelfDruidStatServlet extends ResourceSerlvet {
	Logger logger = LoggerFactory.getLogger(SelfDruidStatServlet.class);
	private static final long serialVersionUID = 1L;
	public static final String PARAM_NAME_RESET_ENABLE = "resetEnable";
	public static final String PARAM_NAME_JMX_URL = "jmxUrl";
	public static final String PARAM_NAME_JMX_USERNAME = "jmxUsername";
	public static final String PARAM_NAME_JMX_PASSWORD = "jmxPassword";
	private DruidStatService statService = DruidStatService.getInstance();
	private String jmxUrl = null;
	private String jmxUsername = null;
	private String jmxPassword = null;
	private MBeanServerConnection conn = null;
	
	private final String key = "%J*m3|.[p`_0&@?<";
	
	public static void main(String[] args){
		System.out.println(AESUtil.Decrypt("Ci64dLaWn2tNC/4tniOhQDXjFHKvZUDSTbobQBpaZOfXrKzYD7Hi/u05P9mN96F9", "%J*m3|.[p`_0&@?<", 1));
	}
	
	public void init() {
		this.initResourceServletAuthEnv();
		this.initStatViewServlet();
	}
	//dbAdmin hjbDBManager
	private void initResourceServletAuthEnv() {
		String paramUserName = this.getInitParameter("loginUsername");
		if(!StringUtils.isEmpty(paramUserName)) {
			this.username = AESUtil.Decrypt(paramUserName, key, 1);
		}
		
		String paramPassword = this.getInitParameter("loginPassword");
		if(!StringUtils.isEmpty(paramPassword)) {
			this.password = AESUtil.Decrypt(paramPassword, key, 1);
		}
		
		String paramRemoteAddressHeader = this.getInitParameter("remoteAddress");
		if(!StringUtils.isEmpty(paramRemoteAddressHeader)) {
			this.remoteAddressHeader = paramRemoteAddressHeader;
		}
		
		try {
			String paramAllow = this.getInitParameter("allow");
			paramAllow = AESUtil.Decrypt(paramAllow, key, 1);
			if(paramAllow != null && paramAllow.trim().length() != 0) {
				paramAllow = paramAllow.trim();
				String[] allows = paramAllow.split(",");
				for (String allow : allows) {
					if(allow != null && allow.length() != 0) {
						this.allowList.add(new IPRange(allow));
					}
				}
			}
		} catch (Exception var12) {
			String msg = "initParameter config error, allow : " + this.getInitParameter("allow");
			this.logger.error(msg, var12);
		}
		
		try {
			String paramDeny = this.getInitParameter("deny");
			if(paramDeny != null && paramDeny.trim().length() != 0) {
				paramDeny = paramDeny.trim();
				String[] denys = paramDeny.split(",");
				for (String deny : denys) {
					if(deny != null && deny.length() != 0) {
						this.denyList.add(new IPRange(deny));
					}
				}
			}
		} catch (Exception var11) {
			String msg = "initParameter config error, deny : " + this.getInitParameter("deny");
			this.logger.error(msg, var11);
		}
		
	}
	
	private void initStatViewServlet() {
		try {
			String resetEnable = this.getInitParameter("resetEnable");
			if(resetEnable != null && resetEnable.trim().length() != 0) {
				this.statService.setResetEnable(Boolean.parseBoolean(resetEnable.trim()));
			}
		} catch (Exception var4) {
			String e = "initParameter config error, resetEnable : " + this.getInitParameter("resetEnable");
			this.logger.error(e, var4);
		}
		
		this.jmxUrl = this.readInitParam("jmxUrl");
		if(this.jmxUrl != null) {
			this.jmxUrl = AESUtil.Decrypt(this.jmxUrl, key, 1);
			this.jmxUsername = this.readInitParam("jmxUsername");
			this.jmxUsername = AESUtil.Decrypt(this.jmxUsername, key, 1);
			this.jmxPassword = this.readInitParam("jmxPassword");
			this.jmxPassword = AESUtil.Decrypt(this.jmxPassword, key, 1);
			
			try {
				this.initJmxConn();
			} catch (IOException var3) {
				this.logger.error("init jmx connection error", var3);
			}
		}
		
	}
	
	public SelfDruidStatServlet() {
		super("support/http/resources");
	}
	
	private String readInitParam(String key) {
		String value = null;
		try {
			value = this.getInitParameter(key).trim();
			value = value.length() > 0 ? value : null;
		} catch (Exception var5) {
			String msg = "initParameter config [" + key + "] error";
			this.logger.warn(msg, var5);
		}
		
		return value;
	}
	
	private void initJmxConn() throws IOException {
		if(this.jmxUrl != null) {
			JMXServiceURL url = new JMXServiceURL(this.jmxUrl);
			HashMap env = new HashMap();
			if(this.jmxUsername != null) {
				String[] jmxc = new String[]{this.jmxUsername, this.jmxPassword};
				env.put("jmx.remote.credentials", jmxc);
			}
			
			JMXConnector jmxc1 = JMXConnectorFactory.connect(url, env);
			this.conn = jmxc1.getMBeanServerConnection();
		}
		
	}
	
	private String getJmxResult(MBeanServerConnection connetion, String url) throws Exception {
		ObjectName name = new ObjectName("com.alibaba.druid:type=DruidStatService");
		return (String)this.conn.invoke(name, "service", new String[]{url}, new String[]{String.class.getName()});
	}
	
	protected String process(String url) {
		String resp = null;
		if(this.jmxUrl == null) {
			resp = this.statService.service(url);
		} else if(this.conn == null) {
			try {
				this.initJmxConn();
			} catch (IOException var6) {
				this.logger.error("init jmx connection error", var6);
				resp = DruidStatService.returnJSONResult(-1, "init jmx connection error" + var6.getMessage());
			}
			
			if(this.conn != null) {
				try {
					resp = this.getJmxResult(this.conn, url);
				} catch (Exception var5) {
					this.logger.error("get jmx data error", var5);
					resp = DruidStatService.returnJSONResult(-1, "get data error:" + var5.getMessage());
				}
			}
		} else {
			try {
				resp = this.getJmxResult(this.conn, url);
			} catch (Exception var4) {
				this.logger.error("get jmx data error", var4);
				resp = DruidStatService.returnJSONResult(-1, "get data error" + var4.getMessage());
			}
		}
		
		return resp;
	}
	
}
