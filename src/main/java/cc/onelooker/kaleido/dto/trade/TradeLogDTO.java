package cc.onelooker.kaleido.dto.trade;

import cc.onelooker.kaleido.dto.ISystem;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易记录DTO
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 * @see cc.onelooker.kaleido.entity.trade.TradeLogDO
 */
@Data
public class TradeLogDTO implements ISystem<Long> {
    private static final long serialVersionUID = -6716214359599170381L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品代码
     */
    private String spdm;

    /**
     * 交易账户id
     */
    private Long accountId;

    /**
     * 交易方向
     */
    private String jyfx;

    /**
     * 胜负结果
     */
    private String sfjg;

    /**
     * 时间周期
     */
    private String sjzq;

    /**
     * 入场价格
     */
    private BigDecimal rcjg;

    /**
     * 出场价格
     */
    private BigDecimal ccjg;

    /**
     * 头寸规模
     */
    private BigDecimal tcgm;

    /**
     * 盈亏金额
     */
    private BigDecimal ykje;

    /**
     * 交易时间
     */
    private String jysj;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
