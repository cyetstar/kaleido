package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysUserRoleDTO;
import cc.onelooker.kaleido.entity.SysUserRoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

/**
 * 用户和角色关系表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysUserRoleConvert {
    SysUserRoleConvert INSTANCE = Mappers.getMapper(SysUserRoleConvert.class);

    SysUserRoleDTO convert(SysUserRoleDO sysUserRoleDO);

    @InheritInverseConfiguration(name="convert")
    SysUserRoleDO convertToDO(SysUserRoleDTO sysUserRoleDTO);
}