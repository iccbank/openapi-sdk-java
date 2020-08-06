package net.iccbank.openapi.sdk;

/**
 * 加密接口
 */
public interface Encryptable {

	/**
	 * 加密
	 */
	String encrypt(String data);
	
	/**
	 * 解密
	 */
	String decrypt(String data);
	
}
