package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;

import java.math.BigDecimal;

public interface SwapApiClient {


    /**
     * 添加流动性
     *
     * @param tokenA         A币种code
     * @param tokenB         B币种code（包含eth时 为 null）
     * @param amountADesired 期望添加A数量
     * @param amountBDesired 期望添加B数量
     * @param amountAMin     A最小数量
     * @param amountBMin     B最小数量
     * @param addressTo      用户token地址
     * @param deadLine       处理截止时间戳（秒）
     */
    ApiResponse<Object> addLiquidity(String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired,
                                     BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine);

    /**
     * 取出流动性
     *
     * @param tokenA     A币种code
     * @param tokenB     B币种code（包含eth时 为 null）
     * @param liquidity  流动性代币数量
     * @param amountAMin A最小数量
     * @param amountBMin B最小数量
     * @param addressTo  用户token地址
     * @param deadLine   处理截止时间戳（秒）
     */
    ApiResponse<Object> removeLiquidity(String tokenA, String tokenB, BigDecimal liquidity,
                                        BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine);
}
