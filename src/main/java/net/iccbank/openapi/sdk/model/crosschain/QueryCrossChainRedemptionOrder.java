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
public class QueryCrossChainRedemptionOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 第三方业务id
     */
    private String merchantBizId;

    /**
     * 赎回支付币种(代币)
     */
    private String payCurrencyCode;

    /**
     * 赎回收入币种（主链币）
     */
    private String receiveCurrencyCode;

    /**
     * 申购预期支付数量
     */
    private BigDecimal expectPayAmount;

    /**
     * 赎回预期收入数量
     */
    private BigDecimal expectReceiveAmount;

    /**
     * 赎回实际支付数量
     */
    private BigDecimal actualPayAmount;

    /**
     * 赎回实际收入数量
     */
    private BigDecimal actualReceiveAmount;

    /**
     * 赎回支付地址（平台提供的代币打款地址）
     */
    private String payAddress;

    /**
     * 赎回支付标签地址
     */
    private String payLabelAddress;

    /**
     * 赎回接收地址（用户提供的主链币收款地址）
     */
    private String receiveAddress;

    /**
     * 赎回接收标签地址
     */
    private String receiveLabelAddress;

    /**
     * 赎回手续费
     */
    private BigDecimal fee;

    /**
     * 赎回手续费币种
     */
    private String feeCurrency;

    /**
     * 赎回转账矿工费
     */
    private BigDecimal transferMinerFee;

    /**
     * 赎回转账矿工费币种
     */
    private String transferMinerFeeCurrency;

    /**
     * 赎回时客户支付的txid
     */
    private String payTxId;

    /**
     * 给客户转账的txid
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
