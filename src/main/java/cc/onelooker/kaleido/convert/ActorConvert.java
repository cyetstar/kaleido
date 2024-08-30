package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.dto.req.ActorCreateReq;
import cc.onelooker.kaleido.dto.req.ActorPageReq;
import cc.onelooker.kaleido.dto.req.ActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.ActorCreateResp;
import cc.onelooker.kaleido.dto.resp.ActorPageResp;
import cc.onelooker.kaleido.dto.resp.ActorViewResp;
import cc.onelooker.kaleido.entity.ActorDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 演职员Convert
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface ActorConvert {

    ActorConvert INSTANCE = Mappers.getMapper(ActorConvert.class);

    ActorDTO convert(ActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    ActorDO convertToDO(ActorDTO dto);

    ActorDTO convertToDTO(ActorPageReq req);

    ActorDTO convertToDTO(ActorCreateReq req);

    ActorDTO convertToDTO(ActorUpdateReq req);

    ActorPageResp convertToPageResp(ActorDTO dto);

    ActorViewResp convertToViewResp(ActorDTO dto);

    ActorCreateResp convertToCreateResp(ActorDTO dto);

}