package net.iccbank.openapi.sdk.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author kevin
 * @Description 获取未花费utxo
 * @Date Created on 2020/8/31 15:46
 * @since 1.1.0
 */
public class ApiUnspentUtxo implements Serializable {

    private static final long serialVersionUID = 1;

    private String vout;            	// 此笔UTXO在交易里的位置(序号从0开始)
    private String txid;                // 交易hash
    private String address;             // 地址
    private String scriptPubKey;        // 公钥脚本
    private Integer confirmations;      // 此交易的确认数
    private BigDecimal value;           // UTXO里包含的金额

    public ApiUnspentUtxo(){}

    public String getVout() {
        return vout;
    }

    public void setVout(String vout) {
        this.vout = vout;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(String scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
