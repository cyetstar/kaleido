package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieActorDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieActorCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieActorPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieActorUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorViewResp;
import cc.onelooker.kaleido.entity.movie.MovieActorDO;
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
public interface MovieActorConvert {

    MovieActorConvert INSTANCE = Mappers.getMapper(MovieActorConvert.class);

    MovieActorDTO convert(MovieActorDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieActorDO convertToDO(MovieActorDTO dto);

    MovieActorDTO convertToDTO(MovieActorPageReq req);

    MovieActorDTO convertToDTO(MovieActorCreateReq req);

    MovieActorDTO convertToDTO(MovieActorUpdateReq req);

    MovieActorPageResp convertToPageResp(MovieActorDTO dto);

    MovieActorViewResp convertToViewResp(MovieActorDTO dto);

    MovieActorCreateResp convertToCreateResp(MovieActorDTO dto);

}