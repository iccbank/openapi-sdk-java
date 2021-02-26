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
public class CreateCrossChainSubscriptionOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 主链币种code
     */
    private String currencyCode;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付地址
     */
    private String payAddress;

    /**
     * 支付标签地址
     */
    private String payLabelAddress;

    /**
     * 支付截止时间（单位：毫秒）
     */
    private Long payTill;
}
