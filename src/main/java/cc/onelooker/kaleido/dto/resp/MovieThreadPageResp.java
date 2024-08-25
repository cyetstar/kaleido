package cc.onelooker.kaleido.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 电影发布记录响应对象
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Data
@ApiModel("电影发布记录响应对象")
public class MovieThreadPageResp {

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
