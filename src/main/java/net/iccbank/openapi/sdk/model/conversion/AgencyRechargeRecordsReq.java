package net.iccbank.openapi.sdk.model.conversion;

import java.io.Serializable;
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
public class AgencyRechargeRecordsReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 开始时间 (时间戳)
     */
    private Long startTime;

    /**
     * 截止时间 (时间戳)
     */
    private Long endTime;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;
}
