package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysMenuDTO;
import cc.onelooker.kaleido.dto.req.SysMenuCreateReq;
import cc.onelooker.kaleido.dto.req.SysMenuInitReq;
import cc.onelooker.kaleido.dto.req.SysMenuPageReq;
import cc.onelooker.kaleido.dto.req.SysMenuUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysMenuCreateResp;
import cc.onelooker.kaleido.dto.resp.SysMenuPageResp;
import cc.onelooker.kaleido.dto.resp.SysMenuTreeResp;
import cc.onelooker.kaleido.dto.resp.SysMenuViewResp;
import cc.onelooker.kaleido.entity.SysMenuDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 菜单表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuDTO convert(SysMenuDO sysMenuDO);

    @InheritInverseConfiguration(name = "convert")
    SysMenuDO convertToDO(SysMenuDTO sysMenuDTO);

    SysMenuDTO convertToDTO(SysMenuPageReq req);

    SysMenuDTO convertToDTO(SysMenuCreateReq req);

    SysMenuDTO convertToDTO(SysMenuUpdateReq req);

    SysMenuPageResp convertToPageResp(SysMenuDTO dto);

    SysMenuViewResp convertToViewResp(SysMenuDTO dto);

    SysMenuCreateResp convertToCreateResp(SysMenuDTO dto);

    SysMenuDTO convertToDTO(SysMenuInitReq req);

    SysMenuTreeResp convertToTreeResp(SysMenuDTO dto);
}