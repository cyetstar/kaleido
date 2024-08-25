package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.enums.SubjectType;
import cc.onelooker.kaleido.enums.TaskType;
import com.zjjcnt.common.core.service.IBaseService;

/**
 * 任务Service
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 */
public interface TaskService extends IBaseService<TaskDTO> {

    void newTask(String subjectId, SubjectType subjectType, TaskType taskType);

    void updateTaskStatus(String id, String taskStatus);
}