package cc.onelooker.kaleido.dto.music.resp;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import java.lang.Integer;
import java.util.List;

import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 发行品响应对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 *
 */
@Data
@ApiModel("发行品响应对象")
public class MusicReleaseViewResp{

    @ApiModelProperty("主键")
    private Long id;

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

    @ApiModelProperty("专辑类型")
    private String zjlx;

    @ApiModelProperty("音乐流派")
    private String yylp;

    @ApiModelProperty("发行国家")
    private String fxgj;

    @ApiModelProperty("日期")
    private String rq;

    @ApiModelProperty("唱片公司")
    private String cpgs;

    @ApiModelProperty("首发日期")
    private String sfrq;

    @ApiModelProperty("碟数")
    private Integer zds;

    @ApiModelProperty("音轨数")
    private Integer ygs;

    @ApiModelProperty("媒体")
    private String mt;

    @ApiModelProperty("简介")
    private String jj;

    @ApiModelProperty("Plex缩略图")
    private String plexThumb;

    @Dict("sfqs")
    @ApiModelProperty("是否缺损")
    private String sfqs;

    @ApiModelProperty("文件路径")
    private String wjlj;

    private List<MusicArtistDTO> musicArtistDTOList;
}
