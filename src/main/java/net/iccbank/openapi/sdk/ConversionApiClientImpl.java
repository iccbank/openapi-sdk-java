package net.iccbank.openapi.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;

import java.io.IOException;
import java.util.*;


public class ConversionApiClientImpl extends HttpClient implements ConversionApiClient, Encryptable {

    private String urlPrefix;
    private String appId;
    private String appSecret;
    private String token;

    /**
     * ApiClient
     *
     * @param appId     AppID
     * @param appSecret 应用密钥
     * @param token     报文加解密的token
     */
    public ConversionApiClientImpl(String appId, String appSecret, String token) {
        this(null, appId, appSecret, token);
    }

    /**
     * ApiClient
     *
     * @param urlPrefix 接口前缀, 默认 https://api.iccbank.net/
     * @param appId     AppID
     * @param appSecret 应用密钥
     * @param token     报文加解密的token
     */
    public ConversionApiClientImpl(String urlPrefix, String appId, String appSecret, String token) {
        this(urlPrefix, appId, appSecret, token, 30, 30, 30);
    }

    /**
     * ApiClient
     *
     * @param urlPrefix      接口前缀, 默认 https://api.iccbank.net/
     * @param appId          AppID
     * @param appSecret      应用密钥
     * @param token          报文加解密的token
     * @param connectTimeout
     * @param writeTimeout
     * @param readTimeout
     */
    public ConversionApiClientImpl(String urlPrefix, String appId, String appSecret, String token, long connectTimeout, long writeTimeout, long readTimeout) {
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

    private String callToString(String url, TreeMap<String, Object> paramsMap) {
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
        if (apiResponse != null) {
            if (!apiResponse.isSuccess()) {
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

    private Map<String, String> initHeaders() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(ApiConstants.HEADER_OPENAPI_APP_ID, appId);
        return map;
    }

    private TreeMap<String, Object> initCommonParameters() {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put(ApiConstants.PARAMETER_APP_ID, appId);
        map.put(ApiConstants.PARAMETER_TIMESTAMP, System.currentTimeMillis());
        map.put(ApiConstants.PARAMETER_NONCE, UUID.randomUUID().toString().replaceAll("-", ""));
        map.put(ApiConstants.PARAMETER_SIGN_TYPE, ApiConstants.ALGORITHM_RSA);
        return map;
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
        if (resJson.containsKey(ApiConstants.RES_ENCRYPTED_DATA)) {
            try {
                ApiEncryptedBody resBody = JsonUtils.parseObject(encryptedResBody, ApiEncryptedBody.class);

                resPlainData = AlgorithmUtils.decryptWith3DES(resBody.getEncryptedData(), token);
            } catch (Exception e) {
                throw ICCBankException.buildException(ICCBankException.RUNTIME_ERROR, "[Decrypt Signature] error: " + e.getMessage());
            }

        } else {
            //针对没有加密报文体
            resPlainData = encryptedResBody;
        }

        return resPlainData;
    }

    @Override
    public ApiResponse<List<ConversionCurrency>> getConversionCurrencies() {
        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CONVERSION_CURRENCY_LIST);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ConversionCurrency>>>() {});
    }

    @Override
    public ApiResponse<List<ConversionCurrencyMineFee>> getCurrencyMineFeeList() {
        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CURRENCY_MINE_FEE_LIST);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<List<ConversionCurrencyMineFee>>>() {});
    }
}
