package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-04-15 20:37:00
 * @Description TODO
 */
@Data
public class ComicBookClearCoverReq {

    @ApiModelProperty("主键")
    private String id;
}
