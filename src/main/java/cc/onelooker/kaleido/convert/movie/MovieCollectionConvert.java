package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieCollectionUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionListByMovieIdResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieCollectionViewResp;
import cc.onelooker.kaleido.entity.movie.MovieCollectionDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 电影集合Convert
*
* @author cyetstar
* @date 2023-12-29 16:15:43
*/
@Mapper
public interface MovieCollectionConvert {

    MovieCollectionConvert INSTANCE = Mappers.getMapper(MovieCollectionConvert.class);

    MovieCollectionDTO convert(MovieCollectionDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieCollectionDO convertToDO(MovieCollectionDTO dto);

    MovieCollectionDTO convertToDTO(MovieCollectionPageReq req);

    MovieCollectionDTO convertToDTO(MovieCollectionCreateReq req);

    MovieCollectionDTO convertToDTO(MovieCollectionUpdateReq req);

    MovieCollectionPageResp convertToPageResp(MovieCollectionDTO dto);

    MovieCollectionViewResp convertToViewResp(MovieCollectionDTO dto);

    MovieCollectionCreateResp convertToCreateResp(MovieCollectionDTO dto);

    MovieCollectionListByMovieIdResp convertToListByMovieIdResp(MovieCollectionDTO dto);
}