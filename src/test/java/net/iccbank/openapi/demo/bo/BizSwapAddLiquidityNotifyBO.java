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
public class BizSwapAddLiquidityNotifyBO  implements Serializable {


    private String thirdId;

    private String tokenA;

    private String tokenB;

    private String amountAActual;

    private String amountBActual;

    private String addressOut;

    private String txid;

    private String status;


    private String refundAmountA;
    private String refundAmountB;
    private String refundMinerFee;
    /**
     * 服务费
     */
    private String serviceFee;
}
