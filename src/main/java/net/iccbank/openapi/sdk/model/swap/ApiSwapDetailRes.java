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


    private String appId;

    private String thirdId;

    private String tokenIn;

    private String tokenOut;

    private String addressIn;

    private String currencyIn;

    private String currencyOut;

    private String swapMethodName;

    private BigDecimal amountIn;

    private BigDecimal amountInActual;

    private BigDecimal amountOut;

    private String addressOut;

    private BigDecimal minerInFee;

    private Date swapTriggerDate;

    private Date swapClosureDate;

    private String swapTxid;

    private Integer status;

    private Date createdOn;

    private BigDecimal serviceFee;
}
