package net.iccbank.openapi.demo;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.ApiClient;
import net.iccbank.openapi.sdk.DefaultApiClient;
import net.iccbank.openapi.sdk.SwapApiClient;
import net.iccbank.openapi.sdk.SwapApiClientImpl;
import net.iccbank.openapi.sdk.enums.SearchTypeEnum;
import net.iccbank.openapi.sdk.model.*;
import net.iccbank.openapi.sdk.model.swap.ApiSwapRes;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class SwapApiClientTest {

	private SwapApiClient client;

	@Before
	public void before(){
		client = new SwapApiClientImpl(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
	}


	@Test
	public void testSwap(){

	}

	@Test
	public void testQuerySwapStatus(){
		String thirdId = "";
		ApiResponse<ApiSwapRes> response =	client.querySwapStatus(thirdId);
		System.out.println(JsonUtils.toJsonString(response));
	}


}
