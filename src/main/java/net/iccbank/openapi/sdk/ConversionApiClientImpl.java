package net.iccbank.openapi.sdk;

import com.fasterxml.jackson.core.type.TypeReference;

import net.iccbank.openapi.sdk.enums.ErrorCodeEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.*;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.model.conversion.CreateFixRateConversion;
import net.iccbank.openapi.sdk.model.conversion.CreateFloatRateConversion;
import net.iccbank.openapi.sdk.model.page.PageBO;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
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
            throw ICCBankException.buildException(ErrorCodeEnum.REMOTE_REQUEST_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
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
            throw ICCBankException.buildException(ErrorCodeEnum.SIGN_ERROR, "[Build Signature] error: " + e.getMessage());
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
            throw ICCBankException.buildException(ErrorCodeEnum.ENCRYPT_ERROR, "[Encrypt Signature] error: " + e.getMessage());
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
                throw ICCBankException.buildException(ErrorCodeEnum.DECRYPT_ERROR, "[Decrypt Signature] error: " + e.getMessage());
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

    @Override
    public ApiResponse<CreateFixRateConversion> createFixRateConversion(String source, String orderId, Long rateId, String code,
                                                                        String payoutAddress, String payoutLabelAddress, String refundAddress, String refundLabelAddress,
                                                                        BigDecimal amountExpectedFrom, BigDecimal amountExpectedTo) {
        if (StringUtils.isBlank(source)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [source] required");
        }
        if (rateId == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [rateId] required");
        }
        if (StringUtils.isBlank(code)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [code] required");
        }
        if (StringUtils.isBlank(payoutAddress)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [payoutAddress] required");
        }
        if (StringUtils.isBlank(refundAddress)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [refundAddress] required");
        }

        if (amountExpectedFrom == null && amountExpectedTo == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"amountFrom,amountTo cannot be empty at the same time");
        }
        if (amountExpectedFrom != null && amountExpectedTo != null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"amountFrom,amountTo only one can be selected");
        }
        if (amountExpectedFrom != null && amountExpectedFrom.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [refundAddress] must greater than 0");
        }
        if (amountExpectedTo != null && amountExpectedTo.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR,"parameter [refundAddress] must greater than 0");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("source", source);
        paramsMap.put("orderId", orderId);
        paramsMap.put("rateId", rateId);
        paramsMap.put("code", code);
        paramsMap.put("payoutAddress", payoutAddress);
        paramsMap.put("payoutLabelAddress", payoutLabelAddress);
        paramsMap.put("refundAddress", refundAddress);
        paramsMap.put("refundLabelAddress", refundLabelAddress);
        paramsMap.put("amountExpectedFrom", amountExpectedFrom != null ? amountExpectedFrom.toPlainString() : null);
        paramsMap.put("amountExpectedTo", amountExpectedTo != null ? amountExpectedTo.toPlainString() : null);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CREATE_FIX_RATE_CONVERSION);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<CreateFixRateConversion>>() {});
    }

    @Override
    public ApiResponse<CreateFloatRateConversion> createFloatRateConversion(String source, String orderId, String code,
                                                                            String payoutAddress, String payoutLabelAddress, BigDecimal amountExpectedFrom) {
        if (StringUtils.isBlank(source)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [source] required");
        }
        if (StringUtils.isBlank(code)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [code] required");
        }
        if (StringUtils.isBlank(payoutAddress)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [payoutAddress] required");
        }
        if (amountExpectedFrom == null || amountExpectedFrom.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [payoutAddress] required or must greater than 0");
        }
        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("source", source);
        paramsMap.put("orderId", orderId);
        paramsMap.put("code", code);
        paramsMap.put("payoutAddress", payoutAddress);
        paramsMap.put("payoutLabelAddress", payoutLabelAddress);
        paramsMap.put("amountExpectedFrom", amountExpectedFrom.toPlainString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.CREATE_FLOAT_RATE_CONVERSION);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<CreateFloatRateConversion>>() {});
    }

    @Override
    public ApiResponse<ConversionRate> getConversionRate(ConversionRateReq param) {

        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("code", param.getCode());
        paramsMap.put("amountFrom", param.getAmountFrom() != null ? param.getAmountFrom().toPlainString() : null);
        paramsMap.put("amountTo", param.getAmountTo() != null ? param.getAmountTo().toPlainString() : null);
        paramsMap.put("fixedRate", param.getFixedRate().toString());

        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_CONVERSION_RATE);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ConversionRate>>(){});
    }


    @Override
    public ApiResponse<ConversionOrderDetail> getConversionOrderDetail(String orderId) {
        if (orderId == null || orderId.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [orderId] required");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("thirdOrderId", orderId);

        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_CONVERSION_DETAIL);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ConversionOrderDetail>>(){});
    }

    @Override
    public ApiResponse<ConversionOrderStatus> getConversionOrderStatus(String orderId) {
        if (orderId == null || orderId.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [orderId] required");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("thirdOrderId", orderId);

        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_CONVERSION_STATUS);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ConversionOrderStatus>>(){});
    }


    @Override
    public ApiResponse<PageBO<ConversionOrderDetail>> getConversionOrderList(GetConversionListReq req) {

        TreeMap<String, Object> paramsMap = new TreeMap<>();
        if(req != null){
            paramsMap.put("orderSource", req.getOrderSource());
            paramsMap.put("currencyFrom", req.getCurrencyFrom());
            paramsMap.put("currencyTo", req.getCurrencyTo());
            paramsMap.put("rateType", req.getRateType());
            paramsMap.put("status", req.getStatus());
            paramsMap.put("refundStatus", req.getRefundStatus());
            paramsMap.put("pageIndex", req.getPageIndex());
            paramsMap.put("pageSize", req.getPageSize());
        }


        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.GET_CONVERSION_LIST);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<PageBO<ConversionOrderDetail>>>(){});
    }

}
