package net.iccbank.openapi.sdk.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 经典的数字签名算法RSA 数字签名
 */
public class RsaUtils {

    public static final String KEY_ALGORTHM = "RSA";//
    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
    public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }


    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得私钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 用私钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        //解密密钥
        byte[] keyBytes = Base64.decodeBase64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = Base64.decodeBase64(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        //对公钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       //加密数据
     * @param privateKey //私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        //解密私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        //解密公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Base64.decodeBase64(sign));
    }


    public static boolean verifyData(String requestXml, String sign, String publicKeyString) {
        try {
            return verify(requestXml.getBytes(), publicKeyString, sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String signData(String xml, String privateKeyString) {
        try {
            return sign(xml.getBytes(), privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String xml, String privateKeyString) {
        try {
//			return utils.Base64Util.ENCODER.encode(RSACoder.encryptByPrivateKey(xml.getBytes(), privateKeyString));
            return Base64.encodeBase64String(encryptByPrivateKey(xml.getBytes(), privateKeyString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String xml, String publicKeyString) {
        try {
//			return utils.Base64Util.ENCODER.encode(RSACoder.decryptByPublicKey(xml.getBytes(), publicKeyString));
            return Base64.encodeBase64String(decryptByPublicKey(xml.getBytes(), publicKeyString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES对称算法 加密 （密钥长度为16位）
     * @param inputStr 明文
     * @param password 密钥
     */
    public static String encryptByAES(String inputStr, String password)throws Exception
    {
        byte[] byteData = inputStr.getBytes();
        byte[] bytePassword = password.getBytes();
        return new String(Base64.encodeBase64(encryptByAES(byteData, bytePassword)),"utf-8");
    }
    private static byte[] encryptByAES(byte[] data, byte[] pwd) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/ECB/pkcs5padding");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }
    /**
     * 生成16位AES随机密钥
     */
    public static String getAESRandomKey()
    {
        Random random = new Random();
        long value = random.nextLong();
        return String.format("%016x", value);
    }
    //=================================================================================================

    /**
     * AES对称算法 解密
     * @param inputStr 密文
     * @param password 密钥
     */
    public static String decryptByAES(String inputStr, String password)throws Exception
    {
        byte[] byteData = Base64.decodeBase64(inputStr.getBytes("utf-8"));
        byte[] bytePassword = password.getBytes();
        return new String(decryptByAES(byteData, bytePassword), "utf-8");
    }

    private static byte[] decryptByAES(byte[] data, byte[] pwd) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(pwd, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] ret = cipher.doFinal(data);
        return ret;
    }


    public static Map<String, String> getKeyPair() {
        KeyPairGenerator keyPairGenerator = null;

        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
        }

        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
        Map<String, String> map = new HashMap();
        map.put("publicKey", publicKeyString);
        map.put("privateKey", privateKeyString);
        return map;
    }


    public static void main(String[] args) throws Exception {
//        15616285817
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfC7SocuEOgIEeTUhl+vwAAyiLvcHQRd97y6qMukoqbaYFfLep+vN/Q5WxgVOUlWpOejnb7kXnVgtagWOgekuvyj42+y8AvFKdkQcnmzHCwiGETIgIcDn1+BtEtUkzpxCIdyUHM00v3kRmld/7XiHwUlT0Ql3FzOUuxqaiQYWh4wIDAQAB";
        String data = "2913ebbab66e543d168f22512a82520364d16a14ea1184df7d21e1d64e4a4bad3855e405b2ffafa74b537c660d30207602c501e9098f5ce0961497b9c123bc8eee694cbca785190eb4b973f03ae1d879bedc13f3f938f995c362623b40dfe89888222b3dc5f6daf20dbaace993fe6a81723d12dfd202d23d84c1ad6f760ce946";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ8LtKhy4Q6AgR5NSGX6/AADKIu9wdBF33vLqoy6SiptpgV8t6n6839DlbGBU5SVak56OdvuRedWC1qBY6B6S6/KPjb7LwC8Up2RByebMcLCIYRMiAhwOfX4G0S1STOnEIh3JQczTS/eRGaV3/teIfBSVPRCXcXM5S7GpqJBhaHjAgMBAAECgYAcxHNC3LSUeO3h2zyzJXibT/bvf70kvN61d5s7pR5xGjAjYfGej0Ony0OyPaAuifAWjckVXL3MICYhkrHAfx5dPc2v8yWVZRmCbi/PILOfzAKDFAcloCHTuBps11NXUP65lf2TdEq6t283o0IvBd7CKLgY25AjyJIhWWxpB35dAQJBAOiYeRON1OIvjYkmIVOaod6rKJ6vvKSKyDeaZ/DIU0t/JkZIg5hQ1ZuEELKKl33brn7g1jve5hc185A5RSa8eaMCQQCvDKGCcL29kUGJGtSY/ckWNNr0aoJR2dsVaoL5+K14VT9czlDbgsm8d+EK/OIgKQ8ABbosIzvz7SCdqWQalTrBAkEAmXycUUID3CqDHKDz1wawkI6j5GuVcYM/cinLM0IObUB/kluhsy6Mdu5kUl1QcLY55PIbjTCe52wryN1x+4jePwJBAJUeiHPLQQe9NvvqHFOcVnIRYri2BwBdOxH6Y0s4+eW5kTmpFB57QwnHgbFPjf5hm8KkHl29QjRgu9kzVPCEUcECQDG5L067rbCw0jFCX02wb70ldWmDqzRR02WuQ/+T+cT0/zEgqc+g+TbvomX1lqY+cbGs1WVNiWTZemA/an+Tw1o=";

        byte[] bytes1 = decryptByPublicKey("{'a':'b'}".getBytes(), publicKey);
        System.out.println(toHexString(bytes1));

        byte[] bytes = encryptByPrivateKey(toByteArray(data), privateKey);
        System.out.println(new String(bytes));

    }

    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }


    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
    public static void genkey(){
        try {
            Map<String, Object> keyMap = RsaUtils.initKey();
            System.out.println("公钥:"+ RsaUtils.getPublicKey(keyMap));     //公钥
            System.out.println("私钥:"+ RsaUtils.getPrivateKey(keyMap));    //私钥

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
