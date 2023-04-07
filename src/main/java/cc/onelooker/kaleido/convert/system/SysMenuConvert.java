package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysMenuDTO;
import cc.onelooker.kaleido.dto.system.exp.SysMenuExp;
import cc.onelooker.kaleido.dto.system.req.SysMenuCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuInitReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuPageReq;
import cc.onelooker.kaleido.dto.system.req.SysMenuUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysMenuCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuTreeResp;
import cc.onelooker.kaleido.dto.system.resp.SysMenuViewResp;
import cc.onelooker.kaleido.entity.system.SysMenuDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单表Convert
 *
 * @author xiadawei
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuDTO convert(SysMenuDO sysMenuDO);

    @InheritInverseConfiguration(name="convert")
    SysMenuDO convertToDO(SysMenuDTO sysMenuDTO);

    SysMenuDTO convertToDTO(SysMenuPageReq req);

    SysMenuDTO convertToDTO(SysMenuCreateReq req);

    SysMenuDTO convertToDTO(SysMenuUpdateReq req);

    SysMenuPageResp convertToPageResp(SysMenuDTO dto);

    SysMenuViewResp convertToViewResp(SysMenuDTO dto);

    SysMenuCreateResp convertToCreateResp(SysMenuDTO dto);

    SysMenuExp convertToExp(SysMenuDTO dto);

    SysMenuDTO convertToDTO(SysMenuInitReq req);

    SysMenuTreeResp convertToTreeResp(SysMenuDTO dto);
}