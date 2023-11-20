package cc.onelooker.kaleido.dto.music.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家请求对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
@ApiModel("艺术家请求对象")
public class MusicArtistUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ApiModelProperty("Plex编号")
    private String plexId;

    @ApiModelProperty("Plex缩略图")
    private String plexThumb;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("国家地区")
    private String area;

    @ApiModelProperty("简介")
    private String summary;

}
