package cc.onelooker.kaleido.dto.trade;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 交易网格DTO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.entity.trade.TradeGridDO
 */
@Data
public class TradeGridDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 7816761761393261485L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 交易商品id
     */
    private Long symbolId;

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * 网格金额
     */
    private String wgje;

    /**
     * 入场价格
     */
    private String rcjg;

    /**
     * 出场价格
     */
    private String ccjg;

    /**
     * 套利次数
     */
    private Integer tlcs;

    /**
     * 套利金额
     */
    private String tlje;

    /**
     * 网格方向
     */
    private String wgfx;

    /**
     * 是否运行
     */
    private String sfyx;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
