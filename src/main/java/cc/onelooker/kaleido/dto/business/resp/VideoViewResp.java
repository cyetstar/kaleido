package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 响应对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("响应对象")
public class VideoViewResp{

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String aspect;

    @ApiModelProperty("")
    private String codec;

    @ApiModelProperty("")
    private String durationinseconds;

    @ApiModelProperty("")
    private String height;

    @ApiModelProperty("")
    private String stereomode;

    @ApiModelProperty("")
    private String width;

    @ApiModelProperty("")
    private Long fileinfoId;
}
