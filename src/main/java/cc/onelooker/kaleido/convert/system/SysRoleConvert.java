package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysRoleDTO;
import cc.onelooker.kaleido.dto.system.exp.SysRoleExp;
import cc.onelooker.kaleido.dto.system.req.SysRoleCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysRolePageReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysRoleCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysRolePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleViewResp;
import cc.onelooker.kaleido.entity.system.SysRoleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleDTO convert(SysRoleDO sysRoleDO);

    @InheritInverseConfiguration(name="convert")
    SysRoleDO convertToDO(SysRoleDTO sysRoleDTO);

    SysRoleDTO convertToDTO(SysRolePageReq req);

    SysRoleDTO convertToDTO(SysRoleCreateReq req);

    SysRoleDTO convertToDTO(SysRoleUpdateReq req);

    SysRolePageResp convertToPageResp(SysRoleDTO dto);

    SysRoleViewResp convertToViewResp(SysRoleDTO dto);

    SysRoleCreateResp convertToCreateResp(SysRoleDTO dto);

    SysRoleExp convertToExp(SysRoleDTO dto);

}