package cc.onelooker.kaleido.dto.movie.req;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影集合请求对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影集合请求对象")
public class MovieCollectionCreateReq{

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

}
