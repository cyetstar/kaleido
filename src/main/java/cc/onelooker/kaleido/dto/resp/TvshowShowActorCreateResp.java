package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 剧集演职员关联表响应对象
 *
 * @author cyetstar
 * @date 2023-11-27 22:51:36
 */
@Data
@ApiModel("剧集演职员关联表响应对象")
public class TvshowShowActorCreateResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("剧集id")
    private Long showId;

    @ApiModelProperty("演职员id")
    private Long actorId;

    @ApiModelProperty("角色")
    private String role;
}
