package cc.onelooker.kaleido.entity.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 策略DO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.dto.trade.TradeStrategyDTO
 */
@Data
@TableName("trade_strategy")
public class TradeStrategyDO implements IdEntity<Long> {
    private static final long serialVersionUID = -5568137640039799353L;

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
     * 交易方向
     */
    @TableField(value = "jyfx")
    private String jyfx;

    /**
     * 区间最高值
     */
    @TableField(value = "qjzgz")
    private String qjzgz;

    /**
     * 区间最低值
     */
    @TableField(value = "qjzdz")
    private String qjzdz;

    /**
     * 网格模式
     */
    @TableField(value = "wgms")
    private String wgms;

    /**
     * 网格数量
     */
    @TableField(value = "wgsl")
    private Integer wgsl;

    /**
     * 投入金额
     */
    @TableField(value = "trje")
    private String trje;

    /**
     * 网格金额
     */
    @TableField(value = "wgje")
    private String wgje;

    /**
     * 区间上移上限
     */
    @TableField(value = "qjsysx")
    private String qjsysx;

    /**
     * 区间下移下限
     */
    @TableField(value = "qjxyxx")
    private String qjxyxx;

    /**
     * 是否区间上移
     */
    @TableField(value = "sfqjsy")
    private String sfqjsy;

    /**
     * 是否区间下移
     */
    @TableField(value = "sfqjxy")
    private String sfqjxy;

    /**
     * 开始条件
     */
    @TableField(value = "kstj")
    private String kstj;

    /**
     * 停止条件
     */
    @TableField(value = "tztj")
    private String tztj;

    /**
     * 是否停止时卖出
     */
    @TableField(value = "sftzsmc")
    private String sftzsmc;

    /**
     * 止盈价格
     */
    @TableField(value = "zyjg")
    private String zyjg;

    /**
     * 止损价格
     */
    @TableField(value = "zsjg")
    private String zsjg;

    /**
     * 开始时间
     */
    @TableField(value = "kssj")
    private String kssj;

    /**
     * 是否增加网格
     */
    @TableField(value = "sfzjwg")
    private String sfzjwg;

    /**
     * 是否运行
     */
    @TableField(value = "sfyx")
    private String sfyx;

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
