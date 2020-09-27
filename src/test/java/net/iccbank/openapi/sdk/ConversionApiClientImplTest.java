package net.iccbank.openapi.sdk;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.*;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class ConversionApiClientImplTest {

    private ConversionApiClient client;

    @Before
    public void before() {
        client = new ConversionApiClientImpl(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
//        client = new ConversionApiClientImpl("http://127.0.0.1:41008/", Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
    }

    @Test
    public void testGetCurrencies() {
        ApiResponse<List<ConversionCurrency>> currencies = client.getCurrencies();
        System.out.println(JsonUtils.toJsonString(currencies));
    }

    @Test
    public void testGetCurrencyMineFeeList() {
        ApiResponse<List<ConversionCurrencyMineFee>> feeList = client.getCurrencyMineFeeList();
        System.out.println(JsonUtils.toJsonString(feeList));
    }


    @Test
    public void getConversionOrderStatus() {
        ApiResponse<ConversionOrderStatus> result = client.getConversionOrderStatus("1076678620767154176");
        System.out.println(JsonUtils.toJsonString(result));
    }

    @Test
    public void getConversionOrderDetail() {
        ApiResponse<ConversionOrderDetail> result = client.getConversionOrderDetail("1076678620767154176");
        System.out.println(JsonUtils.toJsonString(result));
    }

    @Test
    public void getConversionRate() {
        ConversionRateReq req =new ConversionRateReq();
        req.setCode("BTC/ETH");
        req.setFixedRate(true);
        req.setAmountFrom(BigDecimal.ONE);
        ApiResponse<ConversionRate> result = client.getConversionRate(req);
        System.out.println(JsonUtils.toJsonString(result));
    }

}