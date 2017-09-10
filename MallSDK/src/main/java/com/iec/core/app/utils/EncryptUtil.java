package com.iec.core.app.utils;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密
 *     
 * 项目名称：fmallCore    
 * 类名称：EncryptUtil    
 * 类描述：   加密时处理掉了非法字符
 * 创建人：chengjx  
 * 创建时间：2014年5月16日 下午4:32:25    
 * 修改人：chengjx    
 * 修改时间：2014年5月16日 下午4:32:25    
 * 修改备注：        
 *
 */
public class EncryptUtil {

	private static final String UTF8 = "utf-8";
	private static final String ALGORITHM_DESEDE = "DESede";

	public String md5Digest(String src) throws Exception {
		// 定义数字签名方法, 可用：MD5, SHA-1
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes(UTF8));

		return this.byte2HexStr(b);
	}

	public String base64Encoder(String src) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src.getBytes(UTF8));
	}

	public String base64Decoder(String dest) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		return new String(decoder.decodeBuffer(dest),UTF8);
	}

	public String desedeEncoder(String src, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key),
				ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(src.getBytes(UTF8));

		return byte2HexStr(b);
	}

	public String desedeDecoder(String dest, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(build3DesKey(key),
				ALGORITHM_DESEDE);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DESEDE);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] b = cipher.doFinal(str2ByteArray(dest));

		return new String(b, UTF8);

	}

	public String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}

			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}

	public byte[] str2ByteArray(String s) {
		int byteArrayLength = s.length() / 2;
		byte[] b = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
					.intValue();
			b[i] = b0;
		}

		return b;
	}

	private byte[] build3DesKey(String keyStr) throws Exception {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes(UTF8);
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}

		return key;
	}
}