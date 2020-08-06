package net.iccbank.openapi.sdk.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Author kevin
 * @Description 账户余额
 * @Date Created on 2020/7/8 11:48
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiMchBalance implements Serializable {

    private static final long serialVersionUID = 1;

    private List<BalanceNode> rows ;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BalanceNode implements java.io.Serializable{

        private String availableBalance;

        private String frozenBalance;

        private String currencyCode;

        private Long accountType;

    }

}
