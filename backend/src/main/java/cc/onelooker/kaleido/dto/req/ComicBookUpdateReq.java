package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 漫画书籍请求对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */
@Data
@ApiModel("漫画书籍请求对象")
public class ComicBookUpdateReq {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("原标题")
    private String originalTitle;

    @ApiModelProperty("卷号")
    private Integer bookNumber;

    @ApiModelProperty("排序")
    private Integer sortNumber;

    @ApiModelProperty("番组计划编号")
    private String bgmId;

}
