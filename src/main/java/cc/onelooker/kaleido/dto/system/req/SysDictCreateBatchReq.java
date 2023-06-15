package cc.onelooker.kaleido.dto.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典表请求对象
 *
 * @author xiadawei
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("字典表请求对象")
public class SysDictCreateBatchReq {

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("内容")
    private String content;

}
