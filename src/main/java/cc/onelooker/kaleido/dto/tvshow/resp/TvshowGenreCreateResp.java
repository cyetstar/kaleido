package cc.onelooker.kaleido.dto.tvshow.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 剧集类型响应对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集类型响应对象")
public class TvshowGenreCreateResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标识")
    private String tag;
}
