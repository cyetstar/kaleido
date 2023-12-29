package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysUserRoleDTO;
import cc.onelooker.kaleido.dto.system.req.SysUserRoleCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysUserRolePageReq;
import cc.onelooker.kaleido.dto.system.req.SysUserRoleUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysUserRoleCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserRolePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserRoleViewResp;
import cc.onelooker.kaleido.entity.system.SysUserRoleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户和角色关系表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysUserRoleConvert {
    SysUserRoleConvert INSTANCE = Mappers.getMapper(SysUserRoleConvert.class);

    SysUserRoleDTO convert(SysUserRoleDO sysUserRoleDO);

    @InheritInverseConfiguration(name = "convert")
    SysUserRoleDO convertToDO(SysUserRoleDTO sysUserRoleDTO);

    SysUserRoleDTO convertToDTO(SysUserRolePageReq req);

    SysUserRoleDTO convertToDTO(SysUserRoleCreateReq req);

    SysUserRoleDTO convertToDTO(SysUserRoleUpdateReq req);

    SysUserRolePageResp convertToPageResp(SysUserRoleDTO dto);

    SysUserRoleViewResp convertToViewResp(SysUserRoleDTO dto);

    SysUserRoleCreateResp convertToCreateResp(SysUserRoleDTO dto);

}