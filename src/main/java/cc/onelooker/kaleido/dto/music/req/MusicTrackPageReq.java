package cc.onelooker.kaleido.dto.music.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 曲目请求对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
@ApiModel("曲目请求对象")
public class MusicTrackPageReq{

    @ApiModelProperty("发行品id")
    private Long releaseId;

    @ApiModelProperty("MusicBrainzId")
    private String musicbrainzId;

    @ApiModelProperty("Plex编号")
    private String plexId;

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

    @ApiModelProperty("文件格式")
    private String wjgs;

    @ApiModelProperty("文件路径")
    private String wjlj;

    @ApiModelProperty("是否有歌词")
    private String sfygc;

    @ApiModelProperty("是否缺损")
    private String sfqs;

    @StringDateTimeFormat
    @ApiModelProperty("创建时间")
    private String cjsj;

    @StringDateTimeFormat
    @ApiModelProperty("修改时间")
    private String xgsj;
}
