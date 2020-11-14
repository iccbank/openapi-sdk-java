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
public class ConversionCurrencyMineFee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付币种
     */
    private String currency;
    /**
     * 链类型
     */
    private String linkType;
    /**
     * 合约地址
     */
    private String contractAddress;
    /**
     * 矿工费币种
     */
    private String feeCoin;
    /**
     * 矿工费
     */
    private BigDecimal feeNum;
    /**
     * 最小矿工费
     */
    private BigDecimal feeMinNum;
    /**
     * 最大矿工费
     */
    private BigDecimal feeMaxNum;
}
