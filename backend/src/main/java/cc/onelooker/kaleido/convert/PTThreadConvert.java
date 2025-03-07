package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.PTThreadDTO;
import cc.onelooker.kaleido.entity.PTThreadDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 电影发布记录Convert
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PTThreadConvert {

    PTThreadConvert INSTANCE = Mappers.getMapper(PTThreadConvert.class);

    PTThreadDTO convert(PTThreadDO entity);

    @InheritInverseConfiguration(name = "convert")
    PTThreadDO convertToDO(PTThreadDTO dto);

}