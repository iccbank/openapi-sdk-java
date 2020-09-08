package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiCurrencyData implements Serializable {
	
	private static final long serialVersionUID = -2211762277867821447L;
	
	private String name;
	private String symbol;
	private String code;
	private String linkType;
	private String icon;
	private String contractAddress;
	private int supportLabelAddress;
	private String chainCode;
	private int maxDecimals;
	
	public ApiCurrencyData() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public int getSupportLabelAddress() {
		return supportLabelAddress;
	}

	public void setSupportLabelAddress(int supportLabelAddress) {
		this.supportLabelAddress = supportLabelAddress;
	}

	public String getChainCode() {
		return chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public int getMaxDecimals() {
		return maxDecimals;
	}

	public void setMaxDecimals(int maxDecimals) {
		this.maxDecimals = maxDecimals;
	}
	
	
	
}
