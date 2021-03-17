package net.iccbank.openapi.sdk.enums;

public enum ErrorCodeEnum {
	
	PARAMETER_ERROR("90000000","参数错误"),
	REMOTE_REQUEST_ERROR("90000001","远程请求失败"),
	SIGN_ERROR("90000002","数据签名失败"),
	VERIFY_SIGN_ERROR("90000003","数据验签失败"),
	ENCRYPT_ERROR("90000004","数据加密失败"),
	DECRYPT_ERROR("90000005","数据解密失败"),
	;
	private String subCode;
    private String subMsg;
    
    ErrorCodeEnum(String subCode,String subMsg){
    	this.subCode = subCode;
    	this.subMsg = subMsg;
    }

	public String getSubCode() {
		return subCode;
	}

	public String getSubMsg() {
		return subMsg;
	}
}