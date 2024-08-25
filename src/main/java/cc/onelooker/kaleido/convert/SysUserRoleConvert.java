package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysUserRoleDTO;
import cc.onelooker.kaleido.dto.req.SysUserRoleCreateReq;
import cc.onelooker.kaleido.dto.req.SysUserRolePageReq;
import cc.onelooker.kaleido.dto.req.SysUserRoleUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysUserRoleCreateResp;
import cc.onelooker.kaleido.dto.resp.SysUserRolePageResp;
import cc.onelooker.kaleido.dto.resp.SysUserRoleViewResp;
import cc.onelooker.kaleido.entity.SysUserRoleDO;
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