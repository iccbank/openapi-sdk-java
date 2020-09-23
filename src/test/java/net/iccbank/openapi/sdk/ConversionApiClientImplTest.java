package net.iccbank.openapi.sdk;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ConversionApiClientImplTest {

    private ConversionApiClient client;

    @Before
    public void before() {
        client = new ConversionApiClientImpl(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
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
}