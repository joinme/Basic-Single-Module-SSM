package com.youmeek.common.utils;

import java.util.regex.Pattern;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/29 17:47
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class SecurityUtil {
	public SecurityUtil() {
	}
	
	public static String xssStrim(String value) {
		if(Util.isNullOrBlank(value)) {
			return value;
		} else {
			value = value.replaceAll("", "");
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", 2);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("</script>", 2);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("<script(.*?)>", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("javascript:", 2);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("vbscript:", 2);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("onload(.*?)=", 42);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("(?<=^.*[^\\[])\'(?=[^\\]].*$)", 42);
			value = scriptPattern.matcher(value).replaceAll("\"");
			return value.replaceAll("script", "").replaceAll("exec", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("eval", "").replaceAll("cookie", "").replaceAll("\\(", "（").replaceAll("\\)", "）");
		}
	}
}

