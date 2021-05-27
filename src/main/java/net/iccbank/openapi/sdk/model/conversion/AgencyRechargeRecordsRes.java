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
public class AgencyRechargeRecordsRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务ID
     */
    private Long id;

    /**
     * 钱包ID
     */
    private Long bizId;

    /**
     * 区块链上维一标识，充币时确认是否交易过
     */
    private String txid;

    /**
     * 地址
     */
    private String address;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 链类型
     */
    private String linkType;

    /**
     * 标签地址
     */
    private String labelAddress;

    /**
     * 充提币金额
     */
    private BigDecimal amount;

    /**
     * 状态 0:处理中/确认中 ,1:成功/已确认,2:失败/打包失败/孤块
     */
    private Integer walletStatus;

    /**
     * 审核状态：1-待审核，2-审核成功，3-审核拒绝
     */
    private Integer auditStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 完成时间（最终状态时的时间）
     */
    private Date completedOn;

    /**
     * 创建时间
     */
    private Date createdOn;
}
