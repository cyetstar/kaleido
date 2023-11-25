package cc.onelooker.kaleido.dto.movie.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件字幕信息响应对象
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 *
 */
@Data
@ApiModel("电影文件字幕信息响应对象")
public class MovieFileSubtitlePageResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影文件id")
    private Long movieFileId;

    @ApiModelProperty("语言")
    private String lanuage;
}