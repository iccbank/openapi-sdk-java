package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.swap.ApiAddLiquidityRes;
import net.iccbank.openapi.sdk.model.swap.ApiRemoveLiquidityRes;
import net.iccbank.openapi.sdk.model.swap.ApiSwapRes;

import java.math.BigDecimal;

public interface SwapApiClient {


    /**
     * 添加流动性
     *
     * @param thirdId        业务id
     * @param tokenA         A币种code
     * @param tokenB         B币种code（包含eth时 为 null）
     * @param amountADesired 期望添加A数量
     * @param amountBDesired 期望添加B数量
     * @param amountAMin     A最小数量
     * @param amountBMin     B最小数量
     * @param addressTo      用户token地址
     * @param deadLine       处理截止时间戳（秒）
     */
    ApiResponse<Object> addLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired,
                                     BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine);

    /**
     * 取出流动性
     *
     * @param thirdId    业务id
     * @param tokenA     A币种code
     * @param tokenB     B币种code（包含eth时 为 null）
     * @param liquidity  流动性代币数量
     * @param amountAMin A最小数量
     * @param amountBMin B最小数量
     * @param addressTo  用户token地址
     * @param deadLine   处理截止时间戳（秒）
     */
    ApiResponse<Object> removeLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal liquidity,
                                        BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadLine);

    /**
     * @param thirdId
     * @param tokenIn          扣除资金代币Token ETH为空
     * @param tokenOut         兑换资金代币Token ETH为空
     * @param addressIn        支付地址
     * @param methodName       合约方法名
     * @param swapContractPath 币对合约地址
     * @param amountIn         扣除资金代币数量
     * @param amountInMax      最大可扣除资金代币数量
     * @param amountOut        预期兑换资金代币数量
     * @param amountOutMin     最少兑换资金代币数量
     * @param amountOutMax     最多兑换资金代币数量
     * @param addressOut        转出地址
     * @param deadline         截止时间(时间戳)
     * @return
     */
    ApiResponse<Object> swap(String thirdId,String tokenIn, String tokenOut, String addressIn, String methodName,
                             String[] swapContractPath, BigDecimal amountIn, BigDecimal amountInMax,
                             BigDecimal amountOut, BigDecimal amountOutMin, BigDecimal amountOutMax, String addressOut, Long deadline);


    /**
     * 查询添加流动性结果
     *
     * @param thirdId 业务id
     * @return
     */
    ApiResponse<ApiAddLiquidityRes> queryAddLiquidity(String thirdId);

    /**
     * 查询添加流动性结果
     *
     * @param thirdId 业务id
     * @return
     */
    ApiResponse<ApiRemoveLiquidityRes> queryRemoveLiquidity(String thirdId);

    /**
     * 查询兑换结果
     *
     * @param thirdId 业务id
     * @return
     */
    ApiResponse<ApiSwapRes> querySwapStatus(String thirdId);

}
