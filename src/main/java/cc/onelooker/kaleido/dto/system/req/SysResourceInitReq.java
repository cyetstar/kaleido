package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 资源表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("资源表请求对象")
public class SysResourceInitReq {

    @ApiModelProperty("资源类型")
    private String type;

    @ApiModelProperty("操作类型")
    private List<String> action;
}
