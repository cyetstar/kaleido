package cc.onelooker.kaleido.dto.comic.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 漫画书籍作者关联表请求对象
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 *
 */
@Data
@ApiModel("漫画书籍作者关联表请求对象")
public class ComicBookAuthorUpdateReq{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("书籍id")
    private String bookId;

    @ApiModelProperty("作者id")
    private String authorId;

    @ApiModelProperty("角色")
    private String role;

}
