package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.ActorDTO;
import cc.onelooker.kaleido.entity.business.ActorDO;
import cc.onelooker.kaleido.dto.business.req.ActorPageReq;
import cc.onelooker.kaleido.dto.business.req.ActorCreateReq;
import cc.onelooker.kaleido.dto.business.req.ActorUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.ActorPageResp;
import cc.onelooker.kaleido.dto.business.resp.ActorViewResp;
import cc.onelooker.kaleido.dto.business.resp.ActorCreateResp;
import cc.onelooker.kaleido.dto.business.exp.ActorExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface ActorConvert {

    ActorConvert INSTANCE = Mappers.getMapper(ActorConvert.class);

    ActorDTO convert(ActorDO entity);

    @InheritInverseConfiguration(name="convert")
    ActorDO convertToDO(ActorDTO dto);

    ActorDTO convertToDTO(ActorPageReq req);

    ActorDTO convertToDTO(ActorCreateReq req);

    ActorDTO convertToDTO(ActorUpdateReq req);

    ActorPageResp convertToPageResp(ActorDTO dto);

    ActorViewResp convertToViewResp(ActorDTO dto);

    ActorCreateResp convertToCreateResp(ActorDTO dto);

    ActorExp convertToExp(ActorDTO dto);

}