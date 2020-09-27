package net.iccbank.openapi.sdk;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.model.conversion.CreateFixRateConversion;
import net.iccbank.openapi.sdk.model.conversion.CreateFloatRateConversion;
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
    }

    @Test
    public void testGetConversionCurrencies() {
        ApiResponse<List<ConversionCurrency>> currencies = client.getConversionCurrencies();
        System.out.println(JsonUtils.toJsonString(currencies));
    }

    @Test
    public void testGetCurrencyMineFeeList() {
        ApiResponse<List<ConversionCurrencyMineFee>> feeList = client.getCurrencyMineFeeList();
        System.out.println(JsonUtils.toJsonString(feeList));
    }

    @Test
    public void testCreateFixRateConversion() {
        ApiResponse<CreateFixRateConversion> fixRateConversion = client.createFixRateConversion("yy001", "", 1L, "BTC/ETH", "1K4NQvsKtpseHafRV8Han7ifVrx4Zeiq4v", "111",
                "0x222222222", "", null, BigDecimal.valueOf(1));
        System.out.println(JsonUtils.toJsonString(fixRateConversion));
    }

    @Test
    public void testCreateFloatRateConversion() {
        ApiResponse<CreateFloatRateConversion> floatRateConversion = client.createFloatRateConversion("yy001", "", "BTC/ETH",
                "0x111c8492818Dc11D14b50502f2c6e8a05BE8eD30", null, BigDecimal.ONE);
        System.out.println(JsonUtils.toJsonString(floatRateConversion));
    }
}