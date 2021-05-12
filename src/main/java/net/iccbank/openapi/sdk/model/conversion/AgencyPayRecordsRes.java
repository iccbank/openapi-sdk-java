package net.iccbank.openapi.sdk.model.conversion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgencyPayRecordsRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    private Long id;

    /**
     * 商户业务ID（第三方传入）
     */
    private String merchantBizId;

    /**
     * 商户订单描述
     */
    private String subject;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 地址
     */
    private String address;

    /**
     * 标签地址
     */
    private String labelAddress;

    /**
     * 区块链上维一标识，充币时确认是否交易过
     */
    private String txid;

    /**
     * 充提币金额
     */
    private BigDecimal amount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 手续费币种
     */
    private String feeCurrency;

    /**
     * 最终矿工 手续费
     */
    private BigDecimal actualMinerFee;

    /**
     * 矿工 手续费币种
     */
    private String minerFeeCurrency;

    /**
     * 矿工 手续费
     */
    private BigDecimal minerFee;

    /**
     * 状态 0:处理中/确认中 ,1:成功/已确认,2:失败/打包失败/孤块, 3:调用钱包出款成功, 4:调用钱包出款失败
     */
    private Integer walletStatus;

    /**
     * 审核状态（1-待审核 2-审核成功，3-审核拒绝）
     */
    private Integer auditStatus;

    /**
     * 完成时间（最终状态时的时间）
     */
    private Date completedOn;

    /**
     * 创建时间
     */
    private Date createdOn;
}
