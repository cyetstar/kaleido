package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 电影发布文件请求对象
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 *
 */
@Data
@ApiModel("电影发布文件请求对象")
public class MovieThreadFilenameCreateReq{

    @ApiModelProperty("")
    private String value;

    @ApiModelProperty("")
    private Long threadId;

}
