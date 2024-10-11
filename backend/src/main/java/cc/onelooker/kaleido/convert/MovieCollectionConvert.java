package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MovieCollectionDTO;
import cc.onelooker.kaleido.dto.req.MovieCollectionCreateReq;
import cc.onelooker.kaleido.dto.req.MovieCollectionPageReq;
import cc.onelooker.kaleido.dto.req.MovieCollectionSyncReq;
import cc.onelooker.kaleido.dto.resp.MovieCollectionCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieCollectionListByMovieIdResp;
import cc.onelooker.kaleido.dto.resp.MovieCollectionPageResp;
import cc.onelooker.kaleido.dto.resp.MovieCollectionViewResp;
import cc.onelooker.kaleido.entity.MovieCollectionDO;
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

    MovieCollectionDTO convertToDTO(MovieCollectionSyncReq req);

    MovieCollectionPageResp convertToPageResp(MovieCollectionDTO dto);

    MovieCollectionViewResp convertToViewResp(MovieCollectionDTO dto);

    MovieCollectionCreateResp convertToCreateResp(MovieCollectionDTO dto);

    MovieCollectionListByMovieIdResp convertToListByMovieIdResp(MovieCollectionDTO dto);
}