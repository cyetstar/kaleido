package cc.onelooker.kaleido.dto.trade;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

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
     * 交易商品id
     */
    private Long symbolId;

    /**
     * 网格id
     */
    private Long gridId;

    /**
     * 策略id
     */
    private Long strategyId;

    /**
     * 交易所订单号
     */
    private String jysddh;

    /**
     * 价格
     */
    private String jg;

    /**
     * 原始数量
     */
    private String yssl;

    /**
     * 交易数量
     */
    private String jysl;

    /**
     * 累计订单金额
     */
    private String ljddje;

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
    private String zsjg;

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
    private String ysjyje;

    /**
     * 发送标志
     */
    private String fsbz;

    /**
     * 发送时间
     */
    private String fssj;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
