package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author fangfeiting
 * @Date 2021-07-01 17:28:00
 * @Description TODO
 */
@Data
@Accessors(chain = true)
public class WeixinConfigResp {
    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("时间戳")
    private String timestamp;

    @ApiModelProperty("签名")
    private String signature;

}
