package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleDTO;
import cc.onelooker.kaleido.entity.SysRoleDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

/**
 * 角色表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);

    SysRoleDTO convert(SysRoleDO sysRoleDO);

    @InheritInverseConfiguration(name="convert")
    SysRoleDO convertToDO(SysRoleDTO sysRoleDTO);
}