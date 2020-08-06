package net.iccbank.openapi.sdk.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
//import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AlgorithmUtils {
	
	public static final String ALGORTHM_AES = "AES";
	public static final String AES_TRANSFORMATION = "AES/ECB/pkcs5padding";
	
	public static final String ALGORTHM_3DES_SECRETKEY = "DESede";
	public static final String ALGORTHM_3DES_TRANSFORMATION = "DESede/ECB/PKCS5Padding";
	
	public static final String ALGORTHM_RSA = "RSA";
	public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    public static final String UTF_8 = "utf-8";

	
	public static String signWithRSA(String plain, String privateKey) throws Exception {
		//解密私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORTHM_RSA);
        //取私钥匙对象
        PrivateKey pk = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(pk);
        signature.update(plain.getBytes());

        return Base64.encodeBase64String(signature.sign());
	}

    public static boolean verifyWithRSA(String plain, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORTHM_RSA);
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(plain.getBytes());
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * @see encryptWith3DES
     */
    @Deprecated
	public static String encryptWithAES(String plainData, String token) throws Exception {
//		byte[] byteData = plainData.getBytes();
//        byte[] byteToken = token.getBytes();
//        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
//        SecretKeySpec keySpec = new SecretKeySpec(byteToken, ALGORTHM_AES);
//        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//        byte[] ret = cipher.doFinal(byteData);
//        return new String(Base64.encodeBase64(ret), UTF_8);
    	return decryptWith3DES(plainData, token);
	}

    /**
     * @see decryptWith3DES
     */
	@Deprecated
	public static String decryptWithAES(String encryptedData, String token) throws Exception {
//		byte[] byteData = Base64.decodeBase64(encryptedData.getBytes(UTF_8));
//        byte[] byteToken = token.getBytes();
//        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
//        SecretKeySpec keySpec = new SecretKeySpec(byteToken, ALGORTHM_AES);
//        cipher.init(Cipher.DECRYPT_MODE, keySpec);
//        byte[] ret = cipher.doFinal(byteData);
//		return new String(ret, UTF_8);
		return decryptWith3DES(encryptedData, token);
	}
	
	public static String encryptWith3DES(String plainData, String token) throws Exception {
		final DESedeKeySpec dks = new DESedeKeySpec(token.getBytes(UTF_8));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORTHM_3DES_SECRETKEY);
        final SecretKey securekey = keyFactory.generateSecret(dks);

        final Cipher cipher = Cipher.getInstance(ALGORTHM_3DES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        final byte[] b = cipher.doFinal(plainData.getBytes());
        return new String(Base64.encodeBase64(b), UTF_8);
	}

	/**
	 * 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
	 */ 
	public static String decryptWith3DES(String encryptedData, String token) throws Exception {
		// --通过base64,将字符串转成byte数组
        // final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = Base64.decodeBase64(encryptedData);
        
        // --解密的key
        final DESedeKeySpec dks = new DESedeKeySpec(token.getBytes(UTF_8));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORTHM_3DES_SECRETKEY);
        final SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final Cipher cipher = Cipher.getInstance(ALGORTHM_3DES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
	}

}
