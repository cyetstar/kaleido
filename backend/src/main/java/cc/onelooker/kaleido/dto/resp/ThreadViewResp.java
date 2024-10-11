package cc.onelooker.kaleido.dto.resp;

import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 发布记录响应对象
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Data
@ApiModel("发布记录响应对象")
public class ThreadViewResp {

    @ApiModelProperty("主键")
    private String id;

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

    @ApiModelProperty("番组计划编号")
    private String bgmId;

    @ApiModelProperty("状态")
    private String status;

    private String movieBasicId;

    private String movieBasicTitle;

    private String movieBasicOriginalTitle;

    private String movieBasicPath;

    private String comicSeriesId;

    private String comicSeriesTitle;

    private String comicSeriesOriginalTitle;

    private String comicSeriesPath;

    private List<ThreadViewResp> threadList;

}
