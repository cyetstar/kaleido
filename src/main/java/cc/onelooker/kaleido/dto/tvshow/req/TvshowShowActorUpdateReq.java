package cc.onelooker.kaleido.dto.tvshow.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 剧集演职员关联表请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 *
 */
@Data
@ApiModel("剧集演职员关联表请求对象")
public class TvshowShowActorUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("剧集id")
    private Long showId;

    @ApiModelProperty("演职员id")
    private Long actorId;

    @ApiModelProperty("角色")
    private String role;

}
