package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysConfigDTO;
import cc.onelooker.kaleido.dto.system.req.SysConfigCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigPageReq;
import cc.onelooker.kaleido.dto.system.req.SysConfigUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysConfigCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysConfigViewResp;
import cc.onelooker.kaleido.entity.system.SysConfigDO;
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