package net.iccbank.openapi.sdk.model;

import net.iccbank.openapi.sdk.ApiConstants;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 4240925505902182011L;

	private int code;

    private String msg;

    private String subCode;

    private String subMsg;

    private T data;
    
    public ApiResponse() {
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}

	public void setSubMsg(String subMsg) {
		this.subMsg = subMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

    public boolean isSuccess(){
		if(this.code == 200 && ApiConstants.RES_SUB_CODE_SUCCESS.equalsIgnoreCase(this.subCode)){
			return true;
		}
		return false;
	}

}
