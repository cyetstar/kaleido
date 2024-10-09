package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.dto.req.ThreadCreateReq;
import cc.onelooker.kaleido.dto.req.ThreadPageReq;
import cc.onelooker.kaleido.dto.req.ThreadUpdateReq;
import cc.onelooker.kaleido.dto.resp.ThreadCreateResp;
import cc.onelooker.kaleido.dto.resp.ThreadPageResp;
import cc.onelooker.kaleido.dto.resp.ThreadViewResp;
import cc.onelooker.kaleido.entity.ThreadDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 发布记录Convert
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Mapper
public interface ThreadConvert {

    ThreadConvert INSTANCE = Mappers.getMapper(ThreadConvert.class);

    ThreadDTO convert(ThreadDO entity);

    @InheritInverseConfiguration(name = "convert")
    ThreadDO convertToDO(ThreadDTO dto);

    ThreadDTO convertToDTO(ThreadPageReq req);

    ThreadDTO convertToDTO(ThreadCreateReq req);

    ThreadDTO convertToDTO(ThreadUpdateReq req);

    ThreadPageResp convertToPageResp(ThreadDTO dto);

    ThreadViewResp convertToViewResp(ThreadDTO dto);

    ThreadCreateResp convertToCreateResp(ThreadDTO dto);

}