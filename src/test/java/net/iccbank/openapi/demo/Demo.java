package net.iccbank.openapi.demo;

import net.iccbank.openapi.sdk.ApiClient;
import net.iccbank.openapi.sdk.DefaultApiClient;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.utils.JsonUtils;

public class Demo {
	
	public static void main(String[] args) {
		
		String appId = "xxx";
		String appSecret = "xxx";
		String token = "xxx";
		
		ApiClient client = new DefaultApiClient(appId, appSecret, token);
		
		ApiResponse<Object> res = client.addressCheck("LTC", "LYFvjx6EBdQr3uTdwUkBvRczwhKdiwGpeq");
		
		System.out.println(JsonUtils.toJsonString(res));
		
	}
	
}
