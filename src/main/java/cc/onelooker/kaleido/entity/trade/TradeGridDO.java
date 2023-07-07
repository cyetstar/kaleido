package cc.onelooker.kaleido.entity.trade;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

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
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品代码
     */
    @TableField(value = "spdm")
    private String spdm;

    /**
     * 网格策略id
     */
    @TableField(value = "strateg_id")
    private Long strategId;

    /**
     * 入场价格
     */
    @TableField(value = "rcjg")
    private BigDecimal rcjg;

    /**
     * 出场价格
     */
    @TableField(value = "ccjg")
    private BigDecimal ccjg;

    /**
     * 套利次数
     */
    @TableField(value = "tlcs")
    private Integer tlcs;

    /**
     * 套利金额
     */
    @TableField(value = "tlje")
    private BigDecimal tlje;

    /**
     * 是否在场
     */
    @TableField(value = "sfzc")
    private String sfzc;

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
