package cc.onelooker.kaleido.dto.trade.req;

import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交易账户请求对象
 *
 * @author cyetstar
 * @date 2023-06-23 13:38:46
 *
 */
@Data
@ApiModel("交易账户请求对象")
public class TradeAccountCreateReq{

    @ApiModelProperty("账户名称")
    private String zhmc;

    @ApiModelProperty("初始余额")
    private String csye;

    @ApiModelProperty("当前余额")
    private String dqye;

    @StringDateTimeFormat
    @ApiModelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ApiModelProperty("修改时间")
    private String xgsj;
}
