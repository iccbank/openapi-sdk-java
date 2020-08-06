package net.iccbank.openapi.sdk.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ApiAgencyWithdrawData implements Serializable {
	
	private static final long serialVersionUID = -2062802646158075450L;

	/**
	 * 平台订单号
	 */
    private Long businessNo;
    
    /**
	 * 商户订单号
	 */
    private String userBizId;

    /**
	 * 区块确认数
	 */
    //private Integer confirmations;
    
    /**
	 * 区块交易ID
	 */
    //private String txid;

    /**
	 * 地址
	 */
    private String address;

    /**
     * 币种代码
     */
    private String currencyCode;
    
    /**
     * 币种代码
     */
    //private String linkType;

    private String labelAddress;

    /**
     * 提现金额（实际到账金额）
     */
    private BigDecimal amount;
    
    /**
     * 业务手续费（从商户账户余额里额外扣除）
     */
    private BigDecimal fee;

    private Integer status;
    
    public ApiAgencyWithdrawData(){}

	public Long getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(Long businessNo) {
		this.businessNo = businessNo;
	}

	public String getUserBizId() {
		return userBizId;
	}

	public void setUserBizId(String userBizId) {
		this.userBizId = userBizId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getLabelAddress() {
		return labelAddress;
	}

	public void setLabelAddress(String labelAddress) {
		this.labelAddress = labelAddress;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}
