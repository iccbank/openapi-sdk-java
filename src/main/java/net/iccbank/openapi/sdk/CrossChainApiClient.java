package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.crosschain.*;

import java.math.BigDecimal;

public interface CrossChainApiClient {


    /**
     * 创建申购订单
     * @param thirdId           业务id
     * @param currencyCode      主链币种code
     * @param tokenCurrencyCode 代币币种code
     * @param address           申购地址
     * @param labelAddress      申购地址标签
     * @param amount            申购数量
     */
    ApiResponse<CreateCrossChainSubscriptionOrder> createSubscriptionOrder(String thirdId, String currencyCode, String tokenCurrencyCode, String address, String labelAddress, BigDecimal amount);


    /**
     * 创建赎回订单
     * @param thirdId           业务id
     * @param currencyCode      主链币种code
     * @param tokenCurrencyCode 代币币种code
     * @param address           赎回地址
     * @param labelAddress      赎回地址标签
     * @param amount            赎回数量
     */
    ApiResponse<CreateCrossChainRedemptionOrder> createRedemptionOrder(String thirdId, String currencyCode, String tokenCurrencyCode, String address, String labelAddress, BigDecimal amount);

    /**
     * 创建赎回订单-预估矿工费
     * @param currencyCode      主链币种code
     * @param tokenCurrencyCode 代币币种code
     * @param address           赎回地址
     * @param labelAddress      赎回地址标签
     * @param amount            赎回数量
     */
    ApiResponse<CrossChainEstimateMinerFeeRedemptionOrder> estimateMinerFeeRedemptionOrder(String currencyCode, String tokenCurrencyCode, String address, String labelAddress, BigDecimal amount);

    /**
     * 申购订单查询
     * @param orderId 平台订单id
     * @return
     */
    ApiResponse<QueryCrossChainSubscriptionOrder> querySubscriptionOrder(String orderId);

    /**
     * 赎回订单查询
     * @param orderId 平台订单id
     * @return
     */
    ApiResponse<QueryCrossChainRedemptionOrder> queryRedemptionOrder(String orderId);
}
