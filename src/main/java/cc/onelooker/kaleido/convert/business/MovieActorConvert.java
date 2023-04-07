package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieActorDTO;
import cc.onelooker.kaleido.entity.business.MovieActorDO;
import cc.onelooker.kaleido.dto.business.req.MovieActorPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieActorCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieActorUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieActorPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieActorViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieActorCreateResp;
import cc.onelooker.kaleido.dto.business.exp.MovieActorExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface MovieActorConvert {

    MovieActorConvert INSTANCE = Mappers.getMapper(MovieActorConvert.class);

    MovieActorDTO convert(MovieActorDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieActorDO convertToDO(MovieActorDTO dto);

    MovieActorDTO convertToDTO(MovieActorPageReq req);

    MovieActorDTO convertToDTO(MovieActorCreateReq req);

    MovieActorDTO convertToDTO(MovieActorUpdateReq req);

    MovieActorPageResp convertToPageResp(MovieActorDTO dto);

    MovieActorViewResp convertToViewResp(MovieActorDTO dto);

    MovieActorCreateResp convertToCreateResp(MovieActorDTO dto);

    MovieActorExp convertToExp(MovieActorDTO dto);

}