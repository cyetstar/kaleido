package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.LanguageDTO;
import cc.onelooker.kaleido.entity.business.LanguageDO;
import cc.onelooker.kaleido.dto.business.req.LanguagePageReq;
import cc.onelooker.kaleido.dto.business.req.LanguageCreateReq;
import cc.onelooker.kaleido.dto.business.req.LanguageUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.LanguagePageResp;
import cc.onelooker.kaleido.dto.business.resp.LanguageViewResp;
import cc.onelooker.kaleido.dto.business.resp.LanguageCreateResp;
import cc.onelooker.kaleido.dto.business.exp.LanguageExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface LanguageConvert {

    LanguageConvert INSTANCE = Mappers.getMapper(LanguageConvert.class);

    LanguageDTO convert(LanguageDO entity);

    @InheritInverseConfiguration(name="convert")
    LanguageDO convertToDO(LanguageDTO dto);

    LanguageDTO convertToDTO(LanguagePageReq req);

    LanguageDTO convertToDTO(LanguageCreateReq req);

    LanguageDTO convertToDTO(LanguageUpdateReq req);

    LanguagePageResp convertToPageResp(LanguageDTO dto);

    LanguageViewResp convertToViewResp(LanguageDTO dto);

    LanguageCreateResp convertToCreateResp(LanguageDTO dto);

    LanguageExp convertToExp(LanguageDTO dto);

}