package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;
import cc.onelooker.kaleido.dto.movie.exp.MovieAkaExp;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaViewResp;
import cc.onelooker.kaleido.entity.movie.MovieAkaDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 别名Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
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