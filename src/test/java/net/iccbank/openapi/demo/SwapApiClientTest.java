package net.iccbank.openapi.demo;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.ApiClient;
import net.iccbank.openapi.sdk.DefaultApiClient;
import net.iccbank.openapi.sdk.SwapApiClient;
import net.iccbank.openapi.sdk.SwapApiClientImpl;
import net.iccbank.openapi.sdk.enums.SearchTypeEnum;
import net.iccbank.openapi.sdk.model.*;
import net.iccbank.openapi.sdk.model.swap.ApiSwapDetailRes;
import net.iccbank.openapi.sdk.model.swap.ApiSwapRes;
import net.iccbank.openapi.sdk.model.swap.TxFeeRes;
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
		String thirdId = "1088168606711508992";
		ApiResponse<ApiSwapRes> response =	client.querySwapStatus(thirdId);
		System.out.println(JsonUtils.toJsonString(response));
	}

	@Test
	public void testQuerySwapDetail(){
		String thirdId = "1088168606711508992";
		ApiResponse<ApiSwapDetailRes> response =	client.querySwapDetail(thirdId);
		System.out.println(JsonUtils.toJsonString(response));
	}


	@Test
	public void queryTxFeeAddLiquidity(){
		String methodName = "addLiquidity";
		String tokenA = "0x35401C8CA3e994d627d1610777877e5AbeE932dC";
		String tokenB = "0x6f259637dcd74c767781e37bc6133cd6a68aa161";
		BigDecimal amountADesired = BigDecimal.valueOf(100);
		BigDecimal amountBDesired = BigDecimal.valueOf(600);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197";
		Long deadline = 1606981505000L;
		ApiResponse<TxFeeRes> response = client.queryTxFeeAddLiquidity(methodName,tokenA,tokenB,amountADesired,amountBDesired,amountAMin,amountBMin,addressOut,deadline);
		System.out.println(JsonUtils.toJsonString(response));
		System.out.println(response.getData().getGasFee());
//		{"code":200,"data":{"gasFee":0.0075,"gasLimit":7500000,"gasPrice":3.0E-9},"msg":"HTTP_OK","subCode":"0","subMsg":"success","success":true}
	}


	@Test
	public void queryTxFeeRemoveLiquidity(){
		String methodName = "removeLiquidityWithPermit";
		String tokenA = "0x35401C8CA3e994d627d1610777877e5AbeE932dC";
		String tokenB = "0x6f259637dcd74c767781e37bc6133cd6a68aa161";
		BigDecimal liquidity = BigDecimal.valueOf(1);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197";
		Boolean approveMax = true;
		Long deadline = 1606981505000L;
		ApiResponse<TxFeeRes> response =	client.queryTxFeeRemoveLiquidity(methodName,tokenA,tokenB,liquidity,amountAMin,amountBMin,addressOut,approveMax,deadline);
		System.out.println(JsonUtils.toJsonString(response));
		//{"code":200,"data":{"gasFee":0.0025,"gasLimit":2500000,"gasPrice":1.0E-9},"msg":"HTTP_OK","subCode":"0","subMsg":"success","success":true}
	}


	@Test
	public void queryTxFeeSwapTransaction(){
		String methodName = "swapExactTokensForTokens";
		String tokenIn = "0x35401C8CA3e994d627d1610777877e5AbeE932dC";
		String tokenOut	 = "0x6f259637dcd74c767781e37bc6133cd6a68aa161";
		String address	 = "0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197";
		String swapContractPath	 = "0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197,0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197";
		BigDecimal amountIn = BigDecimal.valueOf(90);
		BigDecimal amountOut = BigDecimal.valueOf(90);
		Long deadline = 1606981505000L;
		ApiResponse<TxFeeRes> response =	client.queryTxFeeSwapTransaction(methodName,tokenIn,tokenOut,address,swapContractPath,amountIn,amountOut,deadline);
		System.out.println(JsonUtils.toJsonString(response));
		//{"code":200,"data":{"gasFee":0.0075,"gasLimit":7500000,"gasPrice":3.0E-9},"msg":"HTTP_OK","subCode":"0","subMsg":"success","success":true}
	}


}
