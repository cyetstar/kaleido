package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieRatingDTO;
import cc.onelooker.kaleido.dto.movie.exp.MovieRatingExp;
import cc.onelooker.kaleido.dto.movie.req.MovieRatingCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieRatingPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieRatingUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieRatingCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieRatingPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieRatingViewResp;
import cc.onelooker.kaleido.entity.movie.MovieRatingDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 电影评分Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieRatingConvert {

    MovieRatingConvert INSTANCE = Mappers.getMapper(MovieRatingConvert.class);

    MovieRatingDTO convert(MovieRatingDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieRatingDO convertToDO(MovieRatingDTO dto);

    MovieRatingDTO convertToDTO(MovieRatingPageReq req);

    MovieRatingDTO convertToDTO(MovieRatingCreateReq req);

    MovieRatingDTO convertToDTO(MovieRatingUpdateReq req);

    MovieRatingPageResp convertToPageResp(MovieRatingDTO dto);

    MovieRatingViewResp convertToViewResp(MovieRatingDTO dto);

    MovieRatingCreateResp convertToCreateResp(MovieRatingDTO dto);

    MovieRatingExp convertToExp(MovieRatingDTO dto);

}