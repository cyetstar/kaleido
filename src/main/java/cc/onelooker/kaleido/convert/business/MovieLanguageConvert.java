package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.MovieLanguageDTO;
import cc.onelooker.kaleido.dto.business.exp.MovieLanguageExp;
import cc.onelooker.kaleido.dto.business.req.MovieLanguageCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieLanguagePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieLanguageUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieLanguageCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MovieLanguagePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieLanguageViewResp;
import cc.onelooker.kaleido.entity.business.MovieLanguageDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* 语言Convert
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/
@Mapper
public interface MovieLanguageConvert {

    MovieLanguageConvert INSTANCE = Mappers.getMapper(MovieLanguageConvert.class);

    MovieLanguageDTO convert(MovieLanguageDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieLanguageDO convertToDO(MovieLanguageDTO dto);

    MovieLanguageDTO convertToDTO(MovieLanguagePageReq req);

    MovieLanguageDTO convertToDTO(MovieLanguageCreateReq req);

    MovieLanguageDTO convertToDTO(MovieLanguageUpdateReq req);

    MovieLanguagePageResp convertToPageResp(MovieLanguageDTO dto);

    MovieLanguageViewResp convertToViewResp(MovieLanguageDTO dto);

    MovieLanguageCreateResp convertToCreateResp(MovieLanguageDTO dto);

    MovieLanguageExp convertToExp(MovieLanguageDTO dto);

}