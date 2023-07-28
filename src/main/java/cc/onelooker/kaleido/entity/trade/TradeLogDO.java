package cc.onelooker.kaleido.entity.trade;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;
import java.lang.Long;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;
import java.math.BigDecimal;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 交易记录DO
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 * @see cc.onelooker.kaleido.dto.trade.TradeLogDTO
 */
@Data
@TableName("trade_log")
public class TradeLogDO implements IdEntity<Long> {
    private static final long serialVersionUID = 5010798242045256694L;

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
     * 交易账户id
     */
    @TableField(value = "account_id")
    private Long accountId;

    /**
     * 交易方向
     */
    @TableField(value = "jyfx")
    private String jyfx;

    /**
     * 胜负结果
     */
    @TableField(value = "sfjg")
    private String sfjg;

    /**
     * 时间周期
     */
    @TableField(value = "sjzq")
    private String sjzq;

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
     * 头寸规模
     */
    @TableField(value = "tcgm")
    private String tcgm;

    /**
     * 盈亏金额
     */
    @TableField(value = "ykje")
    private String ykje;

    /**
     * 交易时间
     */
    @TableField(value = "jysj")
    private String jysj;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj")
    private String cjsj;

    /**
     * 修改时间
     */
    @TableField(value = "xgsj")
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
