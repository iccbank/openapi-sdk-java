package net.iccbank.openapi.sdk.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiMinerPower implements Serializable {
    private static final long serialVersionUID = 8743713632041268329L;

    //当前旷工是否有算力
    private boolean hasMinPower;
    //当前旷工算力
    private BigInteger minerPower;
    //全网总算力
    private BigInteger totalPower;
}
