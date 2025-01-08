package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.entity.SubjectAttributeDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 属性Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectAttributeConvert {

    SubjectAttributeConvert INSTANCE = Mappers.getMapper(SubjectAttributeConvert.class);

    SubjectAttributeDTO convert(SubjectAttributeDO entity);

    @InheritInverseConfiguration(name = "convert")
    SubjectAttributeDO convertToDO(SubjectAttributeDTO dto);

}