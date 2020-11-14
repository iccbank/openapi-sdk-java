package net.iccbank.openapi.sdk.model.conversion;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFloatRateConversion implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 兑换订单号 */
    private Long id;
    /**  */
    private BigDecimal amountFrom;
    /** 平台收款地址 */
    private String payinAddress;
    /** 平台收款标签地址 */
    private String payinLabelAddress;
    /** 支付截止时间 */
    private Long payTill;

}
