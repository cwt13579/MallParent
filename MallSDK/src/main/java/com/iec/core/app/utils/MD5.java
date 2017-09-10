package com.iec.core.app.utils;

import java.security.MessageDigest;


/**
 * 
 * MD5算法  
 * @author LiuSQ
 *
 */
public class MD5 {
	
	public final static String getMD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			
			mdTemp.update(strTemp);
			
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static String getMd5(String str) {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(str.getBytes("utf-8"));
			
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < b.length; i++) {
				String s = Integer.toHexString(b[i] & 0xFF);
				sb.append(s);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getMd5toUpperCase(String str) {
		return getMd5(str)!=null?getMd5(str).toUpperCase():null;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5.getMd5toUpperCase("pa888888"));
		System.out.println("BC77502B68CF5E1678D4D9E41E814B94");
	}

}
