package com.wiser.library.util;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Wiser
 * 
 *         AES加密算法工具类
 */
public class WISERAESTool {

	/**
	 * <p>
	 * 加密解密器
	 * </p>
	 * 
	 * @param password
	 *            密钥
	 * @param cipHerType
	 *            加密器执行模式
	 * @return 加密解密器
	 */
	private static Cipher getAESCipher(String password, int cipHerType) {
		// 算法名称
		String algorithmName = "AES";
		try {

			// 密钥生成器
			KeyGenerator kgen = KeyGenerator.getInstance(algorithmName);
			// 使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥大小
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());

			kgen.init(128, secureRandom);
			// 生成一个密钥
			SecretKey secretKey = kgen.generateKey();
			// 返回基本编码格式的密钥
			byte[] enCodeFormat = secretKey.getEncoded();
			// 根据一个字节数组构造一个 SecretKey
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithmName);
			// 创建密码器
			@SuppressLint("GetInstance")
			Cipher cipher = Cipher.getInstance(algorithmName);
			// 初始化为加密模式的常量。
			cipher.init(cipHerType, key);
			return cipher;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 加密
	 * </p>
	 * 
	 * @param byteContent
	 *            需要加密的内容
	 * @param password
	 *            密钥
	 * @return 获取加密后的字节数组
	 */
	private static byte[] encrypt(byte[] byteContent, String password) {
		try {
			// 获取加密模式下的加密器
			Cipher cipher = getAESCipher(password, Cipher.ENCRYPT_MODE);
			// 加密后的字节数组
			assert cipher != null;
			return cipher.doFinal(byteContent);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 解密
	 * </p>
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            密钥
	 * @return 解密后的字节数组
	 */
	private static byte[] decrypt(byte[] content, String password) {
		try {
			Cipher cipher = getAESCipher(password, Cipher.DECRYPT_MODE);// 初始化
			assert cipher != null;
			return cipher.doFinal(content);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 将二进制转换成16进制
	 * </p>
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte[] buf) {
		StringBuilder sb = new StringBuilder();
		for (byte b : buf) {
			sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	/**
	 * <p>
	 * 将16进制转换为二进制
	 * </p>
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 获取加密后的密文
	 * 
	 * @param content
	 *            明文内容
	 * @param password
	 *            秘钥
	 * @return ciphertext 加密后的字串
	 */
	public static String encode(String content, String password) {
		byte[] byteContent = null;
		try {
			byteContent = content.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] encryptResult = encrypt(byteContent, password);
		assert encryptResult != null;
		return parseByte2HexStr(encryptResult);
	}

	/**
	 * <p>
	 * 获取明文
	 * </p>
	 * 
	 * @param encodeContent
	 *            加密文的内容
	 * @param password
	 *            密钥
	 * @return 明文
	 */
	public static String decode(String encodeContent, String password) {
		byte[] decryptBytes = parseHexStr2Byte(encodeContent);
		byte[] decryptResult = decrypt(decryptBytes, password);
		return new String(decryptResult);
	}

}
