package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.req.MovieBasicCollectionCreateReq;
import cc.onelooker.kaleido.dto.req.MovieBasicCollectionPageReq;
import cc.onelooker.kaleido.dto.req.MovieBasicCollectionUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieBasicCollectionCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieBasicCollectionPageResp;
import cc.onelooker.kaleido.dto.resp.MovieBasicCollectionViewResp;
import cc.onelooker.kaleido.entity.MovieBasicCollectionDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 电影集合关联表Convert
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieBasicCollectionConvert {

    MovieBasicCollectionConvert INSTANCE = Mappers.getMapper(MovieBasicCollectionConvert.class);

    MovieBasicCollectionDTO convert(MovieBasicCollectionDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieBasicCollectionDO convertToDO(MovieBasicCollectionDTO dto);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionPageReq req);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionCreateReq req);

    MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionUpdateReq req);

    MovieBasicCollectionPageResp convertToPageResp(MovieBasicCollectionDTO dto);

    MovieBasicCollectionViewResp convertToViewResp(MovieBasicCollectionDTO dto);

    MovieBasicCollectionCreateResp convertToCreateResp(MovieBasicCollectionDTO dto);

}