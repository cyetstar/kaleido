package cc.onelooker.kaleido.dto.system.resp;

import com.zjjcnt.common.core.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 响应对象
 *
 * @author weed
 * @date 2023-02-17 11:52:04
 *
 */
@Data
@ApiModel("响应对象")
public class FwtDmXzqhViewResp{

    @ApiModelProperty("代码字母")
    private String dmzm;

    @ApiModelProperty("代码全称")
    private String dmqc;

    @ApiModelProperty("代码名称")
    private String dmmc;

    @ApiModelProperty("代码拼音")
    private String dmpy;

    @ApiModelProperty("代码顺序")
    private String dmsx;

    @ApiModelProperty("最新代码")
    private String zxdm;

    @Dict("dzbm")
    @ApiModelProperty("")
    private String dzbm;

    @Dict("dmjc")
    @ApiModelProperty("")
    private String dmjc;

    @ApiModelProperty("有效标志")
    private String yxbz;
}
