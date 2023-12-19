package cc.onelooker.kaleido.dto.music.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术家专辑关联表响应对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 *
 */
@Data
@ApiModel("艺术家专辑关联表响应对象")
public class MusicArtistAlbumCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("艺术家id")
    private Long artistId;

    @ApiModelProperty("专辑id")
    private Long albumId;
}
