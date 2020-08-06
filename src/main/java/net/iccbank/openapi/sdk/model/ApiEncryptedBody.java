package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiEncryptedBody implements Serializable {
	
	private static final long serialVersionUID = -6806592983193071022L;

	/**
	 * 加密算法
	 */
	private String algorithm;
	
	/**
	 * 密文数据
	 */
	private String encryptedData;
	
	public ApiEncryptedBody() {
		
	}
	
	public ApiEncryptedBody(String algorithm, String encryptedData) {
		this.algorithm = algorithm;
		this.encryptedData = encryptedData;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	
	
	
}
