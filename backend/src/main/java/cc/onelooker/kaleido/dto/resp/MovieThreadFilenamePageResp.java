package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电影发布文件响应对象
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 */
@Data
@ApiModel("电影发布文件响应对象")
public class MovieThreadFilenamePageResp {

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String value;

    @ApiModelProperty("")
    private Long threadId;
}
