package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 乡镇街道响应对象
 *
 * @author weed
 * @date 2023-02-17 11:52:04
 *
 */
@Data
@ApiModel("乡镇街道响应对象")
public class FwtDmXzjdViewResp{

    @ApiModelProperty("代码")
    private String dmzm;

    @ApiModelProperty("代码名称")
    private String dmmc;

    @ApiModelProperty("代码拼音")
    private String dmpy;

    @ApiModelProperty("代码顺序")
    private String dmsx;

    @ApiModelProperty("有效标志")
    private String yxbz;
}
