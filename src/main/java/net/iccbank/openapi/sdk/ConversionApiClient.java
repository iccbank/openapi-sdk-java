package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.*;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;
import net.iccbank.openapi.sdk.model.conversion.CreateFixRateConversion;
import net.iccbank.openapi.sdk.model.conversion.CreateFloatRateConversion;
import net.iccbank.openapi.sdk.model.page.PageBO;

import java.math.BigDecimal;
import java.util.List;

public interface ConversionApiClient {

    /**
     * 获取可兑换币对列表
     *
     * @return
     */
    ApiResponse<List<ConversionCurrency>> getConversionCurrencies();

    /**
     * 获取币种矿工费列表
     *
     * @return
     */
    ApiResponse<List<ConversionCurrencyMineFee>> getCurrencyMineFeeList();



    /**
     * 获取兑换利率
     *
     * @return
     */
    ApiResponse<ConversionRate> getConversionRate(ConversionRateReq param);


    /**
     * 获取兑换订单详情
     *
     * @return
     */
    ApiResponse<ConversionOrderDetail> getConversionOrderDetail(String orderId);


    /**
     * 获取兑换订单状态
     *
     * @return
     */
    ApiResponse<ConversionOrderStatus> getConversionOrderStatus(String orderId);


    /**
     * 获取兑换订单列表
     *
     * @return
     */
    ApiResponse<PageBO<ConversionOrderDetail>> getConversionOrderList(GetConversionListReq req);


    ApiResponse<CreateFixRateConversion> createFixRateConversion(String source, String orderId, Long rateId, String code,
                                                                 String payoutAddress, String payoutLabelAddress, String refundAddress, String refundLabelAddress,
                                                                 BigDecimal amountExpectedFrom, BigDecimal amountExpectedTo);

    ApiResponse<CreateFloatRateConversion> createFloatRateConversion(String source, String orderId, String code,
                                                                     String payoutAddress, String payoutLabelAddress, BigDecimal amountExpectedFrom);
}
