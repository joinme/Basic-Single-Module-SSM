package com.youmeek.common.amq;

import com.youmeek.common.utils.AESUtil;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/28 11:49
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class SelfActiveMQConnectionFactory extends ActiveMQConnectionFactory {
	public static Logger logger = LoggerFactory.getLogger(SelfActiveMQConnectionFactory.class);
	
	public SelfActiveMQConnectionFactory() {
	}
	
	public synchronized void setPassword(String password) {
		super.setPassword(AESUtil.Decrypt(password, "aPnXcS1B23hJb365", 2));
	}
	
	public synchronized void setUserName(String userName) {
		super.setUserName(AESUtil.Decrypt(userName, "aPnXcS1B23hJb365", 2));
	}
	
	public synchronized void setBrokerURL(String brokerURL) {
		if("failover://tcp://localhost:61616".equals(brokerURL)) {
			super.setBrokerURL("failover://tcp://localhost:61616");
		} else {
			super.setBrokerURL(AESUtil.Decrypt(brokerURL, "aPnXcS1B23hJb365", 2));
		}
		
	}
}
