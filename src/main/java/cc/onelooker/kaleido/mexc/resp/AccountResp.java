package cc.onelooker.kaleido.mexc.resp;

import lombok.Data;

import java.util.List;

/**
 * @Author xiadawei
 * @Date 2023-07-07 17:46:00
 * @Description TODO
 */
@Data
public class AccountResp {

    private Integer makerCommission;
    private Integer takerCommission;
    private Integer buyerCommission;
    private Integer sellerCommission;
    private Boolean canTrade;
    private Boolean canWithdraw;
    private Boolean canDeposit;
    private String updateTime;
    private String accountType;
    private List<Balance> balances;
    private List<String> permissions;

    @Data
    public static class Balance {
        private String asset;
        private String free;
        private String locked;
    }
}
