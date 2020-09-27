package net.iccbank.openapi.sdk.model.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号-代码 唯一
     */
    private String code;
    /**
     * 支付币种
     */
    private String currency;
    /**
     * 兑换币种
     */
    private String targetCurrency;
}
