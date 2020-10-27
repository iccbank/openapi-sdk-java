package net.iccbank.openapi.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import net.iccbank.openapi.sdk.enums.SwapMethodNameEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.swap.ApiAddLiquidityRes;
import net.iccbank.openapi.sdk.model.swap.ApiRemoveLiquidityRes;
import net.iccbank.openapi.sdk.model.swap.ApiSwapDetailRes;
import net.iccbank.openapi.sdk.model.swap.ApiSwapRes;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


public class SwapApiClientImpl extends HttpClient implements SwapApiClient, Encryptable {

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
    public SwapApiClientImpl(String appId, String appSecret, String token) {
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
    public SwapApiClientImpl(String urlPrefix, String appId, String appSecret, String token) {
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
    public SwapApiClientImpl(String urlPrefix, String appId, String appSecret, String token, long connectTimeout, long writeTimeout, long readTimeout) {
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

    private <T> ApiResponse<T> call(String url, TreeMap<String, Object> paramsMap) {
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<T>>() {
        });
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
    public ApiResponse<Object> addLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired,
                                            BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine) {
        checkStringParam(thirdId, "thirdId");
        checkStringParam(methodName, "methodName");
        checkStringParam(tokenA, "tokenA");
        checkStringParam(addressTo, "addressTo");

        checkAmountParam(amountADesired, "amountADesired");
        checkAmountParam(amountBDesired, "amountBDesired");
        checkAmountParam(amountAMin, "amountAMin");
        checkAmountParam(amountBMin, "amountBMin");
        if (deadLine == null) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [deadLine] is null or invalid");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("amountADesired", amountADesired);
        paramsMap.put("amountBDesired", amountBDesired);
        paramsMap.put("amountAMin", amountAMin);
        paramsMap.put("amountBMin", amountBMin);
        paramsMap.put("addressTo", addressTo);
        paramsMap.put("deadLine", deadLine);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_ADD_LIQUIDITY);
        return call(url, paramsMap);
    }

    @Override
    public ApiResponse<Object> removeLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal liquidity,
                                               BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine) {
        checkStringParam(thirdId, "thirdId");
        checkStringParam(methodName, "methodName");
        checkStringParam(tokenA, "tokenA");
        checkStringParam(addressTo, "addressTo");

        checkAmountParam(liquidity, "liquidity");
        checkAmountParam(amountAMin, "amountAMin");
        checkAmountParam(amountBMin, "amountBMin");
        if (deadLine == null) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [deadLine] is null or invalid");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("liquidity", liquidity);
        paramsMap.put("amountAMin", amountAMin);
        paramsMap.put("amountBMin", amountBMin);
        paramsMap.put("addressTo", addressTo);
        paramsMap.put("deadLine", deadLine);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_REMOVE_LIQUIDITY);
        return call(url, paramsMap);
    }

    @Override
    public ApiResponse<Object> swap(String thirdId, String tokenIn, String tokenOut, String addressIn, String minerInFee,  String methodName, String[] swapContractPath, BigDecimal amountIn, BigDecimal amountOut,  String addressOut, Long deadline) {

        checkSwapParams(thirdId, tokenIn, tokenOut, addressIn,minerInFee, methodName, swapContractPath, addressOut, deadline,amountIn,amountOut);

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("tokenIn", tokenIn);
        paramsMap.put("tokenOut", tokenOut);
        paramsMap.put("addressIn", addressIn);
        paramsMap.put("minerInFee", minerInFee);
        paramsMap.put("methodName", methodName);
        paramsMap.put("swapContractPath", swapContractPath);
        paramsMap.put("amountIn", amountIn);
        paramsMap.put("amountOut", amountOut);
        paramsMap.put("addressOut", addressOut);
        paramsMap.put("deadline", deadline);

        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_CREATE_TRANSACTION);
        return call(url, paramsMap);
    }

    private void checkSwapParams( String thirdId, String tokenIn, String tokenOut, String addressIn, String minerInFee,String methodName, String[] swapContractPath, String addressOut, Long deadline, BigDecimal amountIn, BigDecimal amountOut) {
        if (thirdId == null || thirdId.trim().equals("")) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [thirdId]  required");
        }
        if ((tokenIn == null || tokenIn.trim().equals("")) && (tokenOut == null || tokenOut.trim().equals(""))) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [tokenIn] OR [tokenOut] required");
        }
        if (addressIn == null || addressIn.trim().equals("")) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [addressIn]  required");
        }
        if (minerInFee == null || minerInFee.trim().equals("")) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [methodName]  required");
        }
        if (methodName == null || methodName.trim().equals("")) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [methodName]  required");
        }
        if (swapContractPath == null || swapContractPath.length <= 0) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [swapContractPath]  required");
        }
        for (String path : swapContractPath) {
            if (path == null || path.trim().equals("")) {
                throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "invalid parameter [swapContractPath]");
            }
        }
        if (addressOut == null || addressOut.trim().equals("")) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [addressOut]  required");
        }
        if (deadline == null) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, "parameter [deadline]  required");
        }
        if (amountIn == null || amountIn.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [" + amountIn + "] is null or invalid");
        }
        if (amountOut == null || amountOut.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [" + amountOut + "] is null or invalid");
        }

    }


    private void checkStringParam(String str, String amountTypeName) {
        if (StringUtils.isBlank(str)) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [" + amountTypeName + "] is null or invalid");
        }
    }

    private void checkAmountParam(BigDecimal amount, String amountTypeName) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ICCBankException.INPUT_ERROR, " parameter [" + amountTypeName + "] is null or invalid");
        }
    }

    @Override
    public ApiResponse<ApiAddLiquidityRes> queryAddLiquidity(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_ADD_LIQUIDITY);
        return call(url, paramsMap);
    }

	@Override
	public ApiResponse<ApiRemoveLiquidityRes> queryRemoveLiquidity(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_REMOVE_LIQUIDITY);
        return call(url, paramsMap);
	}

    @Override
    public ApiResponse<ApiSwapRes> querySwapStatus(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_STATUS);
        return call(url, paramsMap);
    }

    @Override
    public ApiResponse<ApiSwapDetailRes> querySwapDetail(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_DETAIL);
        return call(url, paramsMap);
    }
}
