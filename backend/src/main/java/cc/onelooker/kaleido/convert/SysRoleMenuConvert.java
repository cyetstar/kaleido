package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysRoleMenuDTO;
import cc.onelooker.kaleido.entity.SysRoleMenuDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 角色和菜单关系表Convert
 *
 * @author cyetstar
 * @date 2022-11-13 01:12:24
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysRoleMenuConvert {
    SysRoleMenuConvert INSTANCE = Mappers.getMapper(SysRoleMenuConvert.class);

    SysRoleMenuDTO convert(SysRoleMenuDO sysRoleMenuDO);

    @InheritInverseConfiguration(name = "convert")
    SysRoleMenuDO convertToDO(SysRoleMenuDTO sysRoleMenuDTO);

}