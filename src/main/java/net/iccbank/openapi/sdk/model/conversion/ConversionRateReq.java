package net.iccbank.openapi.sdk.model.conversion;

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
public class ConversionRateReq implements Serializable {

    /** 币对code */
    private String code;
    /** 是否是固定利率 */
    private Boolean fixedRate;
    /** from币种输入金融 */
    private BigDecimal amountFrom;
    /** to币种输入金额 */
    private BigDecimal amountTo;

}
