package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影集合响应对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("电影集合响应对象")
public class MovieSetViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("名称")
    private String mc;

    @ApiModelProperty("集合说明")
    private String jhsm;
}
