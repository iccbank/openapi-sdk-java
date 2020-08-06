package net.iccbank.openapi.sdk;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;

import net.iccbank.openapi.sdk.model.ApiAddress;
import net.iccbank.openapi.sdk.model.ApiAgencyWithdrawData;
import net.iccbank.openapi.sdk.model.ApiAgencyWithdrawQueryData;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.model.ApiMchBalance;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;


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
	 * @param url 接口前缀, 默认 https://api.iccbank.net/
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
	 * @param url 接口前缀, 默认 https://api.iccbank.net/
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
			throw new RuntimeException("parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw new RuntimeException("parameter [address] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		paramsMap.put("labelAddress", labelAddress);
		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.ADDRESS_CHECK_URL);
			ApiResponse<Object> res = call(url, paramsMap);
			return res;
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
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
			throw new RuntimeException("parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw new RuntimeException("parameter [address] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("address", address);
		if (labelAddress != null) {
			paramsMap.put("labelAddress", labelAddress);
		}

		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENT_PAY_ADD_ADDRESS_URL);
			ApiResponse<Object> res = call(url, paramsMap);
			return res;
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}
	
	@Override
	/**
	 * 创建代收地址
	 *
	 * @param currencyCode 币种代码
	 * @param count 地址数量(1-100)
	 * @param batchNumber 批次号
	 */
	public ApiResponse<List<ApiAddress>> createAgencyRechargeAddress(String currencyCode, int count, String batchNumber) {
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw new RuntimeException("parameter [currencyCode] required");
		}
		
		if (count <= 0 || count > 100) {
			throw new RuntimeException("parameter [count] out of range [1-100]");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("count", count);
		paramsMap.put("batchNumber", batchNumber);
		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.ADDRESS_AGENCY_CREATE_URL);
			String resBody = callToString(url, paramsMap);
			return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ApiAddress>>>(){});
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}
	
	@Override
	public ApiResponse<ApiAgencyWithdrawData> agencyWithdraw(String userBizId, String subject, String currencyCode,
															 String address, String labelAddress, BigDecimal amount, String notifyUrl) {
		if (userBizId == null || userBizId.trim().equals("")) {
			throw new RuntimeException("parameter [userBizId] required");
		}
		
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw new RuntimeException("parameter [currencyCode] required");
		}
		
		if (address == null || address.trim().equals("")) {
			throw new RuntimeException("parameter [address] required");
		}
		
		if (amount == null) {
			throw new RuntimeException("parameter [amount] required");
		}

		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("parameter [amount] invalid");
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

		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENCY_WITHDRAW_URL);
			String resBody = callToString(url, paramsMap);
			return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAgencyWithdrawData>>(){});
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}
	
	@Override
	public ApiResponse<ApiAgencyWithdrawQueryData> queryAgencyWithdrawOrder(String userBizId) {
		if (userBizId == null || userBizId.trim().equals("")) {
			throw new RuntimeException("parameter [userBizId] required");
		}
		
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		paramsMap.put("userBizId", userBizId);
		
		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.AGENCY_WITHDRAW_QUERY_URL);
			String resBody = callToString(url, paramsMap);
			return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAgencyWithdrawQueryData>>(){});
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}

	@Override
	public ApiResponse<ApiMchBalance> getBalances() {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();

		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
			String resBody = callToString(url, paramsMap);
			return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}

	@Override
	public ApiResponse<ApiMchBalance> getBalancesForCurrencyCode(String currencyCode) {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();

		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw new RuntimeException("parameter [currencyCode] required");
		}
		paramsMap.put("currencyCode", currencyCode);

		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
			String resBody = callToString(url, paramsMap);
			return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}

	@Override
	public ApiResponse<ApiMchBalance.BalanceNode> getBalancesForCurrencyCodeAndAccountType(String currencyCode, Long accountType) {
		TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
		if (currencyCode == null || currencyCode.trim().equals("")) {
			throw new RuntimeException("parameter [currencyCode] required");
		}
		if (accountType == null) {
			throw new RuntimeException("parameter [accountType] required");
		}
		paramsMap.put("currencyCode", currencyCode);
		paramsMap.put("accountType", accountType);

		try {
			String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_BALANCE);
			String resBody = callToString(url, paramsMap);
			ApiMchBalance.BalanceNode balanceNode = ApiMchBalance.BalanceNode.builder()
					.build();

			ApiResponse<ApiMchBalance.BalanceNode> response = new ApiResponse<ApiMchBalance.BalanceNode>();
			if(resBody != null){
				ApiResponse<ApiMchBalance> result = JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiMchBalance>>(){});
				List<ApiMchBalance.BalanceNode> list = result.getData().getRows();
				if(result != null && !list.isEmpty()){
					balanceNode = list.get(0);
					response.setCode(result.getCode());
					response.setMsg(result.getMsg());
					response.setSubCode(result.getSubCode());
					response.setData(balanceNode);
				}
			}


			return response;
		} catch (IOException e) {
			throw new RuntimeException("请求异常", e);
		}
	}

	@Override
	public String encrypt(String data) {
		try {
			String encryptedData = AlgorithmUtils.encryptWith3DES(data, token);
			ApiEncryptedBody reqBody = new ApiEncryptedBody(ApiConstants.ALGORITHM_DESEDE, encryptedData);
			return JsonUtils.toJsonString(reqBody);
		} catch (Exception e) {
			throw new RuntimeException("加密异常", e);
		}
	}
	
	@Override
	public String decrypt(String data) {
		try {
			ApiEncryptedBody resBody = JsonUtils.parseObject(data, ApiEncryptedBody.class);
			String resPlainData = AlgorithmUtils.decryptWith3DES(resBody.getEncryptedData(), token);
			return resPlainData;
		} catch (Exception e) {
			throw new RuntimeException("解密异常", e);
		}
	}
	
	private <T> ApiResponse<T> call(String url, TreeMap<String, Object> paramsMap) throws IOException {
		String resBody = callToString(url, paramsMap);
		return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<T>>(){});
	}

	private String callToString(String url, TreeMap<String, Object> paramsMap) throws IOException {
		// 签名
		String reqBody = sign(paramsMap);
		
		// 加密
		String encryptedReqBody = encrypt(reqBody);
		
		// 请求
		String encryptedResBody = callPost(url, initHeaders(), encryptedReqBody);
		
		// AES解密，返回值不需要验证签名
		String resBody = decrypt(encryptedResBody);

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
			throw new RuntimeException("签名异常", e);
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
