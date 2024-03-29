package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieBasicCountryDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCountryCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCountryPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCountryUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCountryCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCountryPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCountryViewResp;
import cc.onelooker.kaleido.entity.movie.MovieBasicCountryDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影国家地区关联表Convert
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieBasicCountryConvert {

    MovieBasicCountryConvert INSTANCE = Mappers.getMapper(MovieBasicCountryConvert.class);

    MovieBasicCountryDTO convert(MovieBasicCountryDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieBasicCountryDO convertToDO(MovieBasicCountryDTO dto);

    MovieBasicCountryDTO convertToDTO(MovieBasicCountryPageReq req);

    MovieBasicCountryDTO convertToDTO(MovieBasicCountryCreateReq req);

    MovieBasicCountryDTO convertToDTO(MovieBasicCountryUpdateReq req);

    MovieBasicCountryPageResp convertToPageResp(MovieBasicCountryDTO dto);

    MovieBasicCountryViewResp convertToViewResp(MovieBasicCountryDTO dto);

    MovieBasicCountryCreateResp convertToCreateResp(MovieBasicCountryDTO dto);

}