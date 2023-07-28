package cc.onelooker.kaleido.dto.business.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件请求对象
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 *
 */
@Data
@ApiModel("电影文件请求对象")
public class MovieFileCreateReq{

    @ApiModelProperty("电影id")
    private Long movieId;

    @ApiModelProperty("文件名")
    private String wjm;

    @ApiModelProperty("文件路径")
    private String wjlj;

    @ApiModelProperty("文件大小")
    private Integer wjdx;
}