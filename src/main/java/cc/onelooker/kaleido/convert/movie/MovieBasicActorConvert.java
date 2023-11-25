package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicActorDO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicActorExp;

/**
* 电影演职员关联表Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieBasicActorConvert {

    MovieBasicActorConvert INSTANCE = Mappers.getMapper(MovieBasicActorConvert.class);

    MovieBasicActorDTO convert(MovieBasicActorDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieBasicActorDO convertToDO(MovieBasicActorDTO dto);

    MovieBasicActorDTO convertToDTO(MovieBasicActorPageReq req);

    MovieBasicActorDTO convertToDTO(MovieBasicActorCreateReq req);

    MovieBasicActorDTO convertToDTO(MovieBasicActorUpdateReq req);

    MovieBasicActorPageResp convertToPageResp(MovieBasicActorDTO dto);

    MovieBasicActorViewResp convertToViewResp(MovieBasicActorDTO dto);

    MovieBasicActorCreateResp convertToCreateResp(MovieBasicActorDTO dto);

    MovieBasicActorExp convertToExp(MovieBasicActorDTO dto);

}