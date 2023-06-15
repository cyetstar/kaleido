package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件音频信息响应对象
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 *
 */
@Data
@ApiModel("电影文件音频信息响应对象")
public class MovieFileAudioViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影文件id")
    private Long movieFileId;

    @ApiModelProperty("编解码器")
    private String codec;

    @ApiModelProperty("语言")
    private String lanuage;

    @ApiModelProperty("声道")
    private String channels;
}
