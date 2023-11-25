package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;
import cc.onelooker.kaleido.entity.movie.MovieGenreDO;
import cc.onelooker.kaleido.dto.movie.req.MovieGenrePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieGenreCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieGenreUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenrePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenreViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenreCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieGenreExp;

/**
* 电影类型Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieGenreConvert {

    MovieGenreConvert INSTANCE = Mappers.getMapper(MovieGenreConvert.class);

    MovieGenreDTO convert(MovieGenreDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieGenreDO convertToDO(MovieGenreDTO dto);

    MovieGenreDTO convertToDTO(MovieGenrePageReq req);

    MovieGenreDTO convertToDTO(MovieGenreCreateReq req);

    MovieGenreDTO convertToDTO(MovieGenreUpdateReq req);

    MovieGenrePageResp convertToPageResp(MovieGenreDTO dto);

    MovieGenreViewResp convertToViewResp(MovieGenreDTO dto);

    MovieGenreCreateResp convertToCreateResp(MovieGenreDTO dto);

    MovieGenreExp convertToExp(MovieGenreDTO dto);

}