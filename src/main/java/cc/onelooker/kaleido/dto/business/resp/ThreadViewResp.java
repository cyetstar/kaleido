package cc.onelooker.kaleido.dto.business.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * 响应对象
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 *
 */
@Data
@ApiModel("响应对象")
public class ThreadViewResp{

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private Date createdAt;

    @ApiModelProperty("")
    private String doubanId;

    @ApiModelProperty("")
    private String imdb;

    @ApiModelProperty("")
    private String links;

    @ApiModelProperty("")
    private String publishDate;

    @ApiModelProperty("")
    private Double rating;

    @ApiModelProperty("")
    private Integer status;

    @ApiModelProperty("")
    private Boolean thanks;

    @ApiModelProperty("")
    private String title;

    @ApiModelProperty("")
    private String type;

    @ApiModelProperty("")
    private Date updatedAt;

    @ApiModelProperty("")
    private String url;
}
