package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.ResumeDTO;
import cc.onelooker.kaleido.entity.business.ResumeDO;
import cc.onelooker.kaleido.dto.business.req.ResumePageReq;
import cc.onelooker.kaleido.dto.business.req.ResumeCreateReq;
import cc.onelooker.kaleido.dto.business.req.ResumeUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.ResumePageResp;
import cc.onelooker.kaleido.dto.business.resp.ResumeViewResp;
import cc.onelooker.kaleido.dto.business.resp.ResumeCreateResp;
import cc.onelooker.kaleido.dto.business.exp.ResumeExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface ResumeConvert {

    ResumeConvert INSTANCE = Mappers.getMapper(ResumeConvert.class);

    ResumeDTO convert(ResumeDO entity);

    @InheritInverseConfiguration(name="convert")
    ResumeDO convertToDO(ResumeDTO dto);

    ResumeDTO convertToDTO(ResumePageReq req);

    ResumeDTO convertToDTO(ResumeCreateReq req);

    ResumeDTO convertToDTO(ResumeUpdateReq req);

    ResumePageResp convertToPageResp(ResumeDTO dto);

    ResumeViewResp convertToViewResp(ResumeDTO dto);

    ResumeCreateResp convertToCreateResp(ResumeDTO dto);

    ResumeExp convertToExp(ResumeDTO dto);

}