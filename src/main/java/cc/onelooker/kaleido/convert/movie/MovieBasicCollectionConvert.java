package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionViewResp;
import cc.onelooker.kaleido.entity.movie.MovieBasicCollectionDO;
import cc.onelooker.kaleido.exp.movie.MovieBasicCollectionExp;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
* 电影集合关联表Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieBasicCollectionConvert {

    MovieBasicCollectionConvert INSTANCE = Mappers.getMapper(MovieBasicCollectionConvert.class);

    MovieBasicCollectionDTO convert(MovieBasicCollectionDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieBasicCollectionDO convertToDO(MovieBasicCollectionDTO dto);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionPageReq req);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionCreateReq req);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionUpdateReq req);

    MovieBasicCollectionPageResp convertToPageResp(MovieBasicCollectionDTO dto);

    MovieBasicCollectionViewResp convertToViewResp(MovieBasicCollectionDTO dto);

    MovieBasicCollectionCreateResp convertToCreateResp(MovieBasicCollectionDTO dto);

    MovieBasicCollectionExp convertToExp(MovieBasicCollectionDTO dto);

}