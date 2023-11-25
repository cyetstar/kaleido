package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieBasicLanguageDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicLanguageDO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicLanguagePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicLanguageCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicLanguageUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicLanguagePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicLanguageViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicLanguageCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicLanguageExp;

/**
* 电影语言关联表Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieBasicLanguageConvert {

    MovieBasicLanguageConvert INSTANCE = Mappers.getMapper(MovieBasicLanguageConvert.class);

    MovieBasicLanguageDTO convert(MovieBasicLanguageDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieBasicLanguageDO convertToDO(MovieBasicLanguageDTO dto);

    MovieBasicLanguageDTO convertToDTO(MovieBasicLanguagePageReq req);

    MovieBasicLanguageDTO convertToDTO(MovieBasicLanguageCreateReq req);

    MovieBasicLanguageDTO convertToDTO(MovieBasicLanguageUpdateReq req);

    MovieBasicLanguagePageResp convertToPageResp(MovieBasicLanguageDTO dto);

    MovieBasicLanguageViewResp convertToViewResp(MovieBasicLanguageDTO dto);

    MovieBasicLanguageCreateResp convertToCreateResp(MovieBasicLanguageDTO dto);

    MovieBasicLanguageExp convertToExp(MovieBasicLanguageDTO dto);

}