package cc.onelooker.kaleido.entity.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 交易网格DO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.dto.trade.TradeGridDTO
 */
@Data
@TableName("trade_grid")
public class TradeGridDO implements IdEntity<Long> {
    private static final long serialVersionUID = 1053009247894191968L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 交易商品id
     */
    @TableField(value = "symbol_id")
    private Long symbolId;

    /**
     * 策略id
     */
    @TableField(value = "strategy_id")
    private Long strategyId;

    /**
     * 网格金额
     */
    @TableField(value = "wgje")
    private String wgje;

    /**
     * 入场价格
     */
    @TableField(value = "rcjg")
    private String rcjg;

    /**
     * 出场价格
     */
    @TableField(value = "ccjg")
    private String ccjg;

    /**
     * 套利次数
     */
    @TableField(value = "tlcs")
    private Integer tlcs;

    /**
     * 套利金额
     */
    @TableField(value = "tlje")
    private String tlje;

    /**
     * 网格方向
     */
    @TableField(value = "wgfx")
    private String wgfx;

    /**
     * 是否运行
     */
    @TableField(value = "sfyx")
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
