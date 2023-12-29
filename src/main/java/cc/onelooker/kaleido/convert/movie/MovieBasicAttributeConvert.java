package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicAttributeDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicAttributeCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicAttributePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicAttributeUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicAttributeCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicAttributePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicAttributeViewResp;
import cc.onelooker.kaleido.entity.movie.MovieBasicAttributeDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影属性值关联表Convert
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Mapper
public interface MovieBasicAttributeConvert {

    MovieBasicAttributeConvert INSTANCE = Mappers.getMapper(MovieBasicAttributeConvert.class);

    MovieBasicAttributeDTO convert(MovieBasicAttributeDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieBasicAttributeDO convertToDO(MovieBasicAttributeDTO dto);

    MovieBasicAttributeDTO convertToDTO(MovieBasicAttributePageReq req);

    MovieBasicAttributeDTO convertToDTO(MovieBasicAttributeCreateReq req);

    MovieBasicAttributeDTO convertToDTO(MovieBasicAttributeUpdateReq req);

    MovieBasicAttributePageResp convertToPageResp(MovieBasicAttributeDTO dto);

    MovieBasicAttributeViewResp convertToViewResp(MovieBasicAttributeDTO dto);

    MovieBasicAttributeCreateResp convertToCreateResp(MovieBasicAttributeDTO dto);

}