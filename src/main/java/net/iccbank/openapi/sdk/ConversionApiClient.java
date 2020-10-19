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
     * @param orderId 平台订单id
     * @return
     */
    ApiResponse<ConversionOrderDetail> getConversionOrderDetail(String orderId);


    /**
     * 获取兑换订单状态
     *
     * @param orderId 平台订单id
     * @return
     */
    ApiResponse<ConversionOrderStatus> getConversionOrderStatus(String orderId);


    /**
     * 获取兑换订单列表
     *
     * @return
     */
    ApiResponse<PageBO<ConversionOrderDetail>> getConversionOrderList(GetConversionListReq req);


    /**
     * 发起固定费率兑换
     *
     * @param source             兑换订单来源
     * @param orderId            第三方给的id 可以为空
     * @param rateId             固定汇率id
     * @param code               币对
     * @param payoutAddress      兑换地址
     * @param payoutLabelAddress 兑换地址标签
     * @param refundAddress      退款地址
     * @param refundLabelAddress 退款地址标签
     * @param amountExpectedFrom 预计支付金额
     * @param amountExpectedTo   预计收到金额
     * @return
     */
    ApiResponse<CreateFixRateConversion> createFixRateConversion(String source, String orderId, Long rateId, String code,
                                                                 String payoutAddress, String payoutLabelAddress, String refundAddress, String refundLabelAddress,
                                                                 BigDecimal amountExpectedFrom, BigDecimal amountExpectedTo);

    /**
     * 发起浮动利率兑换
     *
     * @param source             兑换订单来源
     * @param orderId            第三方给的id 可以为空
     * @param code               币对
     * @param payoutAddress      兑换地址
     * @param payoutLabelAddress 兑换地址标签
     * @param amountExpectedFrom 预计支付金额
     * @return
     */
    ApiResponse<CreateFloatRateConversion> createFloatRateConversion(String source, String orderId, String code,
                                                                     String payoutAddress, String payoutLabelAddress, BigDecimal amountExpectedFrom);
}
