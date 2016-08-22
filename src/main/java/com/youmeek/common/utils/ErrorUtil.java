//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.youmeek.common.utils;

/**
 * 报错发送jms 错误信息
 */
public class ErrorUtil {/*
	
	private JmsTemplate exOutJmsTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(ErrorUtil.class);
	private static String ip = null;
	private static String hostName = null;
	private static int system = -1;
	
	public ErrorUtil() {
	}
	
	public static String getErrorMessage(Throwable ex) {
		StringBuffer stackTraceBuffer = new StringBuffer(ex.toString() + "\r\n");
		logger.error(ex.getMessage());
		if(ex.getStackTrace() != null) {
			for(int i = 0; i < ex.getStackTrace().length; ++i) {
				stackTraceBuffer.append(ex.getStackTrace()[i]);
				if(i != ex.getStackTrace().length - 1) {
					stackTraceBuffer.append("\n");
				}
			}
		}
		
		logger.error(stackTraceBuffer.toString());
		return stackTraceBuffer.toString();
	}
	
	public void loggerError(Throwable ex) {
		this.sendExLog(ex, getErrorMessage(ex), (String)null, (String)null, (String)null);
	}
	
	public void loggerHttpError(Throwable ex, HttpServletRequest request) {
		this.sendExLog(ex, getErrorMessage(ex), request.getRequestURI(), request.getAttribute("hjbUserUID") + "", Util.getRealIP(request));
	}
	
	private void sendExLog(final Throwable ex, final String stack, final String uri, final String uid, final String loginIP) {
		this.exOutJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage msg = session.createMapMessage();
				msg.setInt("system", ErrorUtil.system);
				msg.setString("ip", ErrorUtil.ip);
				msg.setString("hostName", ErrorUtil.hostName);
				msg.setString("message", ex.toString() + ": " + ex.getMessage());
				msg.setString("stackTrace", stack);
				msg.setString("uri", uri);
				msg.setString("uid", uid);
				msg.setString("loginIP", loginIP);
				msg.setLong("time", Long.parseLong((new DateTime()).toString("yyyyMMddHHmmss")));
				return msg;
			}
		});
	}
	
	public static synchronized void setIp(String ipIn) {
		if(ip == null) {
			ip = ipIn;
		}
	}
	
	public static synchronized void setHostName(String hostNameIn) {
		if(hostName == null) {
			hostName = hostNameIn;
		}
	}
	
	public static synchronized void setSystem(int systemIn) {
		if(system == -1) {
			system = systemIn;
		}
	}
	
	public static int getSystem() {
		return system;
	}
	
	public JmsTemplate getExOutJmsTemplate() {
		return this.exOutJmsTemplate;
	}
	
	public void setExOutJmsTemplate(JmsTemplate exOutJmsTemplate) {
		this.exOutJmsTemplate = exOutJmsTemplate;
	}
*/}
