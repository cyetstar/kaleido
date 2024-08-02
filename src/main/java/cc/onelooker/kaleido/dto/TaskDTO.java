package cc.onelooker.kaleido.dto;

import lombok.Data;
import com.zjjcnt.common.core.dto.BaseDTO;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Dict;
import java.lang.String;

/**
 * 任务DTO
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 * @see cc.onelooker.kaleido.entity.TaskDO
 */
@Data
public class TaskDTO implements BaseDTO<String> {
    private static final long serialVersionUID = 8234678698472504094L;

    /**
     * 主键
     */
    private String id;

    /**
     * 项目id
     */
    private String subjectId;

    /**
     * 项目类型
     */
    private String subjectType;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 加入时间
     */
    private Long addedAt;

    /**
     * 任务状态
     */
    private String taskStatus;

    // ------ 非数据库表字段 -------


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
