package cc.onelooker.kaleido.dto.trade;

import cc.onelooker.kaleido.dto.ISystem;
import lombok.Data;

/**
 * 交易账户DTO
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 * @see cc.onelooker.kaleido.entity.trade.TradeAccountDO
 */
@Data
public class TradeAccountDTO implements ISystem<Long> {
    private static final long serialVersionUID = 8216695649126550409L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 账户名称
     */
    private String zhmc;

    /**
     * 初始余额
     */
    private String csye;

    /**
     * 当前余额
     */
    private String dqye;

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
