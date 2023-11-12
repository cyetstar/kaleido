package cc.onelooker.kaleido.dto.music.resp;

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

    @ApiModelProperty("长度")
    private String cd;

    @ApiModelProperty("曲号")
    private Integer qh;

    @ApiModelProperty("碟号")
    private Integer dh;

}
