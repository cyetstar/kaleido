package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 艺术家响应对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 *
 */
@Data
@ApiModel("艺术家响应对象")
public class MusicArtistViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

    @ApiModelProperty("国家地区")
    private String area;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("排序名称")
    private String titleSort;

    @ApiModelProperty("封面图")
    private String thumb;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @ApiModelProperty("更新时间")
    private Long updatedAt;
}