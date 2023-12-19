package cc.onelooker.kaleido.dto.tvshow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 剧集类型请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
@ApiModel("剧集类型请求对象")
public class TvshowGenreCreateReq{

    @ApiModelProperty("标识")
    private String tag;

}
