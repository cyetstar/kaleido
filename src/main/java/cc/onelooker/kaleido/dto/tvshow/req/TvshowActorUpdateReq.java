package cc.onelooker.kaleido.dto.tvshow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 剧集演职员请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集演职员请求对象")
public class TvshowActorUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("原名")
    private String originalName;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

}
