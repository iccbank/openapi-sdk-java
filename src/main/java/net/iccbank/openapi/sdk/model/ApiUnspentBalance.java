package net.iccbank.openapi.sdk.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author kevin
 * @Description 地址未花费余额
 * @Date Created on 2020/9/3 9:56
 */
public class ApiUnspentBalance implements Serializable {

    private static final long serialVersionUID = 1;

    private String currencyCode;

    private String address;

    private BigDecimal amount;

    public ApiUnspentBalance(){}

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
