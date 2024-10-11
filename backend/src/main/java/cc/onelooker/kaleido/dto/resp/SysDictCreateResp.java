package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字典表响应对象
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Data
@ApiModel("字典表响应对象")
public class SysDictCreateResp {

    @ApiModelProperty("字典详情id")
    private Long id;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典标签")
    private String label;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty("顺序号")
    private Integer sort;

}
