package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieRatingDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieRatingExp;
import cc.onelooker.kaleido.dto.business.req.MovieRatingCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieRatingPageReq;
import cc.onelooker.kaleido.dto.business.req.MovieRatingUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieRatingCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieRatingPageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieRatingViewResp;
import cc.onelooker.kaleido.entity.business.MovieRatingDO;
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