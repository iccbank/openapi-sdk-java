package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.*;

import java.util.List;

public interface ConversionApiClient {

    /**
     * 获取可兑换币对列表
     *
     * @return
     */
    ApiResponse<List<ConversionCurrency>> getCurrencies();

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

}
