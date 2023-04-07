package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.StudioDTO;
import cc.onelooker.kaleido.entity.business.StudioDO;
import cc.onelooker.kaleido.dto.business.req.StudioPageReq;
import cc.onelooker.kaleido.dto.business.req.StudioCreateReq;
import cc.onelooker.kaleido.dto.business.req.StudioUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.StudioPageResp;
import cc.onelooker.kaleido.dto.business.resp.StudioViewResp;
import cc.onelooker.kaleido.dto.business.resp.StudioCreateResp;
import cc.onelooker.kaleido.dto.business.exp.StudioExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface StudioConvert {

    StudioConvert INSTANCE = Mappers.getMapper(StudioConvert.class);

    StudioDTO convert(StudioDO entity);

    @InheritInverseConfiguration(name="convert")
    StudioDO convertToDO(StudioDTO dto);

    StudioDTO convertToDTO(StudioPageReq req);

    StudioDTO convertToDTO(StudioCreateReq req);

    StudioDTO convertToDTO(StudioUpdateReq req);

    StudioPageResp convertToPageResp(StudioDTO dto);

    StudioViewResp convertToViewResp(StudioDTO dto);

    StudioCreateResp convertToCreateResp(StudioDTO dto);

    StudioExp convertToExp(StudioDTO dto);

}