package net.iccbank.openapi.sdk.model.swap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiSwapDetailRes implements Serializable {


    private String thirdId;

    private String tokenIn;

    private String tokenOut;

    private String address;

    private BigDecimal amountIn;

    private BigDecimal amountInActual;

    private BigDecimal amountOut;

    private BigDecimal amountOutActual;

    private BigDecimal minerInFee;

    private String swapTxid;

    private Integer status;

    private Date createdOn;

    private BigDecimal serviceFee;
}
