package net.iccbank.openapi.sdk.model.conversion;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GetConversionListReq implements Serializable {

    //订单来源（web、api。。）
    private String orderSource;
    //支付币种
    private String currencyFrom;
    //兑换币种
    private String currencyTo;
    //0-固定利率 1-浮动利率
    private Integer rateType;
    //0-等待中 1-确认中 2-兑换中 3-发送中 4-已完成 5-已失败 6-已退款 7-逾期 8-暂停 9-已过期
    private Integer status;
    //0-待退款 1-退款中 2-退款成功 3-无
    private Integer refundStatus;

    private Integer pageIndex;
    private Integer pageSize;
}
