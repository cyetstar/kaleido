package cc.onelooker.kaleido.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjjcnt.common.core.entity.IdEntity;
import lombok.Data;

/**
 * 任务DO
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 * @see cc.onelooker.kaleido.dto.TaskDTO
 */
@Data
@TableName("task")
public class TaskDO implements IdEntity<String> {
    private static final long serialVersionUID = 4841942742696952046L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 项目id
     */
    @TableField(value = "subject_id")
    private String subjectId;

    /**
     * 项目类型
     */
    @TableField(value = "subject_type")
    private String subjectType;

    /**
     * 项目标题
     */
    @TableField(value = "subject_title")
    private String subjectTitle;

    /**
     * 任务类型
     */
    @TableField(value = "task_type")
    private String taskType;

    /**
     * 加入时间
     */
    @TableField(value = "added_at")
    private Long addedAt;

    /**
     * 任务状态
     */
    @TableField(value = "task_status")
    private String taskStatus;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
