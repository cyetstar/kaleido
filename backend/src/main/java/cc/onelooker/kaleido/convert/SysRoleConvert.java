package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.dto.req.SysRoleCreateReq;
import cc.onelooker.kaleido.dto.req.SysRolePageReq;
import cc.onelooker.kaleido.dto.req.SysRoleUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysRoleCreateResp;
import cc.onelooker.kaleido.dto.resp.SysRolePageResp;
import cc.onelooker.kaleido.dto.resp.SysRoleViewResp;
import cc.onelooker.kaleido.entity.SysRoleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 角色表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleDTO convert(SysRoleDO sysRoleDO);

    @InheritInverseConfiguration(name = "convert")
    SysRoleDO convertToDO(SysRoleDTO sysRoleDTO);

    SysRoleDTO convertToDTO(SysRolePageReq req);

    SysRoleDTO convertToDTO(SysRoleCreateReq req);

    SysRoleDTO convertToDTO(SysRoleUpdateReq req);

    SysRolePageResp convertToPageResp(SysRoleDTO dto);

    SysRoleViewResp convertToViewResp(SysRoleDTO dto);

    SysRoleCreateResp convertToCreateResp(SysRoleDTO dto);

}