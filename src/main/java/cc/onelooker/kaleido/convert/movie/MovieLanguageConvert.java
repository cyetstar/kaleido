package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieLanguageDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieLanguageCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieLanguagePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieLanguageUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieLanguageCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieLanguagePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieLanguageViewResp;
import cc.onelooker.kaleido.entity.movie.MovieLanguageDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 语言Convert
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieLanguageConvert {

    MovieLanguageConvert INSTANCE = Mappers.getMapper(MovieLanguageConvert.class);

    MovieLanguageDTO convert(MovieLanguageDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieLanguageDO convertToDO(MovieLanguageDTO dto);

    MovieLanguageDTO convertToDTO(MovieLanguagePageReq req);

    MovieLanguageDTO convertToDTO(MovieLanguageCreateReq req);

    MovieLanguageDTO convertToDTO(MovieLanguageUpdateReq req);

    MovieLanguagePageResp convertToPageResp(MovieLanguageDTO dto);

    MovieLanguageViewResp convertToViewResp(MovieLanguageDTO dto);

    MovieLanguageCreateResp convertToCreateResp(MovieLanguageDTO dto);

}