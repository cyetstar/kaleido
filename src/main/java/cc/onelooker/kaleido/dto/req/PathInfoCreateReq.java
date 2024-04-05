package cc.onelooker.kaleido.dto.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.String;

/**
 * 目录信息请求对象
 *
 * @author cyetstar
 * @date 2024-03-23 23:08:41
 *
 */
@Data
@ApiModel("目录信息请求对象")
public class PathInfoCreateReq{

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("TMDB编号")
    private String tmdbId;

    @ApiModelProperty("番组计划编号")
    private String bgmId;

}
