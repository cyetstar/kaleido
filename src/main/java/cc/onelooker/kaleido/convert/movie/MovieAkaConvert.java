package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;
import cc.onelooker.kaleido.entity.movie.MovieAkaDO;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieAkaExp;

/**
* 别名Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieAkaConvert {

    MovieAkaConvert INSTANCE = Mappers.getMapper(MovieAkaConvert.class);

    MovieAkaDTO convert(MovieAkaDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieAkaDO convertToDO(MovieAkaDTO dto);

    MovieAkaDTO convertToDTO(MovieAkaPageReq req);

    MovieAkaDTO convertToDTO(MovieAkaCreateReq req);

    MovieAkaDTO convertToDTO(MovieAkaUpdateReq req);

    MovieAkaPageResp convertToPageResp(MovieAkaDTO dto);

    MovieAkaViewResp convertToViewResp(MovieAkaDTO dto);

    MovieAkaCreateResp convertToCreateResp(MovieAkaDTO dto);

    MovieAkaExp convertToExp(MovieAkaDTO dto);

}