package com.iec.core.app.utils;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtilBak {
	/*
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	private String sKey = "0123456789abcdef";
	private String ivParameter = "0123456789abcdef";
	private static AESUtilBak instance = null;

	private AESUtilBak() {

	}

	public static AESUtilBak getInstance() {
		if (instance == null)
			instance = new AESUtilBak();
		return instance;
	}

	// 加密
	public String encrypt(String sSrc) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	// 解密
	public String decrypt(String sSrc) throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		AESUtilBak aes = AESUtilBak.getInstance();
		/*String str = aes.encrypt("{'account':'wujabon@163.com','nickname':'wujabon%40163.com','memberid':'20130129152711','isverify':2}");
		//System.out.println(aes.encrypt("sdfsdfdsfds"));
		//System.out.println(aes.decrypt(str));*/
		//String add = null;
		////System.out.println(aes.encrypt(add));
		////System.out.println(aes.decrypt(java.net"uvVRIIVaQnWmw9wH8gZAIInXp8zzdVTfWvLR8i5gB%2FebTVpjXcg2X6IGjIvDG4hsEnZZhDgMNni8%0AERW16jLVJmT%2BkTQFMYRWUSiMHLJPOR2Z2pk1WMDpLbZJJ%2BJV9J%2Fg35kRfNr2%2BWwI27SYtDJf3g%3D%3D"));
		
		//System.out.println(AESUtil.getInstance().decrypt(URLDecoder.decode("uvVRIIVaQnWmw9wH8gZAIInXp8zzdVTfWvLR8i5gB%2FebTVpjXcg2X6IGjIvDG4hsEnZZhDgMNni8%0AERW16jLVJmT%2BkTQFMYRWUSiMHLJPOR2Z2pk1WMDpLbZJJ%2BJV9J%2Fg35kRfNr2%2BWwI27SYtDJf3g%3D%3D","utf-8")));
	}
}
