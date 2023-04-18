package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;

/**
 * 影片类型响应对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("影片类型响应对象")
public class MovieGenreCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("名称")
    private String mc;
}
