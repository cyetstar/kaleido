package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cyetstar
 * @Date 2022-09-13 01:07:00
 * @Description TODO
 */
@Data
public class SsxqListByParentReq {

    @ApiModelProperty(value = "所属辖区")
    private String ssxq;

    @ApiModelProperty(value = "范围类型")
    private String glmType = "szbm";

    @ApiModelProperty(value = "最小级别")
    private String minLevel;

    @ApiModelProperty(value = "是否包含根节点")
    private boolean root = false;

    @ApiModelProperty(value = "是否为初始请求")
    private boolean init = false;

    @ApiModelProperty(value = "是否启用简称")
    private boolean abbr = true;

    @ApiModelProperty(value = "数据类型")
    private String dataType = "zf";

}
