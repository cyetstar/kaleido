package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.dto.req.SysUserCreateReq;
import cc.onelooker.kaleido.dto.req.SysUserPageReq;
import cc.onelooker.kaleido.dto.req.SysUserUpdateReq;
import cc.onelooker.kaleido.dto.resp.SysUserCreateResp;
import cc.onelooker.kaleido.dto.resp.SysUserPageResp;
import cc.onelooker.kaleido.dto.resp.SysUserViewResp;
import cc.onelooker.kaleido.entity.SysUserDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 用户表Convert
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserDTO convert(SysUserDO sysUserDO);

    @InheritInverseConfiguration(name = "convert")
    SysUserDO convertToDO(SysUserDTO sysUserDTO);

    SysUserDTO convertToDTO(SysUserPageReq req);

    SysUserDTO convertToDTO(SysUserCreateReq req);

    SysUserDTO convertToDTO(SysUserUpdateReq req);

    SysUserPageResp convertToPageResp(SysUserDTO dto);

    SysUserViewResp convertToViewResp(SysUserDTO dto);

    SysUserCreateResp convertToCreateResp(SysUserDTO dto);

    SysUserPageResp.SysRolePageResp convertToPageResp(SysRoleDTO sysRoleDTO);

}