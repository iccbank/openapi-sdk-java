package net.iccbank.openapi.sdk.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Jackson工具类
 */
public class JsonUtils {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		// 序列化KEY排序
		objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
	}
	
	public static String toJsonString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json序列化异常", e);
		}
	}
	
	
	public static <T> T parseObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Json反序列化异常", e);
		}
	}
	
	public static <T> T parseObject(String json, TypeReference<T> valueTypeRef) {
		try {
			return objectMapper.readValue(json, valueTypeRef);
		} catch (IOException e) {
			throw new RuntimeException("Json反序列化异常", e);
		}
	}
	
	public static TreeMap<String, Object> parseTreeMap(String json) {
		return parseObject(json, new TypeReference<TreeMap<String, Object>>(){});
	}
	
}
