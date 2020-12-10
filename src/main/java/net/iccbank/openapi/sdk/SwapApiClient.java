package net.iccbank.openapi.sdk;

import net.iccbank.openapi.sdk.model.ApiResponse;
import net.iccbank.openapi.sdk.model.swap.*;

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
     * @param deadline       处理截止时间戳（秒）
     * @param gasPrice       gas费用
     * @param serviceFee     服务费
     * @param minerInFee     矿工费
     */
    ApiResponse addLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal amountADesired, BigDecimal amountBDesired,
                                     BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Long deadline, BigDecimal gasPrice, BigDecimal serviceFee, BigDecimal minerInFee);

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
     * @param deadline   处理截止时间戳（秒）
     * @param gasPrice   gas费用
     * @param serviceFee 服务费
     * @param minerInFee 矿工费
     */
    ApiResponse removeLiquidity(String thirdId, String methodName, String tokenA, String tokenB, BigDecimal liquidity,
                                        BigDecimal amountAMin, BigDecimal amountBMin, String addressTo, Boolean approveMax, Long deadline, BigDecimal gasPrice, BigDecimal serviceFee, BigDecimal minerInFee);

    /**
     * @param thirdId
     * @param tokenIn          扣除资金代币Token ETH为空
     * @param tokenOut         兑换资金代币Token ETH为空
     * @param address          用户token地址
     * @param methodName       合约方法名
     * @param swapContractPath 币对合约地址
     * @param amountIn         扣除资金代币数量
     * @param amountOut        预期兑换资金代币数量
     * @param deadline         截止时间(时间戳)
     * @param gasPrice         gas费用
     * @param serviceFee       服务费
     * @return
     */
    ApiResponse swap(String thirdId,String tokenIn, String tokenOut, String address, BigDecimal minerInFee,String methodName,
                             String[] swapContractPath, BigDecimal amountIn, BigDecimal amountOut,  Long deadline, BigDecimal gasPrice, BigDecimal serviceFee);


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

    /**
     * 查询兑换详情
     *
     * @param thirdId 业务id
     * @return
     */
    ApiResponse<ApiSwapDetailRes> querySwapDetail(String thirdId);


    /**
     * 查询矿工费-添加流动性
     * @param methodName 合约方法名
     * @param tokenA   tokenA不能为空
     * @param tokenB tokenB为eth时 可为null
     * @param amountADesired tokenA预期金额
     * @param amountBDesired tokenB预期金额
     * @param amountAMin tokenA最小金额
     * @param amountBMin tokenB最小金额
     * @param addressOut 用户地址
     * @param deadline 截止时间(时间戳)
     * @return
     */
    ApiResponse<TxFeeRes> queryTxFeeAddLiquidity(String methodName,String tokenA,String tokenB,BigDecimal amountADesired,BigDecimal amountBDesired,BigDecimal amountAMin,BigDecimal amountBMin,String addressOut,Long deadline);


    /**
     * 查询矿工费-移动流动性
     * @param methodName 合约方法名
     * @param tokenA  tokenA不能为空
     * @param tokenB tokenB为eth时 可为null
     * @param liquidity 流动性数量
     * @param amountAMin tokenA最小金额
     * @param amountBMin tokenB最小金额
     * @param addressOut 用户地址
     * @param approveMax 是否全部授权
     * @param deadline 截止时间(时间戳)
     * @return
     */
    ApiResponse<TxFeeRes> queryTxFeeRemoveLiquidity(String methodName,String tokenA,String tokenB,BigDecimal liquidity,BigDecimal amountAMin,BigDecimal amountBMin,String addressOut,Boolean approveMax,Long deadline);


    /**
     *  查询矿工费-发起兑换
     * @param tokenIn 扣除资金代币Token ETH为空
     * @param tokenOut 兑换资金代币Token ETH为空
     * @param address 充值地址
     * @param methodName 合约方法名
     * @param swapContractPath 币对合约地址集合，多个地址逗号分隔
     * @param amountIn 扣除资金代币数量
     * @param amountOut 预期兑换资金代币数量
     * @param deadline 截止时间(时间戳)
     * @return
     */
    ApiResponse<TxFeeRes> queryTxFeeSwapTransaction(String methodName,String tokenIn,String tokenOut,String address,String swapContractPath,BigDecimal amountIn,BigDecimal amountOut,Long deadline);

}
