package net.iccbank.openapi.demo.bo;

import lombok.*;

import java.io.Serializable;

/**
 * @Author hjt
 * @Description swap添加流动性 异步通知参数
 * @Date Created on 2020/10/22
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BizSwapRemoveLiquidityNotifyBO   implements Serializable {


    private Long userId;

    private String thirdId;

    private String tokenA;

    private String tokenB;

    private String liquidity;

    private String amountAActual;

    private String amountBActual;
    private String refundMinerFee;

    private String addressOut;

    private String txid;

    private Integer status;
    /**
     * 服务费
     */
    private String serviceFee;
}
