package cc.onelooker.kaleido.dto.system.resp;

import com.zjjcnt.common.core.annotation.DictSzbm;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 响应对象
 *
 * @author chenlong
 * @date 2023-01-13 18:30:48
 */
@Data
@ApiModel("响应对象")
public class FwtDmWgViewResp {

    @ApiModelProperty("")
    private String dmzm;

    @ApiModelProperty("")
    private String dmmc;

    @ApiModelProperty("")
    private String wgjj;

    @DictSzbm
    @ApiModelProperty("")
    private String ssxq;

    @ApiModelProperty("")
    private String bz;

    @ApiModelProperty("")
    private BigDecimal x;

    @ApiModelProperty("")
    private BigDecimal y;

    @ApiModelProperty("")
    private BigDecimal z;

    @ApiModelProperty("")
    private BigDecimal minx;

    @ApiModelProperty("")
    private BigDecimal miny;

    @ApiModelProperty("")
    private BigDecimal maxx;

    @ApiModelProperty("")
    private BigDecimal maxy;

    @StringDateTimeFormat
    @ApiModelProperty("")
    private String clsj;

    @ApiModelProperty("有效标志")
    private String yxbz;
}
