package net.iccbank.openapi.sdk.utils;

import net.iccbank.openapi.sdk.ApiConstants;

import java.util.*;

public class SignUtils {

	/**
	 * 报文验签
	 */
	public static boolean verify(String json, String publicKey) {
		TreeMap<String, Object> map = JsonUtils.parseTreeMap(json);
		String sign = (String)map.get("sign");
		//String plainText = JsonUtils.toJsonString(map);
		String plainText = getText(paramsFilter(map), "&");
		return RsaUtils.verifyData(plainText, sign, publicKey);
	}

	/**
	 * 报文验签,使用公用私钥
	 */
	public static boolean verify(String json) {
		return verify(json, ApiConstants.PLATFORM_NOTIFY_PUBLIC_KEY);
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



}
