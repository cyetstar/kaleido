package cc.onelooker.kaleido.dto.system.resp;

import com.zjjcnt.common.core.annotation.DictSzbm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码巡区响应对象
 *
 * @author weed
 * @date 2023-02-17 11:52:04
 *
 */
@Data
@ApiModel("代码巡区响应对象")
public class FwtDmSqjwhViewResp{

    @ApiModelProperty("代码字母")
    private String dmzm;

    @ApiModelProperty("代码名称")
    private String dmmc;

    @ApiModelProperty("代码拼音")
    private String dmpy;

    @ApiModelProperty("代码顺序")
    private String dmsx;

    @ApiModelProperty("")
    private String xzjddm;

    @ApiModelProperty("")
    private String pcsdm;

    @ApiModelProperty("")
    private String cjrjh;

    @DictSzbm
    @ApiModelProperty("")
    private String cjrxq;

    @DictSzbm
    @ApiModelProperty("")
    private String cjrzrq;

    @ApiModelProperty("")
    private String xgrjh;

    @DictSzbm
    @ApiModelProperty("")
    private String xgrxq;

    @DictSzbm
    @ApiModelProperty("")
    private String xgrzrq;

    @ApiModelProperty("地址编码")
    private String dzbm;

    @ApiModelProperty("有效标志")
    private String yxbz;
}
