package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrency;
import net.iccbank.openapi.sdk.model.conversion.ConversionCurrencyMineFee;

import java.util.List;

public interface ConversionApiClient {

    /**
     * 获取可兑换币对列表
     * @return
     */
    ApiResponse<List<ConversionCurrency>> getCurrencies();

    /**
     * 获取币种矿工费列表
     * @return
     */
    ApiResponse<List<ConversionCurrencyMineFee>> getCurrencyMineFeeList();
}
