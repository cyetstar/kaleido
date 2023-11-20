package cc.onelooker.kaleido.dto.music.resp;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * 专辑响应对象
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 *
 */
@Data
@ApiModel("专辑响应对象")
public class MusicAlbumViewResp{

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

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("艺术家")
    private String artists;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("专辑类型")
    private String type;

    @ApiModelProperty("音乐流派")
    private String genre;

    @ApiModelProperty("发行国家")
    private String releaseCountry;

    @ApiModelProperty("日期")
    private String date;

    @ApiModelProperty("唱片公司")
    private String label;

    @ApiModelProperty("发行日期")
    private String releaseDate;

    @ApiModelProperty("碟数")
    private Integer totalDiscs;

    @ApiModelProperty("音轨数")
    private Integer totalTracks;

    @ApiModelProperty("媒体")
    private String media;

    @ApiModelProperty("文件路径")
    private String path;

    private List<MusicArtistDTO> musicArtistDTOList;
}
