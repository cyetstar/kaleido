package cc.onelooker.kaleido.dto.trade;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import com.zjjcnt.common.core.annotation.Dict;
import java.math.BigDecimal;
import java.lang.Integer;

/**
 * 网格策略DTO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.entity.trade.TradeGridStrategyDO
 */
@Data
public class TradeGridStrategyDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3372255069223825511L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品代码
     */
    private String spdm;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
    private String xgsj;

    /**
     * 交易方向
     */
    private String jyfx;

    /**
     * 区间最高值
     */
    private BigDecimal qjzgz;

    /**
     * 区间最低值
     */
    private BigDecimal qjzdz;

    /**
     * 网格模式
     */
    private String wgms;

    /**
     * 网格数量
     */
    private Integer wgsl;

    /**
     * 投入金额
     */
    private BigDecimal trje;

    /**
     * 区间上移上限
     */
    private BigDecimal qjsysx;

    /**
     * 区间下移下限
     */
    private BigDecimal qjxyxx;

    /**
     * 是否区间上移
     */
    private String sfqjsy;

    /**
     * 是否区间下移
     */
    private String sfqjxy;

    /**
     * 开始条件
     */
    private String kstj;

    /**
     * 停止条件
     */
    private String tztj;

    /**
     * 是否停止时卖出
     */
    private String sftzsmc;

    /**
     * 止盈价格
     */
    private BigDecimal zyjg;

    /**
     * 止损价格
     */
    private BigDecimal zsjg;

    /**
     * 开始时间
     */
    private String kssj;

    /**
     * 是否增加网格
     */
    private String sfzjwg;


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
