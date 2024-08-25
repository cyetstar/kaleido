package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleMenuDTO;
import cc.onelooker.kaleido.dto.req.SysRoleMenuCreateReq;
import cc.onelooker.kaleido.dto.req.SysRoleMenuPageReq;
import cc.onelooker.kaleido.dto.req.SysRoleMenuUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysRoleMenuCreateResp;
import cc.onelooker.kaleido.dto.resp.SysRoleMenuPageResp;
import cc.onelooker.kaleido.dto.resp.SysRoleMenuViewResp;
import cc.onelooker.kaleido.entity.SysRoleMenuDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色和菜单关系表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper
public interface SysRoleMenuConvert {
    SysRoleMenuConvert INSTANCE = Mappers.getMapper(SysRoleMenuConvert.class);

    SysRoleMenuDTO convert(SysRoleMenuDO sysRoleMenuDO);

    @InheritInverseConfiguration(name = "convert")
    SysRoleMenuDO convertToDO(SysRoleMenuDTO sysRoleMenuDTO);

    SysRoleMenuDTO convertToDTO(SysRoleMenuPageReq req);

    SysRoleMenuDTO convertToDTO(SysRoleMenuCreateReq req);

    SysRoleMenuDTO convertToDTO(SysRoleMenuUpdateReq req);

    SysRoleMenuPageResp convertToPageResp(SysRoleMenuDTO dto);

    SysRoleMenuViewResp convertToViewResp(SysRoleMenuDTO dto);

    SysRoleMenuCreateResp convertToCreateResp(SysRoleMenuDTO dto);

}