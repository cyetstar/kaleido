package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.ThreadDTO;
import cc.onelooker.kaleido.entity.business.ThreadDO;
import cc.onelooker.kaleido.dto.business.req.ThreadPageReq;
import cc.onelooker.kaleido.dto.business.req.ThreadCreateReq;
import cc.onelooker.kaleido.dto.business.req.ThreadUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.ThreadPageResp;
import cc.onelooker.kaleido.dto.business.resp.ThreadViewResp;
import cc.onelooker.kaleido.dto.business.resp.ThreadCreateResp;
import cc.onelooker.kaleido.dto.business.exp.ThreadExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface ThreadConvert {

    ThreadConvert INSTANCE = Mappers.getMapper(ThreadConvert.class);

    ThreadDTO convert(ThreadDO entity);

    @InheritInverseConfiguration(name="convert")
    ThreadDO convertToDO(ThreadDTO dto);

    ThreadDTO convertToDTO(ThreadPageReq req);

    ThreadDTO convertToDTO(ThreadCreateReq req);

    ThreadDTO convertToDTO(ThreadUpdateReq req);

    ThreadPageResp convertToPageResp(ThreadDTO dto);

    ThreadViewResp convertToViewResp(ThreadDTO dto);

    ThreadCreateResp convertToCreateResp(ThreadDTO dto);

    ThreadExp convertToExp(ThreadDTO dto);

}