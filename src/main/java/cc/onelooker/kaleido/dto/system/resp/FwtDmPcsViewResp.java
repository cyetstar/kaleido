package cc.onelooker.kaleido.dto.system.resp;

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
public class FwtDmPcsViewResp{

    @ApiModelProperty("代码字母")
    private String dmzm;

    @ApiModelProperty("代码识别码")
    private String dmsbm;

    @ApiModelProperty("代码名称")
    private String dmmc;

    @ApiModelProperty("地址简称")
    private String dmjc;

    @ApiModelProperty("代码拼音")
    private String dmpy;

    @ApiModelProperty("代码顺序")
    private String dmsx;

    @ApiModelProperty("辖区名称")
    private String xqmc;

    @ApiModelProperty("地址编码")
    private String dzbm;

    @ApiModelProperty("联系电话")
    private String lxdh;

    @ApiModelProperty("开放时间")
    private String kfsj;

    @ApiModelProperty("地址")
    private String dz;

    @ApiModelProperty("有效标志")
    private String yxbz;
}
