package net.iccbank.openapi.sdk.model;

import java.io.Serializable;

public class ApiAddress implements Serializable {
	
	private static final long serialVersionUID = 8743713632041268329L;
	
	private String currencyCode;
	private String address;
	private String labelAddress;

	public ApiAddress() {}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLabelAddress() {
		return labelAddress;
	}

	public void setLabelAddress(String labelAddress) {
		this.labelAddress = labelAddress;
	}
	
	
}
