package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.zjjcnt.common.core.annotation.Dict;


import java.lang.Long;

/**
 * 艺术家专辑关联表响应对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
@ApiModel("艺术家专辑关联表响应对象")
public class MusicArtistAlbumViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("艺术家id")
    private Long artistId;

    @ApiModelProperty("专辑Id")
    private Long albumId;
}
