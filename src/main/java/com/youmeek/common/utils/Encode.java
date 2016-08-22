package com.youmeek.common.utils;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/29 16:36
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
	public static Logger logger = LoggerFactory.getLogger(Encode.class);
	static MessageDigest SHAdigist = null;
	static MessageDigest MD5digist = null;
	
	public Encode() {
	}
	
	public static synchronized String SHAEncode(String in) {
		if(in != null && in.length() != 0) {
			getSHAdigist().update(in.getBytes());
			byte[] b = getSHAdigist().digest();
			return (new String(Hex.encodeHex(b))).toUpperCase();
		} else {
			return null;
		}
	}
	
	public static synchronized String MD5Encode(String in) {
		if(in != null && in.length() != 0) {
			getMD5digist().update(in.getBytes());
			byte[] b = getMD5digist().digest();
			return (new String(Hex.encodeHex(b))).toUpperCase();
		} else {
			return null;
		}
	}
	
	public static MessageDigest getSHAdigist() {
		if(SHAdigist == null) {
			try {
				SHAdigist = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException var1) {
				var1.printStackTrace();
				logger.error(var1.getMessage());
			}
		}
		
		return SHAdigist;
	}
	
	public static MessageDigest getMD5digist() {
		if(MD5digist == null) {
			try {
				MD5digist = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException var1) {
				var1.printStackTrace();
				logger.error(var1.getMessage());
			}
		}
		
		return MD5digist;
	}
	
	public static String shaAndMd5Encode(String src, String dynamicSolt, String staticSolt) {
		String first = SHAEncode(dynamicSolt + src + staticSolt);
		return MD5Encode(first + staticSolt + dynamicSolt);
	}
}

