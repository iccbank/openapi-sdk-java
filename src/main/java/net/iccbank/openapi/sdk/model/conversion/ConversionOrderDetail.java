package net.iccbank.openapi.sdk.model.conversion;

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
public class ConversionOrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private Long userId;
    private String thirdOrderId;

    /**
     *   订单来源（web、api。。）
     */
    private String orderSource;

    /**
     *   支付币种
     */
    private String currencyFrom;

    /**
     *   兑换币种
     */
    private String currencyTo;

    /**
     *   兑换地址
     */
    private String addressTo;

    /**
     *   兑换地址标签
     */
    private String labelAddressTo;

    /**
     *   兑换代付id
     */
    private String proxyToId;

    /**
     *   截止支付时间（带时区的时间字符串）
     */
    private Date payTill;

    /**
     *   平台收款地址
     */
    private String payinAddress;

    /**
     *   平台收款地址标签
     */
    private String payinLabelAddress;

    /**
     *   平台代收记录id
     */
    private String payinId;

    /**
     *   退款地址
     */
    private String refundAddress;

    /**
     *   退款地址标签
     */
    private String refundLabelAddress;

    /**
     *   预计支付
     */
    private BigDecimal amountExpectedFrom;

    /**
     *   预计收到
     *
     */
    private BigDecimal amountExpectedTo;

    /**
     *   实际支付
     */
    private BigDecimal amountFrom;

    /**
     *   实际收到
     */
    private BigDecimal amountTo;

    /**
     *   api佣金(目前没用)
     */
    private BigDecimal apiFee;

    /**
     *   平台费用（总费用）
     */
    private BigDecimal platformFee;

    /**
     *   预计矿工费
     */
    private BigDecimal minerFee;

    /**
     *   实际旷工费
     */
    private BigDecimal actualMinerFee;

    /**
     *   发起兑换时汇率
     */
    private BigDecimal launchConversionRate;

    /**
     *   实际兑换利率
     */
    private BigDecimal actualConversionRate;

    /**
     *   0-固定利率 1-浮动利率
     */
    private Integer type;

    /**
     *   0-新订单 1-等待中 2-确认中 3-兑换中 4-发送中 5-已完成 6-已失败 7-已退款 8-逾期 9-暂停 10-已过期
     */
    private Integer status;

    /**
     *   0-待退款 1-退款中 2-退款成功 3-退款失败 4-无
     */
    private Integer refundStatus;

    /**
     *   true-启用 false-禁用
     */
    private Boolean enabled;


    private Date createdOn;
    private Date updatedOn;



}
