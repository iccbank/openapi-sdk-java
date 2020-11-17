package net.iccbank.openapi.sdk;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.enums.SearchTypeEnum;
import net.iccbank.openapi.sdk.model.*;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ApiClientTest {

    private ApiClient client;

    @Before
    public void before() {
        client = new DefaultApiClient(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
    }

    @Test
    public void testAddressCheck() {
        ApiResponse<Object> res = client.addressCheck("ETH", "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6");

        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testTestAddressCheck() {
        ApiResponse<Object> res = client.addressCheck("ETH", "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6");

        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testAgentPayAddAddress() {
        ApiResponse<Object> res = client.agentPayAddAddress("ETH", "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6", null);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testCreateAgencyRechargeAddress() {
        ApiResponse<List<ApiAddress>> res = client.createAgencyRechargeAddress("FIL", 1, "Batch"+System.currentTimeMillis());
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testAgencyWithdraw() {
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
    public void testAgencyWithdrawWithMinerFee() {
        String mchOrderNo = "kevin"+System.currentTimeMillis();
        String subject = "test";
        String currencyCode = "ETH";
        String address = "0x31bf7b9f55f155f4ae512e30ac65c590dfad0ca6";//代收
        BigDecimal amount = new BigDecimal("0.0024");
        BigDecimal minerFee = new BigDecimal("0.0024");
        BigDecimal fee = new BigDecimal("0.0024");
        String labelAddress = null;
        String notifyUrl = null;
        ApiResponse<ApiAgencyWithdrawData> res = client.agencyWithdrawWithMinerFee(mchOrderNo, subject, currencyCode, address, labelAddress, amount, minerFee, fee, notifyUrl);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testQueryAgencyWithdrawOrder() {
        ApiResponse<ApiAgencyWithdrawQueryData> res = client.queryAgencyWithdrawOrder("kevin1598512909624");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetBalances() {
        ApiResponse<ApiMchBalance> res = client.getBalances();
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testCurrencySearch() {
        ApiResponse<List<ApiCurrencyData>> res = client.currencySearch(SearchTypeEnum.CURRENCY.getType(), "BTC");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testCurrencyAddToken() {
        ApiResponse<ApiCurrencyData> res = client.currencyAddToken("ethereum", "0xb0c2e9a1e5d379a2e22a570e5b9773da5c30bd27");
        System.out.println(JsonUtils.toJsonString(res));

        ApiResponse<ApiCurrencyData> res2 = client.currencyAddToken("tron", "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        System.out.println(JsonUtils.toJsonString(res2));
    }

    @Test
    public void testGetCurrencyFee() {
        ApiResponse<ApiCurrencyFeeData> res = client.getCurrencyFee("BTC");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testQueryCurrencyChainList() {
        ApiResponse<List<ApiCurrencyData>> res = client.queryCurrencyChainList();
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetCurrencyByCode() {
        ApiResponse<ApiCurrencyData> res = client.getCurrencyByCode("BTC");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetBalancesForCurrencyCode() {
        ApiResponse<ApiMchBalance> res = client.getBalancesForCurrencyCode("BTC");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetBalancesForCurrencyCodeAndAccountType() {
        ApiResponse<ApiMchBalance.BalanceNode> res = client.getBalancesForCurrencyCodeAndAccountType("BTC", 1L);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetTotalBalancesForCurrencyCode() {
        ApiResponse<ApiMchBalance.BalanceNode> res = client.getTotalBalancesForCurrencyCode("BTC");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testGetMinerPower() {
        ApiResponse<ApiMinerPower> res = client.getMinerPower("FIL", null);
        System.out.println(JsonUtils.toJsonString(res));
    }
}