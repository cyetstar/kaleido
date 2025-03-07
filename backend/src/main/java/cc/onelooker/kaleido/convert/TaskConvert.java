package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.TaskDTO;
import cc.onelooker.kaleido.dto.req.TaskCreateReq;
import cc.onelooker.kaleido.dto.req.TaskPageReq;
import cc.onelooker.kaleido.dto.req.TaskUpdateReq;
import cc.onelooker.kaleido.dto.resp.TaskCreateResp;
import cc.onelooker.kaleido.dto.resp.TaskPageResp;
import cc.onelooker.kaleido.dto.resp.TaskViewResp;
import cc.onelooker.kaleido.entity.TaskDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 任务Convert
 *
 * @author cyetstar
 * @date 2024-06-27 17:18:04
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskConvert {

    TaskConvert INSTANCE = Mappers.getMapper(TaskConvert.class);

    TaskDTO convert(TaskDO entity);

    @InheritInverseConfiguration(name = "convert")
    TaskDO convertToDO(TaskDTO dto);

    TaskDTO convertToDTO(TaskPageReq req);

    TaskDTO convertToDTO(TaskCreateReq req);

    TaskDTO convertToDTO(TaskUpdateReq req);

    TaskPageResp convertToPageResp(TaskDTO dto);

    TaskViewResp convertToViewResp(TaskDTO dto);

    TaskCreateResp convertToCreateResp(TaskDTO dto);

}