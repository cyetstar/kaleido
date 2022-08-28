package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysUserDTO;
import cc.onelooker.kaleido.dto.req.SysUserRegisterReq;
import cc.onelooker.kaleido.dto.resp.SysUserListByRoleIdResp;
import cc.onelooker.kaleido.entity.SysUserDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUserDTO convert(SysUserDO sysUserDO);

    @InheritInverseConfiguration(name = "convert")
    SysUserDO convertToDO(SysUserDTO sysUserDTO);

    SysUserDTO convertToDTO(SysUserRegisterReq sysUserRegisterReq);

    SysUserListByRoleIdResp convertToListByRoleIdResp(SysUserDTO dto);
}