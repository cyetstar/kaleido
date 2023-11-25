package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicGenreDO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenrePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenreCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenreUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenrePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenreViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenreCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicGenreExp;

/**
* 电影类型关联表Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieBasicGenreConvert {

    MovieBasicGenreConvert INSTANCE = Mappers.getMapper(MovieBasicGenreConvert.class);

    MovieBasicGenreDTO convert(MovieBasicGenreDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieBasicGenreDO convertToDO(MovieBasicGenreDTO dto);

    MovieBasicGenreDTO convertToDTO(MovieBasicGenrePageReq req);

    MovieBasicGenreDTO convertToDTO(MovieBasicGenreCreateReq req);

    MovieBasicGenreDTO convertToDTO(MovieBasicGenreUpdateReq req);

    MovieBasicGenrePageResp convertToPageResp(MovieBasicGenreDTO dto);

    MovieBasicGenreViewResp convertToViewResp(MovieBasicGenreDTO dto);

    MovieBasicGenreCreateResp convertToCreateResp(MovieBasicGenreDTO dto);

    MovieBasicGenreExp convertToExp(MovieBasicGenreDTO dto);

}