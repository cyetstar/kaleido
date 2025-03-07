package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发布记录请求对象
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Data
@ApiModel("发布记录请求对象")
public class ThreadPageReq {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("链接")
    private String url;

    @ApiModelProperty("站点")
    private String website;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @ApiModelProperty("IMDb编号")
    private String imdbId;

    @ApiModelProperty("状态")
    private String status;

}
