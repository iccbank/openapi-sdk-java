package net.iccbank.openapi.sdk.model.crosschain;

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
public class QueryCrossChainSubscriptionOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方业务id
     */
    private String merchantBizId;

    /**
     * 申购支付币种（客户转过来的主链币）
     */
    private String payCurrencyCode;

    /**
     * 申购收入币种(代币币种)
     */
    private String receiveCurrencyCode;

    /**
     * 申购预期支付数量
     */
    private BigDecimal expectPayAmount;

    /**
     * 申购预期收入数量
     */
    private BigDecimal expectReceiveAmount;

    /**
     * 申购实际支付数量
     */
    private BigDecimal actualPayAmount;

    /**
     * 申购实际收入数量
     */
    private BigDecimal actualReceiveAmount;

    /**
     * 申购代收地址(平台创建，用户将主链币转到该地址)
     */
    private String payAddress;

    /**
     * 申购代收地址标签地址
     */
    private String payLabelAddress;

    /**
     * 代币接收地址(用户提供，平台将铸造代币转到该地址)
     */
    private String receiveAddress;

    /**
     * 代币接收地址标签地址
     */
    private String receiveLabelAddress;

    /**
     * 申购手续费
     */
    private BigDecimal fee;

    /**
     * 申购手续费币种
     */
    private String feeCurrency;

    /**
     * 申购用户支付txid
     */
    private String payTxId;

    /**
     * 铸造后平台转账txid
     */
    private String transferTxId;

    /**
     * 状态 0-交易中，1-成功，2-失败
     */
    private Integer status;

    /**
     * 订单完成时间（最终状态时的时间）
     */
    private Date completedOn;

    private Date createdOn;
}
