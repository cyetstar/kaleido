package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysRoleResourceDTO;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourcePageReq;
import cc.onelooker.kaleido.dto.system.req.SysRoleResourceUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourceCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourcePageResp;
import cc.onelooker.kaleido.dto.system.resp.SysRoleResourceViewResp;
import cc.onelooker.kaleido.entity.system.SysRoleResourceDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色和资源关系表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper
public interface SysRoleResourceConvert {
    SysRoleResourceConvert INSTANCE = Mappers.getMapper(SysRoleResourceConvert.class);

    SysRoleResourceDTO convert(SysRoleResourceDO sysRoleResourceDO);

    @InheritInverseConfiguration(name = "convert")
    SysRoleResourceDO convertToDO(SysRoleResourceDTO sysRoleResourceDTO);

    SysRoleResourceDTO convertToDTO(SysRoleResourcePageReq req);

    SysRoleResourceDTO convertToDTO(SysRoleResourceCreateReq req);

    SysRoleResourceDTO convertToDTO(SysRoleResourceUpdateReq req);

    SysRoleResourcePageResp convertToPageResp(SysRoleResourceDTO dto);

    SysRoleResourceViewResp convertToViewResp(SysRoleResourceDTO dto);

    SysRoleResourceCreateResp convertToCreateResp(SysRoleResourceDTO dto);

}