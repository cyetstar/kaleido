package cc.onelooker.kaleido.dto.movie.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.Arrays;
import java.util.List;

/**
 * 电影集合响应对象
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 *
 */
@Data
@ApiModel("电影集合响应对象")
public class MovieCollectionViewResp{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String summary;

    @ApiModelProperty("海报")
    private String thumb;

    @ApiModelProperty("项目数量")
    private Integer childCount;

    @ApiModelProperty("豆瓣编号")
    private String doubanId;

    @StringDateFormat
    @ApiModelProperty("更新时间")
    private String updateTime;

    @JsonProperty
    public List<String> summaryList() {
        return Arrays.asList(StringUtils.split(summary, "\r"));
    }
}
