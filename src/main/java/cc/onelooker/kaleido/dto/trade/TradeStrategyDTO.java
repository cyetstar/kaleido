package cc.onelooker.kaleido.dto.trade;

import com.zjjcnt.common.core.dto.BaseDTO;
import lombok.Data;

/**
 * 策略DTO
 *
 * @author cyetstar
 * @date 2023-07-05 23:02:49
 * @see cc.onelooker.kaleido.entity.trade.TradeStrategyDO
 */
@Data
public class TradeStrategyDTO implements BaseDTO<Long> {
    private static final long serialVersionUID = -3372255069223825511L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 交易商品id
     */
    private Long symbolId;

    /**
     * 交易方向
     */
    private String jyfx;

    /**
     * 区间最高值
     */
    private String qjzgz;

    /**
     * 区间最低值
     */
    private String qjzdz;

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
    private String trje;

    /**
     * 网格金额
     */
    private String wgje;

    /**
     * 区间上移上限
     */
    private String qjsysx;

    /**
     * 区间下移下限
     */
    private String qjxyxx;

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
    private String zyjg;

    /**
     * 止损价格
     */
    private String zsjg;

    /**
     * 开始时间
     */
    private String kssj;

    /**
     * 是否增加网格
     */
    private String sfzjwg;

    /**
     * 是否运行
     */
    private String sfyx;

    /**
     * 创建时间
     */
    private String cjsj;

    /**
     * 修改时间
     */
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
