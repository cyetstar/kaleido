package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SysDictTypeDTO;
import cc.onelooker.kaleido.entity.SysDictTypeDO;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

/**
 * 字典表类型表Convert
 *
 * @author xiadawei
 * @date 2022-04-26 00:41:00
 */
@Mapper
public interface SysDictTypeConvert {
    SysDictTypeConvert INSTANCE = Mappers.getMapper(SysDictTypeConvert.class);

    SysDictTypeDTO convert(SysDictTypeDO sysDictTypeDO);

    @InheritInverseConfiguration(name="convert")
    SysDictTypeDO convertToDO(SysDictTypeDTO sysDictTypeDTO);
}