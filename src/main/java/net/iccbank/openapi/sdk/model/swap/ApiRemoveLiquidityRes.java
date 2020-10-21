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
    private String tokenC;
    private String tokenCSymbol;
    private BigDecimal amountTokenA;
    private BigDecimal amountTokenB;
    private BigDecimal amountTokenC;
    private String addressTo;
    private BigDecimal minerFee;
}
