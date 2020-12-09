package net.iccbank.openapi.sdk.utils;

import net.iccbank.openapi.sdk.ApiConstants;
import net.iccbank.openapi.sdk.exception.ICCBankException;
import net.iccbank.openapi.sdk.model.ApiEncryptedBody;

import java.util.TreeMap;

/**
 * 通知消息解析工具
 * @param <T>
 */
public class NotifyMessageTransferUtil<T> {


    /**
     * @param token  商户申请的token
     * @param decryptMessage 未解密的原始通知消息
     * @return
     */
    public T transferNotifyMessage(String token,String decryptMessage,Class<T> clazz){
        return transferNotifyMessage(token,decryptMessage,ApiConstants.PLATFORM_NOTIFY_PUBLIC_KEY,clazz);
    }


    /**
     * @param token  商户申请的token
     * @param decryptMessage 未解密的原始通知消息
     * @param publickKey 验签公钥
     * @return
     */
    public  T transferNotifyMessage(String token,String decryptMessage,String publickKey,Class<T> clazz){
        //解密
        String resBody =  decrypt(token,decryptMessage);
        //验签
        Boolean verifySignResult = SignUtils.verify(resBody,publickKey);
        if(!verifySignResult){
            throw ICCBankException.buildException(ICCBankException.BIZ_ERROR, "[verify Signature] error: 验签失败");
        }
        //转换成实体类
        return JsonUtils.parseObject(resBody, clazz);
    }


    public static  String decrypt(String token,String encryptedResBody) {

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

}
