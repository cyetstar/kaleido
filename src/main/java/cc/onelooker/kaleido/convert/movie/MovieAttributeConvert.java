package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieAttributeDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributeCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributeUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributeCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributeViewResp;
import cc.onelooker.kaleido.entity.movie.MovieAttributeDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影属性值Convert
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Mapper
public interface MovieAttributeConvert {

    MovieAttributeConvert INSTANCE = Mappers.getMapper(MovieAttributeConvert.class);

    MovieAttributeDTO convert(MovieAttributeDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieAttributeDO convertToDO(MovieAttributeDTO dto);

    MovieAttributeDTO convertToDTO(MovieAttributePageReq req);

    MovieAttributeDTO convertToDTO(MovieAttributeCreateReq req);

    MovieAttributeDTO convertToDTO(MovieAttributeUpdateReq req);

    MovieAttributePageResp convertToPageResp(MovieAttributeDTO dto);

    MovieAttributeViewResp convertToViewResp(MovieAttributeDTO dto);

    MovieAttributeCreateResp convertToCreateResp(MovieAttributeDTO dto);

}