package net.iccbank.openapi.sdk;

import net.iccbank.openapi.demo.bo.BizSwapAddLiquidityNotifyBO;
import net.iccbank.openapi.sdk.enums.ErrorCodeEnum;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import net.iccbank.openapi.sdk.utils.NotifyMessageTransferUtil;

import java.util.TreeMap;

public class SignTest {

    public static void main(String[] args) {
        String result = "{\"algorithm\":\"DESede\",\"encryptedData\":\"QSsewFvWH4HOnf/S4QtVPae1ZnYDuS3kqL3TAzrJGpEQPw4aXw9uKK/utxzjZev7rgnjj1yWN0fPBmKvjYZjlJn+My3xQldWrhCA0W7uTzxe8UZGSLcygu/SvObNY03o8iELjTLxbDhIATtLMOnOZ50mlXA8JqCx9iUU6+Uc/NZ5NtJC9R1UhJwXtWg0tj+oTG5bsy6DGIBnDOz4DyApu52XHtpO37NNU3WSF3Sg6thK2aT7b9/+NeVS3OVYES9iNcdrbnn/vb0VQuTlGXiJ96iIXcV/XYdHka1yahwf/86GaQ1Gn5YbUMXkZl6t/tt1lP7q54BN18Hz8mBYGm6/pTXfmHg0jRKOoqXg1+aRCD5XD9rdE4IIGsa4/Q/Co7x8z9rUb4FgvvTw7Bh7CbXi33h+Um+mImY5uL5J7JM0AOd+FO9F6iG8awOW7SfHT7JgtNG/6Salwp3DEPiPCNKWJ97W+TGuAJ61vhamTobbKWGJ8qv4H99D8dUAn5cUqaCjFrtHODEHtnv+p9F5anOjXiSTmcpL9sOCCySGpUmbpaX34Uufv0XqpSEuT60IE2UrrmfHaVi5oG5RIcTf+dZlJ/BQU2lzxgUR8BGsR+/uO2LmFvRO1gwW269pyDyxQotlYapKfjALX7AqtqzYkjfZ32zUzIMkJfTa\"}";
        String token = "ace2f15ac47c4520b4eb56b4c4f87918";
//        String resBody =  decrypt(token,result);
//
//        String errorResBody = "{\"addressOut\":\"0x333333333\",\"amountAActual\":\"1\",\"amountBActual\":\"10\",\"appId\":\"f678b3e698f649faa2177536356f83f7\",\"nonce\":\"0xtvmfjo5sc3xfux\",\"refundAmountA\":\"\",\"refundAmountB\":\"\",\"refundMinerFee\":\"\",\"serviceFee\":\"0\",\"sign\":\"fNSEtgTxHvbGNwzGIzs6/s+UEILCgNFQsI1S1R0EbF3ifbLfySjY/AtU/QYlU++b2GtVkImQJ7JQNnI9w7kOdYxNfBxJ+DZdDoFpLedL9s79wd7b66OuDWVTJoUxCssOsu5boEz8e4jDucGQiDMrRPneF+oTqa1iCkWXQtB8QPo=\",\"signType\":\"RSA\",\"status\":\"1\",\"thirdId\":\"d6rg6cxr\",\"timestamp\":1607497114033,\"tokenA\":\"DAI\",\"tokenB\":\"UNI\"}";
//        Boolean vR = SignUtils.verify(errorResBody,ApiConstants.PLATFORM_NOTIFY_PUBLIC_KEY);
//        System.out.println(vR);

        NotifyMessageTransferUtil<BizSwapAddLiquidityNotifyBO> notifyMessageUtils = new NotifyMessageTransferUtil<>();
        BizSwapAddLiquidityNotifyBO msg = notifyMessageUtils.transferNotifyMessage(token,result,BizSwapAddLiquidityNotifyBO.class);
        System.out.println(msg);
    }

    public static  String decrypt(String token,String encryptedResBody) {

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
}
