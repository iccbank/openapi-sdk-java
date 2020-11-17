package net.iccbank.openapi.demo;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.ApiClient;
import net.iccbank.openapi.sdk.DefaultApiClient;
import net.iccbank.openapi.sdk.enums.SearchTypeEnum;
import net.iccbank.openapi.sdk.model.*;
import net.iccbank.openapi.sdk.utils.AlgorithmUtils;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ApiClientTest {

	private ApiClient client;

	@Before
	public void before(){
		client = new DefaultApiClient(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
	}

	@Test
	public void addressCheck(){
		ApiResponse<Object> res = client.addressCheck("ETH", "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6");

		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void agentPayAddAddress(){
		ApiResponse<Object> res = client.agentPayAddAddress("ETH", "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6", null);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void createAgencyRechargeAddress(){
		ApiResponse<List<ApiAddress>> res = client.createAgencyRechargeAddress("FIL", 1, "Batch"+System.currentTimeMillis());
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void agencyWithdraw(){
		String mchOrderNo = "kevin"+System.currentTimeMillis();
		String subject = "test";
		String currencyCode = "ETH";
		String address = "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6";//代收
		BigDecimal amount = new BigDecimal("0.0024");
		String labelAddress = null;
		String notifyUrl = null;

		ApiResponse<ApiAgencyWithdrawData> res = client.agencyWithdraw(mchOrderNo, subject, currencyCode, address, labelAddress, amount, notifyUrl);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void queryAgencyWithdrawOrder(){
		ApiResponse<ApiAgencyWithdrawQueryData> res = client.queryAgencyWithdrawOrder("kevin1598512909624");
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void getBalances(){
		ApiResponse<ApiMchBalance> res =  client.getBalances();
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void getBalancesForCurrencyCode(){
		ApiResponse<ApiMchBalance> res =  client.getBalancesForCurrencyCode("ETC");
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void getBalancesForCurrencyCodeAndAccountType(){
		ApiResponse<ApiMchBalance.BalanceNode> res =  client.getBalancesForCurrencyCodeAndAccountType("ETH", 1L);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void currencySearch(){
		ApiResponse<List<ApiCurrencyData>> res = client.currencySearch(SearchTypeEnum.CURRENCY.getType(), "kevin");
		System.out.println(JsonUtils.toJsonString(res));
	}
	
	@Test
	public void addToken() {
		ApiResponse<ApiCurrencyData> res = client.currencyAddToken("ethereum", "0xb0c2e9a1e5d379a2e22a570e5b9773da5c30bd27");
		System.out.println(JsonUtils.toJsonString(res));
		
		ApiResponse<ApiCurrencyData> res2 = client.currencyAddToken("tron", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
		System.out.println(JsonUtils.toJsonString(res2));
	}

	@Test
	public void getTotalBalances(){
		ApiResponse<ApiMchBalance.BalanceNode> res =  client.getTotalBalancesForCurrencyCode("DASH");
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void getMinerPower(){
		ApiResponse<ApiMinerPower> res =  client.getMinerPower("FIL",null);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void fetchUnspentUTXO(){
//		ApiResponse<List<ApiUnspentUtxo>> res = client.fetchUnspentUTXO("BTC", "bc1qj8cdvxsq0raaqzfvm6w2dfdwj6cm9najk60e56", new BigDecimal("1"));
		ApiResponse<List<ApiUnspentUtxo>> res = client.fetchUnspentUTXO("DASH", "Xv1k3LLz6mLHg3v6hjZ6Qf34G4RqgDTFWM", new BigDecimal("1"));
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void reporting(){
		ApiProxyScanningAddress address = new ApiProxyScanningAddress();
		List<String> addressList = new ArrayList<>();

//		address.setLinkType("ionchain");
//		addressList.add("0xCaB80B629fbb6Af23FCe5da89c6794Cd4ff31e29");

//		address.setLinkType("bitcoin");
//		address.setSource(1);
//		addressList.add("3HAfhSHPfaCN4BYpjQL7T52Qx13bAbpjn9");
		address.setLinkType("dash");
		address.setSource(2);
		addressList.add("Xv1k3LLz6mLHg3v6hjZ6Qf34G4RqgDTFWM");
//		addressList.add("0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6");
//		addressList.add("147SwRQdpCfj5p8PnfsXV2SsVVpVcz3aPq");
		address.setAddressLists(addressList);

		ApiResponse res = client.reporting(address);
		System.out.println(JsonUtils.toJsonString(res));
	}

	@Test
	public void getUnspentBalanceByAddress(){
//		ApiResponse<ApiUnspentBalance> res = client.getUnspentBalanceByAddress("BTC", "bc1qj8cdvxsq0raaqzfvm6w2dfdwj6cm9najk60e56");
		ApiResponse<ApiUnspentBalance> res = client.getUnspentBalanceByAddress("DASH", "Xv1k3LLz6mLHg3v6hjZ6Qf34G4RqgDTFWM");
		System.out.println(JsonUtils.toJsonString(res));
	}

}
