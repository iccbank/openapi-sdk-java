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

	public static final String AGENCY_WITHDRAW2_URL = VERSION + "/agentPay/proxyPay2";
	
	public static final String AGENCY_WITHDRAW_QUERY_URL = VERSION + "/agentPay/query";

	public static final String GET_BALANCE = VERSION + "/mch/getBalance";
	
	public static final String CURRENCY_SEARCH = VERSION + "/currency/search";
	
	public static final String CURRENCY_ADD_TOKEN = VERSION + "/currency/addToken";

	public static final String PROXY_SCANNING_ADDRESS_REG = VERSION + "/proxyScanning/addressReg";

	public static final String UNSPENT_LIST = VERSION + "/unspent/list";

	public static final String UNSPENT_GET_BALANCE_BY_ADDRESS = VERSION + "/unspent/getBalanceByAddress";

	public static final String CURRENCY_FEE = VERSION + "/currency/bizFee";

	public static final String QUERY_CURRENCY_CHAIN_LIST = VERSION + "/currency/queryChainList";

	public static final String GET_CURRENCY_BY_CODE = VERSION + "/currency/getByCode";

	public static final String GET_CONVERSION_STATUS = VERSION + "/conversion/getConversionStatus";

	public static final String GET_CONVERSION_DETAIL = VERSION + "/conversion/getConversionDetail";

	public static final String GET_CONVERSION_LIST = VERSION + "/conversion/getConversionList";

	public static final String GET_CONVERSION_RATE = VERSION + "/conversion/getConversionRate";


	public static final String CONVERSION_CURRENCY_LIST = VERSION + "/conversion/getConversionCurrencies";

	public static final String CURRENCY_MINE_FEE_LIST = VERSION + "/conversion/getCurrencyMineFeeList";

	public static final String CREATE_FIX_RATE_CONVERSION = VERSION + "/conversion/createFixRateConversion";

	public static final String CREATE_FLOAT_RATE_CONVERSION = VERSION + "/conversion/createFloatRateConversion";


	public static final String SWAP_ADD_LIQUIDITY = VERSION + "/swap/addLiquidity";

	public static final String SWAP_REMOVE_LIQUIDITY = VERSION + "/swap/removeLiquidity";

	public static final String SWAP_CREATE_TRANSACTION = VERSION + "/swap/createSwapTransaction";

	public static final String SWAP_QUERY_ADD_LIQUIDITY = VERSION + "/swap/queryAddLiquidity";

	public static final String SWAP_QUERY_REMOVE_LIQUIDITY = VERSION + "/swap/queryRemoveLiquidity";

	public static final String SWAP_QUERY_STATUS = VERSION + "/swap/querySwapStatus";

	public static final String SWAP_QUERY_DETAIL = VERSION + "/swap/querySwapDetail";


	public static final String GET_TOTAL_BALANCE = VERSION + "/mch/getTotalBalance";

	public static final String QUERY_TX_FEE_ADD_LIQUIDITY = VERSION + "/swap/queryTxFeeAddLiquidity";
	public static final String QUERY_TX_FEE_REMOVE_LIQUIDITY = VERSION + "/swap/queryTxFeeRemoveLiquidity";
	public static final String QUERY_TX_FEE_SWAP_TRANSACTION = VERSION + "/swap/queryTxFeeSwapTransaction";

	public static final String GET_MINER_POWER = VERSION + "/minerPower/getMinerPower";

	/** 跨链资产相关 */
	public static final String CROSS_CHAIN_QUERY_SUBSCRIPTION_ORDER = VERSION + "/crosschain/querySubscriptionOrder";

	public static final String CROSS_CHAIN_QUERY_REDEMPTION_ORDER = VERSION + "/crosschain/queryRedemptionOrder";

	public static final String CROSS_CHAIN_CREATE_SUBSCRIPTION_ORDER = VERSION + "/crosschain/createSubscriptionOrder";

	public static final String CROSS_CHAIN_CREATE_REDEMPTION_ORDER = VERSION + "/crosschain/createRedemptionOrder";

	public static final String CROSS_CHAIN_ESTIMATE_MINER_FEE_REDEMPTION_ORDER = VERSION + "/crosschain/estimateMinerFeeRedemptionOrder";

	public static final String concatUrl(String urlPrefix, String urlSuffix) {
		return urlPrefix + urlSuffix;
	}

}
