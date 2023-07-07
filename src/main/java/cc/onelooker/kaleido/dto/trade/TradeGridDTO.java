package cc.onelooker.kaleido.dto.trade;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;

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
     * 商品代码
     */
    private String spdm;

    /**
     * 网格策略id
     */
    private Long strategId;

    /**
     * 入场价格
     */
    private BigDecimal rcjg;

    /**
     * 出场价格
     */
    private BigDecimal ccjg;

    /**
     * 套利次数
     */
    private Integer tlcs;

    /**
     * 套利金额
     */
    private BigDecimal tlje;

    /**
     * 是否在场
     */
    private String sfzc;

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
