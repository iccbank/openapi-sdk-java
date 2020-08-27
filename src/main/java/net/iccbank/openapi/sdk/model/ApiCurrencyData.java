package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiCurrencyData implements Serializable {
	
	private static final long serialVersionUID = -2211762277867821447L;
	
	private String name;
	private String symbol;
	private String code;
	private String linkType;
	private String contractAddress;
	
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

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	
}
