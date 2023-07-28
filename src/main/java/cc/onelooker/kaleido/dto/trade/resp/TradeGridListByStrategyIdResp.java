package cc.onelooker.kaleido.dto.trade.resp;

import com.zjjcnt.common.core.annotation.Dict;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2023-07-09 00:35:00
 * @Description TODO
 */
@Data
public class TradeGridListByStrategyIdResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("商品代码")
    private Long symbolId;

    @ApiModelProperty("策略id")
    private Long strategyId;

    @ApiModelProperty("入场价格")
    private String rcjg;

    @ApiModelProperty("出场价格")
    private String ccjg;

    @ApiModelProperty("套利次数")
    private Integer tlcs;

    @ApiModelProperty("套利金额")
    private String tlje;

    @Dict("sfbz")
    @ApiModelProperty("是否在场")
    private String sfzc;

    @Dict("sfbz")
    @ApiModelProperty("是否运行")
    private String sfyx;
}
