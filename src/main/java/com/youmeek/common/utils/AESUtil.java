package com.youmeek.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 16:02
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class AESUtil {
	public static final String ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	public static final int ENCODE_BASE64 = 1;
	public static final int ENCODE_HEX = 2;
	private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
	
	public AESUtil() {
	}
	
	public static String Encrypt(String sSrc, String sKey, int encodeType) {
		try {
			if(sKey == null) {
				System.out.println("Key为空null");
				return null;
			} else if(sKey.length() != 16) {
				System.out.println("Key长度不是16位");
				return null;
			} else {
				byte[] e = sKey.getBytes("ASCII");
				SecretKeySpec skeySpec = new SecretKeySpec(e, "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(1, skeySpec);
				byte[] encrypted = cipher.doFinal(sSrc.getBytes());
				if(encodeType == 2) {
					return HexUtil.byte2hex(encrypted).toLowerCase();
				} else if(encodeType == 1) {
					return Base64Util.encodeBase64(encrypted);
				} else {
					System.out.print("编码格式错误,应该是ENCODE_HEX/ENCODE_BASE64二选一!");
					return null;
				}
			}
		} catch (Exception var7) {
			System.out.println(var7.toString());
			return null;
		}
	}
	
	public static String Decrypt(String sSrc, String sKey, int encodeType) {
		try {
			if(sKey == null) {
				System.out.println("Key为空null");
				return null;
			} else if(sKey.length() != 16) {
				System.out.println("Key长度不是16位");
				return null;
			} else {
				Object ex = null;
				byte[] ex1;
				if(encodeType == 2) {
					ex1 = HexUtil.hex2byte(sSrc);
				} else {
					if(encodeType != 1) {
						System.out.print("编码格式错误,应该是ENCODE_HEX/ENCODE_BASE64二选一!");
						return null;
					}
					
					ex1 = Base64Util.decodeBase64(sSrc);
				}
				
				if(ex1 != null && ex1.length != 0) {
					byte[] raw = sKey.getBytes("ASCII");
					SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
					Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
					cipher.init(2, skeySpec);
					
					try {
						byte[] e = cipher.doFinal(ex1);
						return new String(e);
					} catch (Exception var9) {
						System.out.println(var9.toString());
						return null;
					}
				} else {
					System.out.print("解密时,解码后的字节数组为空!");
					return null;
				}
			}
		} catch (Exception var10) {
			System.out.println(var10.toString());
			return null;
		}
	}
}

