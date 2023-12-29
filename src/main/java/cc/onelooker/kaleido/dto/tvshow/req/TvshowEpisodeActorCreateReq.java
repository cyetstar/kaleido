package cc.onelooker.kaleido.dto.tvshow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单集演职员关联表请求对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("单集演职员关联表请求对象")
public class TvshowEpisodeActorCreateReq {

    @ApiModelProperty("单集id")
    private Long episodeId;

    @ApiModelProperty("演职员id")
    private Long actorId;

    @ApiModelProperty("角色")
    private String role;

}
