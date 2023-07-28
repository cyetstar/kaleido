package cc.onelooker.kaleido.entity.trade;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;

import com.zjjcnt.common.core.entity.IdEntity;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;

import com.zjjcnt.common.core.annotation.Dict;

import java.math.BigDecimal;

/**
 * 交易商品DO
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 * @see cc.onelooker.kaleido.dto.trade.TradeSymbolDTO
 */
@Data
@TableName("trade_symbol")
public class TradeSymbolDO implements IdEntity<Long> {
    private static final long serialVersionUID = -7895891580400932444L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品名称
     */
    @TableField(value = "spmc")
    private String spmc;

    /**
     * 交易所
     */
    @TableField(value = "jys")
    private String jys;

    /**
     * 交易币
     */
    @TableField(value = "jyb")
    private String jyb;

    /**
     * 交易币精度
     */
    @TableField(value = "jybjd")
    private Integer jybjd;

    /**
     * 交易币手续费精度
     */
    @TableField(value = "jybsxfjd")
    private Integer jybsxfjd;

    /**
     * 计价币
     */
    @TableField(value = "jjb")
    private String jjb;

    /**
     * 计价币价格精度
     */
    @TableField(value = "jjbjgjd")
    private Integer jjbjgjd;

    /**
     * 计价币资产精度
     */
    @TableField(value = "jjbzcjd")
    private Integer jjbzcjd;

    /**
     * 计价币手续费精度
     */
    @TableField(value = "jjbsxfjd")
    private Integer jjbsxfjd;

    /**
     * 是否允许市价委托
     */
    @TableField(value = "sfyxsjwt")
    private String sfyxsjwt;

    /**
     * 是否允许api现货交易
     */
    @TableField(value = "sfyxapixhjy")
    private String sfyxapixhjy;

    /**
     * 是否允许api杠杆交易
     */
    @TableField(value = "sfyxapiggjy")
    private String sfyxapiggjy;

    /**
     * 支持订单类型
     */
    @TableField(value = "zcddlx")
    private String zcddlx;

    /**
     * 权限
     */
    @TableField(value = "qx")
    private String qx;

    /**
     * 最大委托数量
     */
    @TableField(value = "zdwtsl")
    private String zdwtsl;

    /**
     * marker手续费
     */
    @TableField(value = "markersxf")
    private String markersxf;

    /**
     * taker手续费
     */
    @TableField(value = "takersxf")
    private String takersxf;

    /**
     * 最小下单金额
     */
    @TableField(value = "zxxdje")
    private String zxxdje;

    /**
     * 最小下单数量
     */
    @TableField(value = "zxxdsl")
    private String zxxdsl;

    /**
     * 是否收藏
     */
    @TableField(value = "sfsc")
    private String sfsc;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
