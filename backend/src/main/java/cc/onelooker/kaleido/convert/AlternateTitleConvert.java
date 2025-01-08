package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.req.AlternateTitleCreateReq;
import cc.onelooker.kaleido.dto.req.AlternateTitlePageReq;
import cc.onelooker.kaleido.dto.req.AlternateTitleUpdateReq;
import cc.onelooker.kaleido.dto.resp.AlternateTitleCreateResp;
import cc.onelooker.kaleido.dto.resp.AlternateTitlePageResp;
import cc.onelooker.kaleido.dto.resp.AlternateTitleViewResp;
import cc.onelooker.kaleido.entity.AlternateTitleDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 别名Convert
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlternateTitleConvert {

    AlternateTitleConvert INSTANCE = Mappers.getMapper(AlternateTitleConvert.class);

    AlternateTitleDTO convert(AlternateTitleDO entity);

    @InheritInverseConfiguration(name = "convert")
    AlternateTitleDO convertToDO(AlternateTitleDTO dto);

    AlternateTitleDTO convertToDTO(AlternateTitlePageReq req);

    AlternateTitleDTO convertToDTO(AlternateTitleCreateReq req);

    AlternateTitleDTO convertToDTO(AlternateTitleUpdateReq req);

    AlternateTitlePageResp convertToPageResp(AlternateTitleDTO dto);

    AlternateTitleViewResp convertToViewResp(AlternateTitleDTO dto);

    AlternateTitleCreateResp convertToCreateResp(AlternateTitleDTO dto);

}