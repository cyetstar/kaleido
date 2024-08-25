package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.dto.req.SysConfigCreateReq;
import cc.onelooker.kaleido.dto.req.SysConfigPageReq;
import cc.onelooker.kaleido.dto.req.SysConfigUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysConfigCreateResp;
import cc.onelooker.kaleido.dto.resp.SysConfigPageResp;
import cc.onelooker.kaleido.dto.resp.SysConfigViewResp;
import cc.onelooker.kaleido.entity.SysConfigDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysConfigConvert {
    SysConfigConvert INSTANCE = Mappers.getMapper(SysConfigConvert.class);

    SysConfigDTO convert(SysConfigDO sysConfigDO);

    @InheritInverseConfiguration(name = "convert")
    SysConfigDO convertToDO(SysConfigDTO sysConfigDTO);

    SysConfigDTO convertToDTO(SysConfigPageReq req);

    SysConfigDTO convertToDTO(SysConfigCreateReq req);

    SysConfigDTO convertToDTO(SysConfigUpdateReq req);

    SysConfigPageResp convertToPageResp(SysConfigDTO dto);

    SysConfigViewResp convertToViewResp(SysConfigDTO dto);

    SysConfigCreateResp convertToCreateResp(SysConfigDTO dto);

}