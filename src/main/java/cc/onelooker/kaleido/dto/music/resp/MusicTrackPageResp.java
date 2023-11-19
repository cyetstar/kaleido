package cc.onelooker.kaleido.dto.music.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 曲目响应对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
@ApiModel("曲目响应对象")
public class MusicTrackPageResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("发行品id")
    private Long releaseId;

    @ApiModelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ApiModelProperty("Plex编号")
    private String plexId;

    @ApiModelProperty("网易云音乐Id")
    private String neteaseId;

    @ApiModelProperty("标题")
    private String bt;

    @ApiModelProperty("艺术家")
    private String ysj;

    @ApiModelProperty("长度")
    private String cd;

    @ApiModelProperty("曲号")
    private Integer qh;

    @ApiModelProperty("碟号")
    private Integer dh;

    @Dict("wjgs")
    @ApiModelProperty("文件格式")
    private String wjgs;

    @ApiModelProperty("文件路径")
    private String wjlj;

    @Dict("sfygc")
    @ApiModelProperty("是否有歌词")
    private String sfygc;

    @Dict("sfqs")
    @ApiModelProperty("是否缺损")
    private String sfqs;
}
