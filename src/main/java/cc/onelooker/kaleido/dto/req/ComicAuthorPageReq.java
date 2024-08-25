package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 漫画作者请求对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 *
 */
@Data
@ApiModel("漫画作者请求对象")
public class ComicAuthorPageReq{

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("关键字")
    private String keyword;

}
