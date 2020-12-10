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
	public void testAddLiquidity() {
		String methodName = "addLiquidity";
		String tokenA = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenB = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
		BigDecimal amountADesired = BigDecimal.valueOf(100);
		BigDecimal amountBDesired = BigDecimal.valueOf(600);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0xd98da0BA4247E616E8d402AAC4c58dcEc1D1d0Be";
		Long deadline = 1606981505000L;
		BigDecimal gasPrice = new BigDecimal("0.000000004547515013");
		BigDecimal minerInFee = new BigDecimal("0.000000004547515013");
		ApiResponse res = client.addLiquidity("xx111", methodName, tokenA, tokenB, amountADesired, amountBDesired, amountAMin, amountBMin, addressOut, deadline, gasPrice, BigDecimal.ZERO, minerInFee);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void testRemoveLiquidity() {
		String methodName = "removeLiquidityWithPermit";
		String tokenA = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenB = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
		BigDecimal amountADesired = BigDecimal.valueOf(100);
		BigDecimal amountBDesired = BigDecimal.valueOf(600);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0xd98da0BA4247E616E8d402AAC4c58dcEc1D1d0Be";
		Long deadline = 1606981505000L;
		BigDecimal gasPrice = new BigDecimal("0.000000004547515013");
		BigDecimal minerInFee = new BigDecimal("0.000000004547515013");
		//ApiResponse res = client.addLiquidity("xx111", methodName, tokenA, tokenB, amountADesired, amountBDesired, amountAMin, amountBMin, addressOut, deadline, gasPrice, BigDecimal.ZERO, minerInFee);
		ApiResponse res = client.removeLiquidity("ww111", methodName, tokenA, tokenB, BigDecimal.TEN, amountAMin, amountBMin, addressOut, true, deadline, gasPrice, BigDecimal.ZERO, minerInFee);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void testSwap() {
		String methodName = "swapExactTokensForTokens";
		String tokenIn = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenOut	 = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
		String address	 = "0xd98da0BA4247E616E8d402AAC4c58dcEc1D1d0Be";
		String swapContractPath	 = "0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197,0x5f3ca1D59F54A853eF639b2ec9EEAfE06DE00197";
		BigDecimal amountIn = BigDecimal.valueOf(90);
		BigDecimal amountOut = BigDecimal.valueOf(90);
		Long deadline = 1606981505000L;
		BigDecimal gasPrice = new BigDecimal("0.000000004547515013");
		BigDecimal minerInFee = new BigDecimal("0.000000004547515013");
		ApiResponse res = client.swap("px555", tokenIn, tokenOut, address, minerInFee, methodName, swapContractPath, amountIn, amountOut, deadline, gasPrice, BigDecimal.ZERO);
		System.out.println(JsonUtils.toJsonString(res));
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
		String tokenA = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenB = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
		BigDecimal amountADesired = BigDecimal.valueOf(100);
		BigDecimal amountBDesired = BigDecimal.valueOf(600);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0xd98da0BA4247E616E8d402AAC4c58dcEc1D1d0Be";
		Long deadline = 1606981505000L;
		ApiResponse<TxFeeRes> response = client.queryTxFeeAddLiquidity(methodName,tokenA,tokenB,amountADesired,amountBDesired,amountAMin,amountBMin,addressOut,deadline);
		System.out.println(JsonUtils.toJsonString(response));
		System.out.println(response.getData().getGasFee());
//		{"code":200,"data":{"gasFee":0.0075,"gasLimit":7500000,"gasPrice":3.0E-9},"msg":"HTTP_OK","subCode":"0","subMsg":"success","success":true}
	}


	@Test
	public void queryTxFeeRemoveLiquidity(){
		String methodName = "removeLiquidityWithPermit";
		String tokenA = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenB = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
		BigDecimal liquidity = BigDecimal.valueOf(1);
		BigDecimal amountAMin = BigDecimal.valueOf(90);
		BigDecimal amountBMin = BigDecimal.valueOf(592);
		String addressOut = "0xd98da0BA4247E616E8d402AAC4c58dcEc1D1d0Be";
		Boolean approveMax = true;
		Long deadline = 1606981505000L;
		ApiResponse<TxFeeRes> response =	client.queryTxFeeRemoveLiquidity(methodName,tokenA,tokenB,liquidity,amountAMin,amountBMin,addressOut,approveMax,deadline);
		System.out.println(JsonUtils.toJsonString(response));
		//{"code":200,"data":{"gasFee":0.0025,"gasLimit":2500000,"gasPrice":1.0E-9},"msg":"HTTP_OK","subCode":"0","subMsg":"success","success":true}
	}


	@Test
	public void queryTxFeeSwapTransaction(){
		String methodName = "swapExactTokensForTokens";
		String tokenIn = "0x6c31a6ca2ce248ee4456d2972f5744bf4a2a2b4b";
		String tokenOut	 = "0x93efb82cf2a9b30608c1ce2cd28de70a198724a9";
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
