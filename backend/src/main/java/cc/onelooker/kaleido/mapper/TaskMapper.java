package cc.onelooker.kaleido.mapper;

import cc.onelooker.kaleido.entity.TaskDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 任务Mapper接口
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 */
@Mapper
public interface TaskMapper extends BaseMapper<TaskDO> {

    void deleteNotExistRecords(@Param("tableName") String tableName, @Param("subjectType") String subjectType);
}