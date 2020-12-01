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
public class ConversionRate implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 支付金额 */
    private BigDecimal amountFrom;
    /** 兑换金额 */
    private BigDecimal amountTo;
    /** 兑换手续费 */
    private BigDecimal conversionFee;
    /** 网络费（旷工费） */
    private BigDecimal networkFee;
    /** 兑换汇率 */
    private BigDecimal rate;
    /** 汇率id（固定利率才有） */
    private Long rateId;
    /** 最大支付金额 */
    private BigDecimal maxFrom;
    /** 最小支付金额 */
    private BigDecimal minFrom;

    @Override
    public String toString() {
        return "RateBO{" +
                "amountFrom=" + amountFrom +
                ", amountTo=" + amountTo +
                ", conversionFee=" + conversionFee +
                ", networkFee=" + networkFee +
                ", rate=" + rate +
                ", rateId=" + rateId +
                ", maxFrom=" + maxFrom +
                ", minFrom=" + minFrom +
                '}';
    }

}
