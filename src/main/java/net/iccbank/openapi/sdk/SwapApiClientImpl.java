package net.iccbank.openapi.sdk;

import com.fasterxml.jackson.core.type.TypeReference;

import net.iccbank.openapi.sdk.enums.ErrorCodeEnum;
import net.iccbank.openapi.sdk.enums.SwapMethodNameEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.swap.*;
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
    public ApiResponse addLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired,
                                            BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadline, BigDecimal gasPrice, BigDecimal serviceFee, BigDecimal minerInFee) {
        checkStringParam(thirdId, "thirdId");
        checkStringParam(methodName, "methodName");
        //checkStringParam(tokenA, "tokenA");
        checkStringParam(addressTo, "addressTo");

        checkAmountParam(amountADesired, "amountADesired");
        checkAmountParam(amountBDesired, "amountBDesired");
        checkAmountParam(amountAMin, "amountAMin");
        checkAmountParam(amountBMin, "amountBMin");
        checkAmountParam(gasPrice, "gasPrice");
        if (deadline == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [deadline] is null or invalid");
        }
        if (serviceFee == null || serviceFee.compareTo(BigDecimal.ZERO) < 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [serviceFee] is null or invalid");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("amountADesired", amountADesired.toPlainString());
        paramsMap.put("amountBDesired", amountBDesired.toPlainString());
        paramsMap.put("amountAMin", amountAMin.toPlainString());
        paramsMap.put("amountBMin", amountBMin.toPlainString());
        paramsMap.put("addressOut", addressTo);
        paramsMap.put("deadline", deadline.toString());
        paramsMap.put("gasPrice", gasPrice.toPlainString());
        paramsMap.put("minerInFee", minerInFee.toPlainString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_ADD_LIQUIDITY);
        return call(url, paramsMap);
    }

    @Override
    public ApiResponse removeLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal liquidity,
                                               BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Boolean approveMax, Long deadline, BigDecimal gasPrice, BigDecimal serviceFee, BigDecimal minerInFee) {
        checkStringParam(thirdId, "thirdId");
        checkStringParam(methodName, "methodName");
        //checkStringParam(tokenA, "tokenA");
        checkStringParam(addressTo, "addressTo");

        checkAmountParam(liquidity, "liquidity");
        checkAmountParam(amountAMin, "amountAMin");
        checkAmountParam(amountBMin, "amountBMin");
        checkAmountParam(gasPrice, "gasPrice");
        if (deadline == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [deadline] is null or invalid");
        }
        if (approveMax == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [approveMax] is null or invalid");
        }
        if (serviceFee == null || serviceFee.compareTo(BigDecimal.ZERO) < 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [serviceFee] is null or invalid");
        }
        if (StringUtils.isBlank(tokenA) && StringUtils.isBlank(tokenB)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [tokenA] OR [tokenB] required");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("liquidity", liquidity.toPlainString());
        paramsMap.put("amountAMin", amountAMin.toPlainString());
        paramsMap.put("amountBMin", amountBMin.toPlainString());
        paramsMap.put("addressOut", addressTo);
        paramsMap.put("approveMax", approveMax.toString());
        paramsMap.put("deadline", deadline.toString());
        paramsMap.put("gasPrice", gasPrice.toPlainString());
        paramsMap.put("minerInFee", minerInFee.toPlainString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_REMOVE_LIQUIDITY);
        return call(url, paramsMap);
    }

    @Override
    public ApiResponse swap(String thirdId, String tokenIn, String tokenOut, String address, BigDecimal minerInFee,  String methodName, String swapContractPath, BigDecimal amountIn, BigDecimal amountOut,  Long deadline, BigDecimal gasPrice, BigDecimal serviceFee) {

        checkSwapParams(thirdId, tokenIn, tokenOut, address,minerInFee, methodName, swapContractPath, deadline,amountIn,amountOut,gasPrice,serviceFee);

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        paramsMap.put("tokenIn", tokenIn);
        paramsMap.put("tokenOut", tokenOut);
        paramsMap.put("address", address);
        paramsMap.put("minerInFee", minerInFee.toPlainString());
        paramsMap.put("methodName", methodName);
        paramsMap.put("swapContractPath", swapContractPath);
        paramsMap.put("amountIn", amountIn.toPlainString());
        paramsMap.put("amountOut", amountOut.toPlainString());
        paramsMap.put("deadline", deadline.toString());
        paramsMap.put("gasPrice", gasPrice.toString());

        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_CREATE_TRANSACTION);
        return call(url, paramsMap);
    }

    private void checkSwapParams( String thirdId, String tokenIn, String tokenOut, String address, BigDecimal minerInFee,String methodName, String swapContractPath, Long deadline, BigDecimal amountIn, BigDecimal amountOut, BigDecimal gasPrice, BigDecimal serviceFee) {
        if (thirdId == null || thirdId.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [thirdId]  required");
        }
        if ((tokenIn == null || tokenIn.trim().equals("")) && (tokenOut == null || tokenOut.trim().equals(""))) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [tokenIn] OR [tokenOut] required");
        }
        if (address == null || address.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [addressIn]  required");
        }
        if (minerInFee == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [methodName]  required");
        }
        if (minerInFee.compareTo(BigDecimal.ZERO) < 0){
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [" + minerInFee + "] is invalid");
        }
        if (methodName == null || methodName.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [methodName]  required");
        }
        if (swapContractPath == null || swapContractPath.trim().equals("")) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [swapContractPath]  required");
        }
        if (deadline == null) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, "parameter [deadline]  required");
        }
        if (amountIn == null || amountIn.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [" + amountIn + "] is null or invalid");
        }
        if (amountOut == null || amountOut.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [" + amountOut + "] is null or invalid");
        }
        if (gasPrice == null || gasPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [gasPrice] is null or invalid");
        }
        if (serviceFee == null || serviceFee.compareTo(BigDecimal.ZERO) < 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [serviceFee] is null or invalid");
        }

    }


    private void checkStringParam(String str, String amountTypeName) {
        if (StringUtils.isBlank(str)) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [" + amountTypeName + "] is null or invalid");
        }
    }

    private void checkAmountParam(BigDecimal amount, String amountTypeName) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [" + amountTypeName + "] is null or invalid");
        }
    }

    @Override
    public ApiResponse<ApiAddLiquidityRes> queryAddLiquidity(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_ADD_LIQUIDITY);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiAddLiquidityRes>>(){});
    }

	@Override
	public ApiResponse<ApiRemoveLiquidityRes> queryRemoveLiquidity(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_REMOVE_LIQUIDITY);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiRemoveLiquidityRes>>(){});
	}

    @Override
    public ApiResponse<ApiSwapRes> querySwapStatus(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_STATUS);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiSwapRes>>(){});
    }

    @Override
    public ApiResponse<ApiSwapDetailRes> querySwapDetail(String thirdId) {
        checkStringParam(thirdId, "thirdId");

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("thirdId", thirdId);
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.SWAP_QUERY_DETAIL);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<ApiSwapDetailRes>>(){});
    }

    @Override
    public  ApiResponse<TxFeeRes> queryTxFeeAddLiquidity(String methodName, String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired, BigDecimal amountAMin, BigDecimal amountBMin, String addressOut, Long deadline) {
        checkStringParam("methodName",methodName);
        //checkStringParam("tokenA",tokenA);
        checkAmountParam(amountADesired,"amountADesired");
        checkAmountParam(amountBDesired,"amountBDesired");
        checkAmountParam(amountAMin,"amountAMin");
        checkAmountParam(amountBMin,"amountBMin");
        checkStringParam("addressOut",addressOut);
        if(deadline == null){
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [deadline] is null or invalid");
        }
        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("amountADesired", amountADesired.toPlainString());
        paramsMap.put("amountBDesired", amountBDesired.toPlainString());
        paramsMap.put("amountAMin", amountAMin.toPlainString());
        paramsMap.put("amountBMin", amountBMin.toPlainString());
        paramsMap.put("addressOut", addressOut);
        paramsMap.put("deadline", deadline.toString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.QUERY_TX_FEE_ADD_LIQUIDITY);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<TxFeeRes>>(){});
    }

    @Override
    public ApiResponse<TxFeeRes> queryTxFeeRemoveLiquidity(String methodName, String tokenA, String tokenB, BigDecimal liquidity, BigDecimal amountAMin, BigDecimal amountBMin, String addressOut, Boolean approveMax, Long deadline) {
        checkStringParam("methodName",methodName);
        //checkStringParam("tokenA",tokenA);
        checkAmountParam(liquidity,"liquidity");
        checkAmountParam(amountAMin,"amountAMin");
        checkAmountParam(amountBMin,"amountBMin");
        checkStringParam("addressOut",addressOut);
        if(approveMax == null){
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [approveMax] is null or invalid");
        }
        if(deadline == null){
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [deadline] is null or invalid");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("methodName", methodName);
        paramsMap.put("tokenA", tokenA);
        paramsMap.put("tokenB", tokenB);
        paramsMap.put("liquidity", liquidity.toPlainString());
        paramsMap.put("amountAMin", amountAMin.toPlainString());
        paramsMap.put("amountBMin", amountBMin.toPlainString());
        paramsMap.put("addressOut", addressOut);
        paramsMap.put("approveMax", approveMax.toString());
        paramsMap.put("deadline", deadline.toString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.QUERY_TX_FEE_REMOVE_LIQUIDITY);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<TxFeeRes>>(){});
    }

    @Override
    public ApiResponse<TxFeeRes> queryTxFeeSwapTransaction(String methodName,String tokenIn, String tokenOut, String address,  String swapContractPath, BigDecimal amountIn, BigDecimal amountOut, Long deadline) {
        checkStringParam("tokenIn",tokenIn);
        checkStringParam("tokenOut",tokenOut);
        checkStringParam("address",address);
        checkStringParam("methodName",methodName);
        checkStringParam("swapContractPath",swapContractPath);
        checkAmountParam(amountIn,"amountIn");
        checkAmountParam(amountOut,"amountOut");
        if(deadline == null){
            throw ICCBankException.buildException(ErrorCodeEnum.PARAMETER_ERROR, " parameter [deadline] is null or invalid");
        }

        TreeMap<String, Object> paramsMap = new TreeMap<String, Object>();
        paramsMap.put("tokenIn", tokenIn);
        paramsMap.put("tokenOut", tokenOut);
        paramsMap.put("address", address);
        paramsMap.put("methodName", methodName);
        paramsMap.put("swapContractPath", swapContractPath);
        paramsMap.put("amountIn", amountIn.toPlainString());
        paramsMap.put("amountOut", amountOut.toPlainString());
        paramsMap.put("deadline", deadline.toString());
        String url = ApiConstants.concatUrl(urlPrefix, ApiConstants.QUERY_TX_FEE_SWAP_TRANSACTION);
        String resBody = callToString(url, paramsMap);
        return JsonUtils.parseObject(resBody, new TypeReference<ApiResponse<TxFeeRes>>(){});
    }


}
