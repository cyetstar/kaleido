package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleResourceDTO;
import cc.onelooker.kaleido.entity.SysRoleResourceDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 角色和资源关系表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 00:43:42
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleResourceConvert {
    SysRoleResourceConvert INSTANCE = Mappers.getMapper(SysRoleResourceConvert.class);

    SysRoleResourceDTO convert(SysRoleResourceDO sysRoleResourceDO);

    @InheritInverseConfiguration(name = "convert")
    SysRoleResourceDO convertToDO(SysRoleResourceDTO sysRoleResourceDTO);

}