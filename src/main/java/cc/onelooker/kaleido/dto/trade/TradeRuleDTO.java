package cc.onelooker.kaleido.dto.trade;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

/**
 * 交易规则DTO
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 * @see cc.onelooker.kaleido.entity.trade.TradeRuleDO
 */
@Data
public class TradeRuleDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -6847903456264562996L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 交易账户id
     */
    private Long accountId;

    /**
     * 盈亏比
     */
    private String ykb;

    /**
     * 风险比
     */
    private String fxb;

    /**
     * 风险金额
     */
    private String fxje;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
