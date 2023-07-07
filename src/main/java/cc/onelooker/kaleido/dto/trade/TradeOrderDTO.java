package cc.onelooker.kaleido.dto.trade;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易订单DTO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.entity.trade.TradeOrderDO
 */
@Data
public class TradeOrderDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = 8441684573522247179L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品代码
     */
    private String spdm;

    /**
     * 网格id
     */
    private Long gridId;

    /**
     * 交易所订单号
     */
    private String jysddh;

    /**
     * 价格
     */
    private BigDecimal jg;

    /**
     * 原始数量
     */
    private BigDecimal yssl;

    /**
     * 交易数量
     */
    private BigDecimal yjsl;

    /**
     * 累计订单金额
     */
    private BigDecimal ljddje;

    /**
     * 订单状态
     */
    private String ddzt;

    /**
     * 订单时效方式
     */
    private String ddsxfs;

    /**
     * 订单类型
     */
    private String ddlx;

    /**
     * 订单方向
     */
    private String ddfx;

    /**
     * 止损价格
     */
    private BigDecimal zsjg;

    /**
     * 冰山数量
     */
    private Integer bssl;

    /**
     * 订单时间
     */
    private String ddsj;

    /**
     * 更新时间
     */
    private String gxsj;

    /**
     * 原始交易金额
     */
    private BigDecimal ysjyje;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
