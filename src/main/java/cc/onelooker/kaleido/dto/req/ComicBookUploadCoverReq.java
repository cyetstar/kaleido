package cc.onelooker.kaleido.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xiadawei
 * @Date 2024-04-15 20:37:00
 * @Description TODO
 */
@Data
public class ComicBookUploadCoverReq {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("base64数据")
    private String data;

    @ApiModelProperty("封面页码")
    private Integer coverPageNumber;

    @ApiModelProperty("封面裁切数据")
    private String coverBoxData;
}
