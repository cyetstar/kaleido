package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.entity.SysDictDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;
import cc.onelooker.kaleido.dto.SysDictDTO;

/**
 * 字典表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysDictConvert {
    SysDictConvert INSTANCE = Mappers.getMapper(SysDictConvert.class);

    SysDictDTO convert(SysDictDO sysDictDO);

    @InheritInverseConfiguration(name="convert")
    SysDictDO convertToDO(SysDictDTO sysDictDTO);
}