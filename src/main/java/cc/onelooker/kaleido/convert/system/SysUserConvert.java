package cc.onelooker.kaleido.convert.system;

import cc.onelooker.kaleido.dto.system.SysRoleDTO;
import cc.onelooker.kaleido.dto.system.SysUserDTO;
import cc.onelooker.kaleido.dto.system.exp.SysUserExp;
import cc.onelooker.kaleido.dto.system.req.SysUserCreateReq;
import cc.onelooker.kaleido.dto.system.req.SysUserPageReq;
import cc.onelooker.kaleido.dto.system.req.SysUserUpdateReq;
import cc.onelooker.kaleido.dto.system.resp.SysUserCreateResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserPageResp;
import cc.onelooker.kaleido.dto.system.resp.SysUserViewResp;
import cc.onelooker.kaleido.entity.system.SysUserDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户表Convert
 *
 * @author cyetstar
 * @date 2022-11-10 16:46:50
 */
@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserDTO convert(SysUserDO sysUserDO);

    @InheritInverseConfiguration(name="convert")
    SysUserDO convertToDO(SysUserDTO sysUserDTO);

    SysUserDTO convertToDTO(SysUserPageReq req);

    SysUserDTO convertToDTO(SysUserCreateReq req);

    SysUserDTO convertToDTO(SysUserUpdateReq req);

    SysUserPageResp convertToPageResp(SysUserDTO dto);

    SysUserViewResp convertToViewResp(SysUserDTO dto);

    SysUserCreateResp convertToCreateResp(SysUserDTO dto);

    SysUserExp convertToExp(SysUserDTO dto);

    SysUserPageResp.SysRolePageResp convertToPageResp(SysRoleDTO sysRoleDTO);

}