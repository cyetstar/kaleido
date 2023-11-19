package cc.onelooker.kaleido.dto.music.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 曲目响应对象
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Data
@ApiModel("曲目响应对象")
public class MusicTrackListByReleaseIdResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String bt;

    @ApiModelProperty("曲长")
    private Integer qc;

    @ApiModelProperty("曲号")
    private Integer qh;

    @ApiModelProperty("碟号")
    private Integer dh;

    @ApiModelProperty("是否有歌词")
    private String sfygc;

    @JsonProperty
    public String qcLabel() {
        //将秒转分秒格式
        if (qc != null) {
            return String.format("%d:%02d", qc / 60, qc % 60);
        }
        return null;
    }

}
