package cc.onelooker.kaleido.dto.system.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作日志表响应对象
 *
 * @author weed
 * @date 2023-02-18 09:30:33
 *
 */
@Data
@ApiModel("操作日志表响应对象")
public class WebLogCreateResp{

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("操作描述")
    private String description;

    @ApiModelProperty("操作用户")
    private String username;

    @ApiModelProperty("操作时间")
    private Long startTime;

    @ApiModelProperty("消耗时间")
    private Integer spendTime;

    @ApiModelProperty("根路径")
    private String basePath;

    @ApiModelProperty("请求参数")
    private String parameter;

    @ApiModelProperty("URI")
    private String uri;

    @ApiModelProperty("URL")
    private String url;

    @ApiModelProperty("请求类型")
    private String method;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("创建人辖区")
    private String cjrxq;

    @ApiModelProperty("修改人辖区")
    private String xgrxq;
}
