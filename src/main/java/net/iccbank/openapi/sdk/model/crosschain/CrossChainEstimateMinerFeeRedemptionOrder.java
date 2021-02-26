package net.iccbank.openapi.sdk.model.crosschain;

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
public class CrossChainEstimateMinerFeeRedemptionOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 矿工费币种
     */
    private String minerFeeCurrency;

    /**
     * 矿工费
     */
    private BigDecimal minerFee;
}
