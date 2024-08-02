package cc.onelooker.kaleido.dto.resp;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;

/**
 * 任务响应对象
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 *
 */
@Data
@ApiModel("任务响应对象")
public class TaskCreateResp{

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("项目id")
    private String subjectId;

    @ApiModelProperty("项目类型")
    private String subjectType;

    @ApiModelProperty("任务类型")
    private String taskType;

    @ApiModelProperty("加入时间")
    private Long addedAt;

    @Dict("taskStatus")
    @ApiModelProperty("任务状态")
    private String taskStatus;
}
