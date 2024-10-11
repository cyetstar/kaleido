package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 曲目请求对象
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Data
@ApiModel("曲目请求对象")
public class MusicTrackDownloadLyricReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("网易云音乐编号")
    private String neteaseId;

}
