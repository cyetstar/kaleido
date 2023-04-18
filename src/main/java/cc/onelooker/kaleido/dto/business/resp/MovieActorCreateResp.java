package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Crypto;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 演职员响应对象
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 *
 */
@Data
@ApiModel("演职员响应对象")
public class MovieActorCreateResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("姓名")
    private String xm;

    @ApiModelProperty("本名")
    private String bm;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;
}
