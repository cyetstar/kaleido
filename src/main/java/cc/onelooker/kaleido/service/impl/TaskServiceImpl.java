package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TaskConvert;
import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.entity.TaskDO;
import cc.onelooker.kaleido.enums.TaskType;
import cc.onelooker.kaleido.mapper.TaskMapper;
import cc.onelooker.kaleido.service.TaskService;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 任务ServiceImpl
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 */
@Service
public class TaskServiceImpl extends AbstractBaseServiceImpl<TaskMapper, TaskDO, TaskDTO> implements TaskService {

    TaskConvert convert = TaskConvert.INSTANCE;

    @Override
    protected Wrapper<TaskDO> genQueryWrapper(TaskDTO dto) {
        LambdaQueryWrapper<TaskDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getSubjectId()), TaskDO::getSubjectId, dto.getSubjectId());
        query.eq(StringUtils.isNotEmpty(dto.getSubjectType()), TaskDO::getSubjectType, dto.getSubjectType());
        query.eq(StringUtils.isNotEmpty(dto.getTaskType()), TaskDO::getTaskType, dto.getTaskType());
        query.eq(StringUtils.isNotEmpty(dto.getTaskStatus()), TaskDO::getTaskStatus, dto.getTaskStatus());
        return query;
    }

    @Override
    public TaskDTO convertToDTO(TaskDO taskDO) {
        return convert.convert(taskDO);
    }

    @Override
    public TaskDO convertToDO(TaskDTO taskDTO) {
        return convert.convertToDO(taskDTO);
    }

    @Override
    @Transactional
    public void newTask(String subjectId, String subjectType, TaskType taskType) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setSubjectId(subjectId);
        taskDTO.setSubjectType(subjectType);
        taskDTO.setTaskType(taskType.name());
        taskDTO.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        TaskDTO existTaskDTO = find(taskDTO);
        if (Objects.nonNull(existTaskDTO)) {
            return;
        }
        taskDTO.setTaskStatus(KaleidoConstants.TASK_STATUS_TODO);
        taskDTO.setAddedAt(System.currentTimeMillis() / 1000L);
        insert(taskDTO);
    }

    @Override
    public void updateTaskStatus(String id, String taskStatus) {
        TaskDO taskDO = new TaskDO();
        taskDO.setId(id);
        taskDO.setTaskStatus(taskStatus);
        updateById(taskDO);
    }

}