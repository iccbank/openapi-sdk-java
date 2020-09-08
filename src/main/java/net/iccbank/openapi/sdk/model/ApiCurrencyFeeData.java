package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiCurrencyFeeData implements Serializable {
	
	private static final long serialVersionUID = -2211762277867821447L;
	
	private String feeCurrencyCode;
	private String bizFee;
	private String minMinerFee;
	private String maxMinerFee;
	
	public ApiCurrencyFeeData() {}

	public String getFeeCurrencyCode() {
		return feeCurrencyCode;
	}

	public void setFeeCurrencyCode(String feeCurrencyCode) {
		this.feeCurrencyCode = feeCurrencyCode;
	}

	public String getBizFee() {
		return bizFee;
	}

	public void setBizFee(String bizFee) {
		this.bizFee = bizFee;
	}

	public String getMinMinerFee() {
		return minMinerFee;
	}

	public void setMinMinerFee(String minMinerFee) {
		this.minMinerFee = minMinerFee;
	}

	public String getMaxMinerFee() {
		return maxMinerFee;
	}

	public void setMaxMinerFee(String maxMinerFee) {
		this.maxMinerFee = maxMinerFee;
	}

	
}
