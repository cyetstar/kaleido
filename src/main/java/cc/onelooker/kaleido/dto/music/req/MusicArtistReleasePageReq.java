package cc.onelooker.kaleido.dto.music.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;

/**
 * 艺术家发行品关联表请求对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
@ApiModel("艺术家发行品关联表请求对象")
public class MusicArtistReleasePageReq{

    @ApiModelProperty("艺术家id")
    private Long artistId;

    @ApiModelProperty("发行品id")
    private Long releaseId;
}
