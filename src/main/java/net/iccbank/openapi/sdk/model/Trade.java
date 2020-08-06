package net.iccbank.openapi.sdk.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author kevin
 * @Description 交易
 * @Date Created on 2020/6/10 13:27
 */

@Data
public class Trade implements Serializable {

    //平台订单号
    private Long businessNo;

    //商户订单号
    private String userBizId;

    //确认次数
    private Integer confirmations;

    //交易hash
    private String txid;

    //地址
    private String address;

    //币种
    private String currencyCode;

    private String linkType;

    //标签地址 memo
    private String labelAddress;

    //金额
    private BigDecimal amount;

    //手续费
    private BigDecimal fee;

    private String feeCurrency;

    //交易状态  0, “处理中” 1, “成功” 2, “失败”
    private Integer status;

}
