package cc.onelooker.kaleido.dto.trade;

import cc.onelooker.kaleido.dto.IDictionary;
import com.zjjcnt.common.util.constant.Constants;
import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.String;
import java.lang.Long;
import java.lang.Integer;

import com.zjjcnt.common.core.annotation.Dict;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 交易商品DTO
 *
 * @author cyetstar
 * @date 2023-07-06 11:56:08
 * @see cc.onelooker.kaleido.entity.trade.TradeSymbolDO
 */
@Data
public class TradeSymbolDTO implements IDictionary<Long> {
    private static final long serialVersionUID = 8656769805223989087L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品名称
     */
    private String spmc;

    /**
     * 交易所
     */
    private String jys;

    /**
     * 交易币
     */
    private String jyb;

    /**
     * 交易币精度
     */
    private Integer jybjd;

    /**
     * 交易币手续费精度
     */
    private Integer jybsxfjd;

    /**
     * 计价币
     */
    private String jjb;

    /**
     * 计价币价格精度
     */
    private Integer jjbjgjd;

    /**
     * 计价币资产精度
     */
    private Integer jjbzcjd;

    /**
     * 计价币手续费精度
     */
    private Integer jjbsxfjd;

    /**
     * 是否允许市价委托
     */
    private String sfyxsjwt;

    /**
     * 是否允许api现货交易
     */
    private String sfyxapixhjy;

    /**
     * 是否允许api杠杆交易
     */
    private String sfyxapiggjy;

    /**
     * 支持订单类型
     */
    private String zcddlx;

    /**
     * 权限
     */
    private String qx;

    /**
     * 最大委托数量
     */
    private String zdwtsl;

    /**
     * marker手续费
     */
    private String markersxf;

    /**
     * taker手续费
     */
    private String takersxf;

    /**
     * 最小下单金额
     */
    private String zxxdje;

    /**
     * 最小下单数量
     */
    private String zxxdsl;

    /**
     * 是否收藏
     */
    private String sfsc;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return getSpmc();
    }

    public int getZxxdsljd() {
        return StringUtils.length(StringUtils.substringAfter(zxxdsl, Constants.DOT));
    }

}
