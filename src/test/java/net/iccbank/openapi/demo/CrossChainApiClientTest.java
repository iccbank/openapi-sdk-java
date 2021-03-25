package net.iccbank.openapi.demo;

import net.iccbank.openapi.demo.constants.Constants;
import net.iccbank.openapi.sdk.CrossChainApiClient;
import net.iccbank.openapi.sdk.CrossChainApiClientImpl;
import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.crosschain.*;
import net.iccbank.openapi.sdk.utils.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class CrossChainApiClientTest {

    private CrossChainApiClient client;

    @Before
    public void before() {
        client = new CrossChainApiClientImpl(Constants.DOMAIN, Constants.APP_ID, Constants.APP_SECRET, Constants.TOKEN);
    }

    @Test
    public void testCreateSubscriptionOrder() {
        String thirdId = String.valueOf(System.currentTimeMillis());
        String currencyCode = "ETC";
        String tokenCurrencyCode = "VETC";
        String address = "0x6074d5517d20C580A86f0260dA7b9a91Ad38f7e6";
        String labelAddress = null;
        BigDecimal amount = BigDecimal.valueOf(0.05);
        ApiResponse<CreateCrossChainSubscriptionOrder> res = client.createSubscriptionOrder(thirdId, currencyCode, tokenCurrencyCode, address, labelAddress, amount);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testCreateRedemptionOrder() {
        String thirdId = String.valueOf(System.currentTimeMillis());
        String currencyCode = "ETC";
        String tokenCurrencyCode = "VETC";
        String address = "0x111c8492818Dc11D14b50502f2c6e8a05BE8eD30";
        String labelAddress = null;
        BigDecimal amount = BigDecimal.valueOf(0.05);
        ApiResponse<CreateCrossChainRedemptionOrder> res = client.createRedemptionOrder(thirdId, currencyCode, tokenCurrencyCode, address, labelAddress, amount);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testEstimateMinerFeeRedemptionOrder() {
        String currencyCode = "ETC";
        String tokenCurrencyCode = "VETC";
//        String address = "0x111c8492818Dc11D14b50502f2c6e8a05BE8eD30";
//        String labelAddress = null;
        BigDecimal amount = BigDecimal.valueOf(5);
        ApiResponse<CrossChainEstimateMinerFeeRedemptionOrder> res = client.estimateMinerFeeRedemptionOrder(currencyCode, tokenCurrencyCode, amount);
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testQuerySubscriptionOrder() {
        ApiResponse<QueryCrossChainSubscriptionOrder> res = client.querySubscriptionOrder("1131766093274468352");
        System.out.println(JsonUtils.toJsonString(res));
    }

    @Test
    public void testQueryRedemptionOrder() {
        ApiResponse<QueryCrossChainRedemptionOrder> res = client.queryRedemptionOrder("1131766094213992448");
        System.out.println(JsonUtils.toJsonString(res));
    }
}