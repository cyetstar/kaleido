package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleResourceDTO;
import cc.onelooker.kaleido.entity.SysRoleResourceDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

/**
 * 角色和资源关系表Convert
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysRoleResourceConvert {
    SysRoleResourceConvert INSTANCE = Mappers.getMapper(SysRoleResourceConvert.class);

    SysRoleResourceDTO convert(SysRoleResourceDO sysRoleResourceDO);

    @InheritInverseConfiguration(name="convert")
    SysRoleResourceDO convertToDO(SysRoleResourceDTO sysRoleResourceDTO);
}