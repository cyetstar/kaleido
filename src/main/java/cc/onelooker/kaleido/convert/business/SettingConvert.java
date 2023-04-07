package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.SettingDTO;
import cc.onelooker.kaleido.entity.business.SettingDO;
import cc.onelooker.kaleido.dto.business.req.SettingPageReq;
import cc.onelooker.kaleido.dto.business.req.SettingCreateReq;
import cc.onelooker.kaleido.dto.business.req.SettingUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.SettingPageResp;
import cc.onelooker.kaleido.dto.business.resp.SettingViewResp;
import cc.onelooker.kaleido.dto.business.resp.SettingCreateResp;
import cc.onelooker.kaleido.dto.business.exp.SettingExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface SettingConvert {

    SettingConvert INSTANCE = Mappers.getMapper(SettingConvert.class);

    SettingDTO convert(SettingDO entity);

    @InheritInverseConfiguration(name="convert")
    SettingDO convertToDO(SettingDTO dto);

    SettingDTO convertToDTO(SettingPageReq req);

    SettingDTO convertToDTO(SettingCreateReq req);

    SettingDTO convertToDTO(SettingUpdateReq req);

    SettingPageResp convertToPageResp(SettingDTO dto);

    SettingViewResp convertToViewResp(SettingDTO dto);

    SettingCreateResp convertToCreateResp(SettingDTO dto);

    SettingExp convertToExp(SettingDTO dto);

}