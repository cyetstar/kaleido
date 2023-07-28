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
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易订单DO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.dto.trade.TradeOrderDTO
 */
@Data
@TableName("trade_order")
public class TradeOrderDO implements IdEntity<Long> {
    private static final long serialVersionUID = -6435329695876195398L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 交易商品id
     */
    @TableField(value = "symbol_id")
    private String symbolId;

    /**
     * 网格id
     */
    @TableField(value = "grid_id")
    private Long gridId;

    /**
     * 策略id
     */
    @TableField(value = "strategy_id")
    private Long strategyId;

    /**
     * 交易所订单号
     */
    @TableField(value = "jysddh")
    private String jysddh;

    /**
     * 价格
     */
    @TableField(value = "jg")
    private String jg;

    /**
     * 原始数量
     */
    @TableField(value = "yssl")
    private String yssl;

    /**
     * 交易数量
     */
    @TableField(value = "jysl")
    private String jysl;

    /**
     * 累计订单金额
     */
    @TableField(value = "ljddje")
    private String ljddje;

    /**
     * 订单状态
     */
    @TableField(value = "ddzt")
    private String ddzt;

    /**
     * 订单时效方式
     */
    @TableField(value = "ddsxfs")
    private String ddsxfs;

    /**
     * 订单类型
     */
    @TableField(value = "ddlx")
    private String ddlx;

    /**
     * 订单方向
     */
    @TableField(value = "ddfx")
    private String ddfx;

    /**
     * 止损价格
     */
    @TableField(value = "zsjg")
    private String zsjg;

    /**
     * 冰山数量
     */
    @TableField(value = "bssl")
    private Integer bssl;

    /**
     * 订单时间
     */
    @TableField(value = "ddsj")
    private String ddsj;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj")
    private String gxsj;

    /**
     * 原始交易金额
     */
    @TableField(value = "ysjyje")
    private String ysjyje;

    /**
     * 发送标志
     */
    @TableField(value = "fsbz")
    private String fsbz;

    /**
     * 发送时间
     */
    @TableField(value = "fssj")
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
