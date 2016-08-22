package com.youmeek.common.utils;

/**
 * @author Administrator
 * @version v1.0
 * @title
 * @usage
 * @create 2016/7/26 16:04
 * @copyright Copyright 2015 hjb365. All rights reserved.
 */

public class HexUtil {
	public HexUtil() {
	}
	
	public static byte[] hex2byte(String strhex) {
		if(strhex == null) {
			return null;
		} else {
			int l = strhex.length();
			if(l % 2 == 1) {
				return null;
			} else {
				byte[] b = new byte[l / 2];
				
				for(int i = 0; i != l / 2; ++i) {
					b[i] = (byte)Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
				}
				
				return b;
			}
		}
	}
	
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		
		for(int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 255);
			if(stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		
		return hs.toUpperCase();
	}
}

