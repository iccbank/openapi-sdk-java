package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.model.conversion.CreateFixRateConversion;
import net.iccbank.openapi.sdk.model.conversion.CreateFloatRateConversion;

import java.math.BigDecimal;
import java.util.List;

public interface ConversionApiClient {

    /**
     * 获取可兑换币对列表
     * @return
     */
    ApiResponse<List<ConversionCurrency>> getConversionCurrencies();

    /**
     * 获取币种矿工费列表
     * @return
     */
    ApiResponse<List<ConversionCurrencyMineFee>> getCurrencyMineFeeList();

    ApiResponse<CreateFixRateConversion> createFixRateConversion(String source, String orderId, Long rateId, String code,
                                                                 String payoutAddress, String payoutLabelAddress, String refundAddress, String refundLabelAddress,
                                                                 BigDecimal amountExpectedFrom, BigDecimal amountExpectedTo);

    ApiResponse<CreateFloatRateConversion> createFloatRateConversion(String source, String orderId, String code,
                                                                     String payoutAddress, String payoutLabelAddress, BigDecimal amountExpectedFrom);
}
