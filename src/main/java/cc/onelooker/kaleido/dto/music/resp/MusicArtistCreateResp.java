package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 艺术家响应对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
@ApiModel("艺术家响应对象")
public class MusicArtistCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ApiModelProperty("Plex编号")
    private String plexId;

    @ApiModelProperty("名称")
    private String mc;

    @Dict("ysjlx")
    @ApiModelProperty("艺术家类型")
    private String ysjlx;

    @ApiModelProperty("国家地区")
    private String gjdq;

    @ApiModelProperty("简介")
    private String jj;
}
