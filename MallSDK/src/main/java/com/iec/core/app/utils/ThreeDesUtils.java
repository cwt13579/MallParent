package com.iec.core.app.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 加密解密工具
 */
public class ThreeDesUtils {
	
	private static final String KEY_ALGORITHM = "DESede";
	private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	private static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
			0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
			0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
			(byte) 0xE2 };
	private Key key;

	private static final ThreeDesUtils INSTANCE = new ThreeDesUtils();
	
	public static final ThreeDesUtils getInstance(){
		return INSTANCE ;
	}
	
	public ThreeDesUtils() {
		this.initKey(keyBytes);
	}
	/**
	 * 初始化Key对象
	 * @param keyBytes
	 */
	private void initKey(byte[] keyBytes) {
		DESedeKeySpec dks;
		SecretKeyFactory factory = null;
		try {
			if(keyBytes != null && keyBytes.length>=24){
				dks = new DESedeKeySpec(keyBytes);
				factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
				this.key = factory.generateSecret(dks);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密
	 * @param 明文
	 * @return 密文
	 */
	public String getEncString(String inputText) {
		Cipher cipher = null;
		String outputText = null;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, this.key);
			outputText = Base64.encode(cipher.doFinal(inputText
					.getBytes("UTF8")));
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return outputText;
	}

	/**
	 * 解密
	 * @param 密文
	 * @return 明文
	 */
	public String getDesString(String inputText) {
		Cipher cipher = null;
		String outputText = null;
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, this.key);
			outputText = new String(cipher.doFinal(Base64.decode(inputText)));
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return outputText;
	}
	
	public static void main(String[] args) {
		ThreeDesUtils utils = ThreeDesUtils.getInstance();
	    String inputText = "goodboy" ;
	    String encText = utils.getEncString(inputText) ;
	    String desText = utils.getDesString(encText);
	    //System.out.println(inputText);
	    //System.out.println(encText);
	    //System.out.println(desText);
	}

}