package net.iccbank.openapi.sdk.model.swap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRemoveLiquidityRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String thirdId;
    private String tokenA;
    private String tokenB;
    private BigDecimal liquidity;
    private BigDecimal amountAActual;
    private BigDecimal amountBActual;
    private BigDecimal refundMinerFee;
    private String addressOut;
    private String txid;
    private Integer status;
    private BigDecimal serviceFee;
}
