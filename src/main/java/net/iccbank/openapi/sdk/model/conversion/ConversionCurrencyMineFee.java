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
public class ConversionCurrencyMineFee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
}
