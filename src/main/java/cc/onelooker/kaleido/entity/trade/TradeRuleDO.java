package cc.onelooker.kaleido.entity.trade;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.math.BigDecimal;
import java.lang.String;

/**
 * 交易规则DO
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 * @see cc.onelooker.kaleido.dto.trade.TradeRuleDTO
 */
@Data
@TableName("trade_rule")
public class TradeRuleDO implements IdEntity<Long> {
    private static final long serialVersionUID = 7275303353933522958L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 交易账户id
     */
    @TableField(value = "account_id")
    private Long accountId;

    /**
     * 盈亏比
     */
    @TableField(value = "ykb")
    private String ykb;

    /**
     * 风险比
     */
    @TableField(value = "fxb")
    private String fxb;

    /**
     * 风险金额
     */
    @TableField(value = "fxje")
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
