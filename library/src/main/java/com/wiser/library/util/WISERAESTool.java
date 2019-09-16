package com.wiser.library.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Wiser
 * 
 *         AES加密算法工具类
 */
public class WISERAESTool {

	/*
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	// private String sKey = "1234567890123456"; // key，加密的key TODO 必须是16位
	// private String ivParameter = "1201230125462244"; // 偏移量,4*4矩阵 TODO 必须16位

	/**
	 * 加密
	 *
	 * @param content
	 *            要加密的内容
	 * @param secretKey
	 *            加密的秘钥
	 * @param iv
	 *            偏移量
	 * @return 加密后的字符串
	 */
	public static String encode(String content, String secretKey, String iv) {
		if (WISERCheck.isEmpty(content) || WISERCheck.isEmpty(secretKey) || WISERCheck.isEmpty(iv) || secretKey.length() != 16 || iv.length() != 16) return "";
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = secretKey.getBytes();
			SecretKeySpec mKeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, mKeySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
			return WISERBase64.encode(encrypted).replaceAll("\r|\n", "");// 此处使用BASE64做转码。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 解密
	 *
	 * @param encodeContent
	 *            要解密的内容
	 * @param secretKey
	 *            解密要秘钥
	 * @param iv
	 *            偏移量
	 * @return 解密后的字符串
	 */
	public static String decode(String encodeContent, String secretKey, String iv) {
		if (WISERCheck.isEmpty(encodeContent) || WISERCheck.isEmpty(secretKey) || WISERCheck.isEmpty(iv)) return "";
		try {
			byte[] raw = secretKey.getBytes("ASCII");
			SecretKeySpec mKeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, mKeySpec, ivParameterSpec);
			byte[] encrypted = WISERBase64.decode(encodeContent);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted);
			return new String(original, "utf-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
