package net.iccbank.openapi.sdk.model.swap;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxFeeRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigInteger gasLimit;   //估算的gaslimit
    private BigDecimal gasPrice;   //gas价格，单位ETH
    private BigDecimal gasFee;     //交易手续费， 单位ETH

    /**
     * 服务费
     */
    private BigDecimal serviceFee;
}