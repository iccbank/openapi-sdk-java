package net.iccbank.openapi.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import net.iccbank.openapi.sdk.enums.SearchTypeEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.*;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


public class DefaultApiClient extends HttpClient implements ApiClient, Encryptable {
	
	private String urlPrefix;
	private String appId;
	private String appSecret;
	private String token;
	
	/**
	 * ApiClient
	 * 
	 * @param appId AppID
	 * @param appSecret 应用密钥
	 * @param token 报文加解密的token
	 */
	public DefaultApiClient(String appId, String appSecret, String token) {
		this(null, appId, appSecret, token);
	}

	/**
	 * ApiClient
	 * 
	 * @param urlPrefix 接口前缀, 默认 https://api.iccbank.net/
	 * @param appId AppID
	 * @param appSecret 应用密钥
	 * @param token 报文加解密的token
	 */
	public DefaultApiClient(String urlPrefix, String appId, String appSecret, String token) {
		this(urlPrefix, appId, appSecret, token, 30, 30, 30);
	}
	
	/**
	 * ApiClient
	 * 
	 * @param urlPrefix 接口前缀, 默认 https://api.iccbank.net/
	 * @param appId AppID
	 * @param appSecret 应用密钥
	 * @param token 报文加解密的token
	 * @param connectTimeout 
	 * @param writeTimeout 
	 * @param readTimeout 
	 */
	public DefaultApiClient(String urlPrefix, String appId, String appSecret, String token, long connectTimeout, long writeTimeout, long readTimeout) {
		if (urlPrefix == null) {
			this.urlPrefix = ApiConstants.URL_PREFIX;
		} else {
			this.urlPrefix = urlPrefix;
			if (!urlPrefix.endsWith("/")) {
				this.urlPrefix = this.urlPrefix + "/";
			}
		}
		this.appId = appId;
		this.appSecret = appSecret;
		this.token = token;
		
		this.connectTimeout = connectTimeout;
		this.writeTimeout = writeTimeout;
		this.readTimeout = readTimeout;
	}

	@Override
	public ApiResponse<Object> addressCheck(String currencyCode, String address) {
		return addressCheck(currencyCode, address, null);
	}

	@Override
	public ApiResponse<Object> addressCheck(String currencyCode, String address, String labelAddress) {
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [address] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		paramsMap.put("labelAddress", labelAddress);
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.ADDRESS_CHECK_URL);
		ApiResponse<Object> res = call(url, paramsMap);
		return res;
	}

	/**
	 * 代付地址注册
	 *
	 * @param currencyCode 币种代码
	 * @param address 代付地址
	 * @param labelAddress 地址标签, EOS或XRP等地址使用（非必填）
	 */
	@Override
	public ApiResponse<Object> agentPayAddAddress(String currencyCode, String address, String labelAddress){
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [address] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		if (labelAddress != null) {
			paramsMap.put("labelAddress", labelAddress);
		}

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENT_PAY_ADD_ADDRESS_URL);
		ApiResponse<Object> res = call(url, paramsMap);
		return res;
	}
	

	/**
	 * 创建代收地址
	 *
	 * @param currencyCode 币种代码
	 * @param count 地址数量(1-100)
	 * @param batchNumber 批次号
	 */
	@Override
	public ApiResponse<List<ApiAddress>> createAgencyRechargeAddress(String currencyCode, int count, String batchNumber) {
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		
		if (count <= 0 || count > 100) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [count] out of range [1-100]");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("count", count);
		paramsMap.put("batchNumber", batchNumber);
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.ADDRESS_AGENCY_CREATE_URL);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ApiAddress>>>(){});
	}
	
	@Override
	public ApiResponse<ApiAgencyWithdrawData> agencyWithdraw(String userBizId, String subject, String currencyCode,
															 String address, String labelAddress, BigDecimal amount, String notifyUrl) {
		if (userBizId == null || userBizId.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [userBizId] required");
		}
		
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [address] required");
		}
		
		if (amount == null) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [amount] required");
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [amount] invalid");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("userBizId", userBizId);
		if (subject != null) {
			paramsMap.put("subject", subject);
		}
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		if (labelAddress != null) {
			paramsMap.put("labelAddress", labelAddress);
		}
		if (notifyUrl != null) {
			paramsMap.put("notifyUrl", notifyUrl);
		}
		paramsMap.put("amount", amount.toPlainString());

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENCY_WITHDRAW_URL);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAgencyWithdrawData>>(){});
	}
	
	@Override
	public ApiResponse<ApiAgencyWithdrawData> agencyWithdrawWithMinerFee(String userBizId, String subject,
			String currencyCode, String address, String labelAddress, BigDecimal amount, BigDecimal minerFee,
			String notifyUrl) {
		if (userBizId == null || userBizId.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [userBizId] required");
		}
		
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [address] required");
		}
		
		if (amount == null) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [amount] required");
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [amount] invalid");
		}

		if (minerFee.compareTo(BigDecimal.ZERO) <= 0) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [minerFee] invalid");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("userBizId", userBizId);
		if (subject != null) {
			paramsMap.put("subject", subject);
		}
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		if (labelAddress != null) {
			paramsMap.put("labelAddress", labelAddress);
		}
		if (notifyUrl != null) {
			paramsMap.put("notifyUrl", notifyUrl);
		}
		paramsMap.put("amount", amount.stripTrailingZeros().toPlainString());
		paramsMap.put("minerFee", minerFee.stripTrailingZeros().toPlainString());

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENCY_WITHDRAW2_URL);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAgencyWithdrawData>>(){});
	}
	
	@Override
	public ApiResponse<ApiAgencyWithdrawQueryData> queryAgencyWithdrawOrder(String userBizId) {
		if (userBizId == null || userBizId.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [userBizId] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("userBizId", userBizId);
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENCY_WITHDRAW_QUERY_URL);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAgencyWithdrawQueryData>>(){});
	}

	@Override
	public ApiResponse<ApiMchBalance> getBalances() {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
	}
	
	@Override
	public ApiResponse<List<ApiCurrencyData>> currencySearch(int searchType, String keywords) {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOfByType(searchType);
		if (searchTypeEnum == null) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "searchType '" + searchType + "' unSupport");
		}
		
		if (keywords == null || keywords.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [keywords] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("searchType", searchTypeEnum.getType());
		paramsMap.put("keywords", keywords);
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CURRENCY_SEARCH);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ApiCurrencyData>>>(){});
	}

	@Override
	public ApiResponse<ApiCurrencyData> currencyAddToken(String linkType, String contractAddress) {
		if (linkType == null || linkType.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [linkType] required");
		}

		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [contractAddress] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("linkType", linkType);
		paramsMap.put("contractAddress", contractAddress);
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CURRENCY_ADD_TOKEN);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiCurrencyData>>(){});
	}
	
	@Override
	public ApiResponse<ApiCurrencyFeeData> getCurrencyFee(String currencyCode) {
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CURRENCY_FEE);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiCurrencyFeeData>>(){});
	}
	
	@Override
	public ApiResponse<List<ApiCurrencyData>> queryCurrencyChainList() {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.QUERY_CURRENCY_CHAIN_LIST);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ApiCurrencyData>>>(){});
	}
	
	@Override
	public ApiResponse<ApiCurrencyData> getCurrencyByCode(String currencyCode) {
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		
		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_CURRENCY_BY_CODE);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiCurrencyData>>(){});
	}
	
	@Override
	public ApiResponse<ApiMchBalance> getBalancesForCurrencyCode(String currencyCode) {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();

		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		paramsMap.put("currencyCode", currencyCode);

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
	}

	@Override
	public ApiResponse<ApiMchBalance.BalanceNode> getBalancesForCurrencyCodeAndAccountType(String currencyCode, Long accountType) {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [currencyCode] required");
		}
		if (accountType == null) {
			throw ICCBankException.buildException(ICCBankException.INPUT_ERROR,"parameter [accountType] required");
		}
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("accountType", accountType);

		String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
		String resBody = callToString(url, paramsMap);
		ApiMchBalance.BalanceNode balanceNode = ApiMchBalance.BalanceNode.builder()
				.build();

		ApiResponse<ApiMchBalance.BalanceNode> response = new ApiResponse<ApiMchBalance.BalanceNode>();
		if(resBody != null){
			ApiResponse<ApiMchBalance> result = JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
			if(result != null && result.getData() != null){
				List<ApiMchBalance.BalanceNode> list = result.getData().getRows();
				if(list != null && !list.isEmpty()){
					balanceNode = list.get(0);
					response.setCode(result.getCode());
					response.setMsg(result.getMsg());
					response.setSubCode(result.getSubCode());
					response.setData(balanceNode);
				}
			}
		}

		return response;
	}

	@Override
	public String encrypt(String data) {
		try {
			String encryptedData = AlgorithmUtils.encryptWith3DES(data, token);
			ApiEncryptedBody reqBody = new ApiEncryptedBody(ApiConstants.ALGORITHM_DESEDE, encryptedData);
			return JsonUtils.toJsonString(reqBody);
		} catch (Exception e) {
			throw ICCBankException.buildException(ICCBankException.RUNTIME_ERROR, "[Encrypt Signature] error: " + e.getMessage());
		}
	}
	
	@Override
	public String decrypt(String encryptedResBody) {

		String resPlainData = null;

		TreeMap<String, Object> resJson = JsonUtils.parseTreeMap(encryptedResBody);
		if(resJson.containsKey(ApiConstants.RES_ENCRYPTED_DATA)){
			try {
				ApiEncryptedBody resBody = JsonUtils.parseObject(encryptedResBody, ApiEncryptedBody.class);

				resPlainData = AlgorithmUtils.decryptWith3DES(resBody.getEncryptedData(), token);
			} catch (Exception e) {
				throw ICCBankException.buildException(ICCBankException.RUNTIME_ERROR, "[Decrypt Signature] error: " + e.getMessage());
			}

		}else {
			//针对没有加密报文体
			resPlainData = encryptedResBody;
		}

		return resPlainData;
	}
	
	private <T> ApiResponse<T> call(String url, TreeMap<String, Object> paramsMap){
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<T>>(){});
	}

	private String callToString(String url, TreeMap<String, Object> paramsMap){
		// 签名
		String reqBody = sign(paramsMap);
		
		// 加密
		String encryptedReqBody = encrypt(reqBody);
		
		// 请求
		String encryptedResBody = null;
		try {
			//请求响应
			encryptedResBody = callPost(url, initHeaders(), encryptedReqBody);
		} catch (IOException e) {
			throw ICCBankException.buildException(ICCBankException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
		}

		// AES解密，返回值不需要验证签名
		String resBody = decrypt(encryptedResBody);

		ApiResponse apiResponse = JsonUtils.parseObject(resBody, ApiResponse.class);
		if(apiResponse != null){
			if(!apiResponse.isSuccess()){
				throw ICCBankException.buildException(apiResponse.getSubCode(), apiResponse.getSubMsg());
			}
		}

		return resBody;
	}

	private String sign(TreeMap<String, Object> paramsMap) {
		paramsMap.putAll(initCommonParameters());
		//String json = JsonUtils.toJsonString(paramsMap);
		String plainText = getText(paramsFilter(paramsMap), "&");
		try {
			String sign = AlgorithmUtils.signWithRSA(plainText, appSecret);
			paramsMap.put(ApiConstants.PARAMETER_SIGN, sign);
			return JsonUtils.toJsonString(paramsMap);
		} catch (Exception e) {
			throw ICCBankException.buildException(ICCBankException.RUNTIME_ERROR, "[Build Signature] error: " + e.getMessage());
		}
	}

	/**
	 * @param params 签名参数
	 * @return 返回参与MD5签名的Map参数集合
	 * @description 过滤掉签名参数中的空值以及签名参数，使其不参与签名
	 */
	public static Map<String, String> paramsFilter(Map<String, Object> params) {
		Map<String, String> result = new TreeMap<String, String>();
		if (params == null || params.size() <= 0) {
			return result;
		}
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equals("signKey")) {
				continue;
			}
			result.put(key, value.toString());
		}
		return result;
	}


	/**
	 * @param params 签名参数
	 * @param join   连接符
	 * @return 返回签名参数经过连接符拼装过后的字符串
	 * @description 将签名参数根据连接符进行拼装，组成我们想要格式的字符串（不包含签名秘钥）
	 */
	public static String getText(Map<String, String> params, String join) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String text = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				text = text + key + "=" + value;
			} else {
				text = text + key + "=" + value + join;
			}
		}
		return text;
	}

	private Map<String, String> initHeaders(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(ApiConstants.HEADER_OPENAPI_APP_ID, appId);
		return map;
	}
	
	private TreeMap<String, Object> initCommonParameters(){
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		map.put(ApiConstants.PARAMETER_APP_ID, appId);
		map.put(ApiConstants.PARAMETER_TIMESTAMP, System.currentTimeMillis());
		map.put(ApiConstants.PARAMETER_NONCE, UUID.randomUUID().toString().replaceAll("-", ""));
		map.put(ApiConstants.PARAMETER_SIGN_TYPE, ApiConstants.ALGORITHM_RSA);
		return map;
	}

}
