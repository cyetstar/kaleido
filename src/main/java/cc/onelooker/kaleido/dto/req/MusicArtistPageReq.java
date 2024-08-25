package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术家请求对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("艺术家请求对象")
public class MusicArtistPageReq {

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

    @ApiModelProperty("关键词")
    private String keyword;

}
