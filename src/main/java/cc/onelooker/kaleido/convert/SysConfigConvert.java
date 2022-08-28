package cc.onelooker.kaleido.convert;

import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;
import cc.onelooker.kaleido.dto.SysConfigDTO;
import cc.onelooker.kaleido.entity.SysConfigDO;
/**
 * 系统配置表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysConfigConvert {
    SysConfigConvert INSTANCE = Mappers.getMapper(SysConfigConvert.class);

    SysConfigDTO convert(SysConfigDO sysConfigDO);

    @InheritInverseConfiguration(name="convert")
    SysConfigDO convertToDO(SysConfigDTO sysConfigDTO);
}