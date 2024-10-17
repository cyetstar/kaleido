package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 艺术家响应对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("艺术家响应对象")
public class ArtistPageResp {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("MusicBrainz编号")
    private String musicbrainzId;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("排序名称")
    private String titleSort;

}
