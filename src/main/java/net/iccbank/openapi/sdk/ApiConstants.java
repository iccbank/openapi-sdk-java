package net.iccbank.openapi.sdk;

public class ApiConstants {
	
	public static final String CONTENT_TYPE = "application/json; charset=utf-8";
	
	public static final String HEADER_OPENAPI_APP_ID = "OPENAPI_APP_ID";
	
	public static final String PARAMETER_APP_ID = "appId";
	public static final String PARAMETER_TIMESTAMP = "timestamp";
	public static final String PARAMETER_NONCE = "nonce";
	public static final String PARAMETER_SIGN_TYPE = "signType";
	public static final String PARAMETER_SIGN = "sign";
	public static final String RES_ENCRYPTED_DATA = "encryptedData";
	public static final String RES_SUB_CODE_SUCCESS = "0";



	public static final String ALGORITHM_AES = "AES";
	public static final String ALGORITHM_DESEDE = "DESede";
	public static final String ALGORITHM_RSA = "RSA";
	
	public static final String URL_PREFIX = "https://api.iccbank.net/";
	
	public static final String PLATFORM_NOTIFY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+Rg1laobYtbd1hMeA2qazd+HRldO83Fa60aiEj8jKIEOZYH9yhef9GMpQ1znRsd3kbZOAOAljunQ+dI3HUPOoEIP36h8nN0OWWKviGxd88vxz2+NJWRWij3PDQmeHOmgN3djaOiWk8WCXHA6DGh/adw11RDvE+1YF5Mp8369WSwIDAQAB";
	
	public static final String VERSION = "v1";
	
	public static final String ADDRESS_CHECK_URL = VERSION + "/address/check";
	
	public static final String AGENT_PAY_ADD_ADDRESS_URL = VERSION + "/agentPay/addAddress";
	
	public static final String ADDRESS_AGENCY_CREATE_URL = VERSION + "/address/agency/create";

	public static final String AGENCY_WITHDRAW_URL = VERSION + "/agentPay/proxyPay";
	
	public static final String AGENCY_WITHDRAW_QUERY_URL = VERSION + "/agentPay/query";

	public static final String GET_BALANCE = VERSION + "/mch/getBalance";
	
	public static final String CURRENCY_SEARCH = VERSION + "/currency/search";
	
	public static final String CURRENCY_ADD_TOKEN = VERSION + "/currency/addToken";

	public static final String concatUrl(String urlPrefix, String urlSuffix) {
		return urlPrefix + urlSuffix;
	}

}
