package com.youmeek.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	private static String realPath = null;
	
	public Util() {
	}
	
	public static synchronized String getRealPath() {
		return realPath;
	}
	
	public static synchronized void setRealPath(String realPath) {
		if(realPath == null) {
			realPath = realPath;
		}
	}
	
	public static double divide(double v1, double v2, int scale) {
		BigDecimal volumn = new BigDecimal(v1);
		if(scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		} else {
			return volumn.divide(new BigDecimal(v2), scale, 4).doubleValue();
		}
	}
	
	public static String randonString(int len) {
		return RandomStringUtils.random(len, "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
	}
	
	public static String randonNum(int len) {
		return RandomStringUtils.random(len, "1234567890");
	}
	
	public static int getRandomInt(int digits) {
		return RandomUtils.nextInt(0, (int) Math.pow(10.0D, (double) digits) - (int) Math.pow(10.0D, (double) (digits - 1))) + (int)Math.pow(10.0D, (double)(digits - 1));
	}
	
	public static boolean isNullOrBlank(String param) {
		return null != param && !param.trim().equals("")?removeSpace(param).equals(""):true;
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isIntegerLargerZero(String str) {
		Pattern pattern = Pattern.compile("[1-9]*");
		return pattern.matcher(str).matches();
	}
	
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}
	
	public static void deleteAxisTempDirectory(File dir) throws Exception {
		if(dir != null && dir.isDirectory()) {
			File[] entries = dir.listFiles();
			int sz = entries.length;
			
			for(int i = 0; i < sz; ++i) {
				if(entries[i].isDirectory()) {
					deleteAxisTempDirectory(entries[i]);
				} else if(entries[i].getName().indexOf("axis2") != -1) {
					entries[i].delete();
				}
			}
		}
		
	}
	
	public static String idTypeToName(String idType) {
		return idType.equals("1")?"居民身份证":(idType.equals("3")?"护照":(idType.equals("4")?"军人证":(idType.equals("2")?"驾照":(idType.equals("5")?"出生证明":(idType.equals("6")?"户口簿":(idType.equals("7")?"港澳台同胞证":(idType.equals("8")?"其他":null)))))));
	}
	
	public static String nameToIdType(String name) {
		return name.equals("居民身份证")?"1":(name.equals("护照")?"3":(name.equals("军人证")?"4":(name.equals("驾照")?"2":(name.equals("出生证明")?"5":(name.equals("户口簿")?"6":(name.equals("港澳台同胞证")?"7":(name.equals("其他")?"8":null)))))));
	}
	
	public static String isSepecialString(String str) throws Exception {
		if(str != null) {
			if(str.indexOf("<script") != -1) {
				throw new Exception("存在特殊字符: script");
			}
			
			String regEx = "[<>]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			if(m.find()) {
				throw new Exception("存在特殊字符:  " + str);
			}
		}
		
		return str;
	}
	
	public static boolean ifSepecialString(String str) {
		if(str != null) {
			String regEx = "[`~@#$%^&*+=\'\'\\\\<>/~@#￥%……&*——+‘’]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.find();
		} else {
			return false;
		}
	}
	
	/** @deprecated */
	@Deprecated
	public static String getIpForLinux(String macName) throws SocketException, UnknownHostException {
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		
		while(true) {
			NetworkInterface ni;
			do {
				if(!en.hasMoreElements()) {
					return null;
				}
				
				ni = (NetworkInterface)en.nextElement();
			} while(!ni.getName().equals(macName));
			
			List list = ni.getInterfaceAddresses();
			Iterator it = list.iterator();
			
			while(it.hasNext()) {
				InterfaceAddress ia = (InterfaceAddress)it.next();
				if(ia.getNetworkPrefixLength() == 24) {
					return ia.getAddress().getHostAddress();
				}
			}
		}
	}
	
	/** @deprecated */
	@Deprecated
	public static String getIpForWin() throws SocketException, UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress().toString();
	}
	
	public static String getLocalRealIp() {
		StringBuffer ip = new StringBuffer();
//		Collection colInetAddress = getAllHostAddress();
		ArrayList<InetAddress> colInetAddress = (ArrayList) getAllHostAddress();
		
		for (InetAddress address : colInetAddress) {
			if(!address.isLoopbackAddress() && address instanceof Inet4Address) {
				if(ip.length() == 0) {
					ip.append(address.getHostAddress());
				} else {
					ip.append("/" + address.getHostAddress());
				}
			}
		}
		/*
		Iterator i$ = colInetAddress.iterator();
		
		while(i$.hasNext()) {
			InetAddress address = (InetAddress)i$.next();
			if(!address.isLoopbackAddress() && address instanceof Inet4Address) {
				if(ip.length() == 0) {
					ip.append(address.getHostAddress());
				} else {
					ip.append("/" + address.getHostAddress());
				}
			}
		}
		*/
		return ip.toString();
	}
	
	public static String getLocalRealHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}
	
	public static Collection<InetAddress> getAllHostAddress() {
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			ArrayList addresses = new ArrayList();
			
			while(e.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface)e.nextElement();
				Enumeration inetAddresses = networkInterface.getInetAddresses();
				
				while(inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress)inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}
			
			return addresses;
		} catch (SocketException var5) {
			throw new RuntimeException(var5.getMessage(), var5);
		}
	}
	
	public static String removeSpace(String source) {
		source = source.trim().replaceAll("　", "");
		return source.replaceAll(" ", "");
	}
	
	public static boolean isMoreThanMaxLength(String s, int max) {
		return isNullOrBlank(s)?false:s.length() > max;
	}
	
	public static final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
		if(QJstr.getBytes().length == QJstr.length()) {
			return QJstr;
		} else {
			StringBuffer outStrBuf = new StringBuffer("");
			String Tstr = "";
			Object b = null;
			
			for(int i = 0; i < QJstr.length(); ++i) {
				Tstr = QJstr.substring(i, i + 1);
				if(Tstr.equals("　")) {
					outStrBuf.append(" ");
				} else {
					byte[] var5 = Tstr.getBytes("unicode");
					if(var5[2] == -1) {
						var5[3] = (byte)(var5[3] + 32);
						var5[2] = 0;
						outStrBuf.append(new String(var5, "unicode"));
					} else {
						outStrBuf.append(Tstr);
					}
				}
			}
			
			return outStrBuf.toString();
		}
	}
	
	public static String getPeriodFlag(String num) {
		return isNullOrBlank(num)?"":(num.equals("1")?"Y":(num.equals("2")?"M":(num.equals("3")?"D":"")));
	}
	
	public static void write(String fileName, String fileContent) {
		File f = new File(fileName);
		
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(fileContent);
			fw.close();
		} catch (IOException var5) {
			var5.printStackTrace();
		}
		
	}
	
	public static void setNoCacheHeader(HttpServletResponse response) {
		response.addHeader("Cache-Control", "no-store");
		response.addHeader("Cache-Control", "no-cache");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0L);
	}
	
	public static void setHttpOnlyCookie(HttpServletResponse response, String key, String value, String path, String domain, long maxAge, boolean isSecure) {
		StringBuilder sb = new StringBuilder(key.trim() + "=" + value);
		sb.append(isNullOrBlank(path)?"":";Path=" + path);
		sb.append(isNullOrBlank(domain)?"":";Domain=" + domain);
		sb.append(maxAge == -1L?"":";Max-Age=" + maxAge);
		if(isSecure) {
			sb.append(";secure");
		}
		
		sb.append(";HTTPOnly");
		response.addHeader("Set-Cookie", sb.toString());
	}
	
	public static String getRealIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	public static String getRealScheme(HttpServletRequest request) {
		return !isNullOrBlank(request.getHeader("X-Forwarded-Proto"))?request.getHeader("X-Forwarded-Proto"):request.getScheme();
	}
	
	public static String getRealBasepath(HttpServletRequest request) {
		String port = ":" + request.getServerPort();
		String schema = getRealScheme(request);
		if("https".equals(schema) && request.getServerPort() == 443 || "http".equals(schema) && request.getServerPort() == 80) {
			port = "";
		}
		
		return schema + "://" + request.getServerName() + port;
	}
	
	public static String getRealMethod(HttpServletRequest request) {
		String method = request.getMethod().toUpperCase();
		String m = request.getParameter("_method");
		if(!isNullOrBlank(m)) {
			if("DELETE".equals(m.trim().toUpperCase())) {
				method = "DELETE";
			} else if("PUT".equals(m.trim().toUpperCase())) {
				method = "PUT";
			}
		}
		
		return method;
	}
	
	public static boolean checkRegExp(String expStr, String expInfo) throws Exception {
		Pattern p = Pattern.compile(expStr);
		Matcher m = p.matcher(expInfo);
		return m.matches();
	}
	
	public static String readSafeFile(String fileName, String key) throws IOException {
		return AESUtil.Decrypt(FileUtil.readSrcFile(fileName, "UTF-8"), key, 2);
	}
	
	public static String readKeyFile(String fileName) throws IOException {
		return readSafeFile(fileName, "%J*m3|.[p`_0&@?<");
	}
	
	public static Map convertBean(Object bean, boolean isShowBlankFileds) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		HashMap returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		
		for(int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if(!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if(result != null) {
					returnMap.put(propertyName, result);
				} else if(isShowBlankFileds) {
					returnMap.put(propertyName, (Object)null);
				}
			}
		}
		
		return returnMap;
	}
	
	public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		
		for(int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if(map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Object[] args = new Object[]{value};
				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		
		return obj;
	}
}
