package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件视频信息请求对象
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 *
 */
@Data
@ApiModel("电影文件视频信息请求对象")
public class MovieFileVideoUpdateReq{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("电影文件id")
    private Long movieFileId;

    @ApiModelProperty("编解码器")
    private String codec;

    @ApiModelProperty("画幅比例")
    private String aspect;

    @ApiModelProperty("宽")
    private Integer width;

    @ApiModelProperty("高")
    private Integer height;

    @ApiModelProperty("时长")
    private Integer durationinseconds;

    @ApiModelProperty("立体声模式")
    private String stereomode;
}
