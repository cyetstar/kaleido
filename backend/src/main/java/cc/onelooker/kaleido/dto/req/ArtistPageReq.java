package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术家请求对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("艺术家请求对象")
public class ArtistPageReq {

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("关键字")
    private String keyword;

    @ApiModelProperty("主键列表，逗号间隔")
    private String ids;

}
