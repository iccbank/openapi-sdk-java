package net.iccbank.openapi.sdk.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiAgencyWithdrawQueryData implements Serializable {

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
    private Integer confirmations;

    /**
	 * 区块交易ID
	 */
    private String txid;

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
    private String labelAddress;

    /**
     * 提现金额（实际到账金额）
     */
    private BigDecimal amount;

    /**
     * 业务手续费（从商户账户余额里额外扣除）
     */
    private BigDecimal fee;

	private String feeCurrency;

	//状态， 0, “处理中” 1, “成功” 2, “失败”
    private Integer status;

}
