package cc.onelooker.kaleido.dto.trade;

import cc.onelooker.kaleido.dto.ISystem;
import lombok.Data;

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
     * 交易商品id
     */
    private Long symbolId;

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
    private String rcjg;

    /**
     * 出场价格
     */
    private String ccjg;

    /**
     * 头寸规模
     */
    private String tcgm;

    /**
     * 盈亏金额
     */
    private String ykje;

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
