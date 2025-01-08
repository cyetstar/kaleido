package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.req.AttributeCreateReq;
import cc.onelooker.kaleido.dto.req.AttributePageReq;
import cc.onelooker.kaleido.dto.req.AttributeUpdateReq;
import cc.onelooker.kaleido.dto.resp.AttributeCreateResp;
import cc.onelooker.kaleido.dto.resp.AttributePageResp;
import cc.onelooker.kaleido.dto.resp.AttributeViewResp;
import cc.onelooker.kaleido.entity.AttributeDO;
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
public interface AttributeConvert {

    AttributeConvert INSTANCE = Mappers.getMapper(AttributeConvert.class);

    AttributeDTO convert(AttributeDO entity);

    @InheritInverseConfiguration(name = "convert")
    AttributeDO convertToDO(AttributeDTO dto);

    AttributeDTO convertToDTO(AttributePageReq req);

    AttributeDTO convertToDTO(AttributeCreateReq req);

    AttributeDTO convertToDTO(AttributeUpdateReq req);

    AttributePageResp convertToPageResp(AttributeDTO dto);

    AttributeViewResp convertToViewResp(AttributeDTO dto);

    AttributeCreateResp convertToCreateResp(AttributeDTO dto);

}