package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.entity.SysResourceDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;
import cc.onelooker.kaleido.dto.SysResourceDTO;

/**
 * 资源表Convert
 *
 * @author xiadawei
 * @date 2022-08-15 17:38:57
 */
@Mapper
public interface SysResourceConvert {
    SysResourceConvert INSTANCE = Mappers.getMapper(SysResourceConvert.class);

    SysResourceDTO convert(SysResourceDO sysResourceDO);

    @InheritInverseConfiguration(name="convert")
    SysResourceDO convertToDO(SysResourceDTO sysResourceDTO);
}