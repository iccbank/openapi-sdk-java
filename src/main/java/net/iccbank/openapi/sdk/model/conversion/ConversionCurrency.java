package net.iccbank.openapi.sdk.model.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionCurrency implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付币种
     */
    private String currency;
    /**
     * 可兑换币种列表
     */
    private List<String> targetCurrencyList;
}
