package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.UniqueidDTO;
import cc.onelooker.kaleido.entity.business.UniqueidDO;
import cc.onelooker.kaleido.dto.business.req.UniqueidPageReq;
import cc.onelooker.kaleido.dto.business.req.UniqueidCreateReq;
import cc.onelooker.kaleido.dto.business.req.UniqueidUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.UniqueidPageResp;
import cc.onelooker.kaleido.dto.business.resp.UniqueidViewResp;
import cc.onelooker.kaleido.dto.business.resp.UniqueidCreateResp;
import cc.onelooker.kaleido.dto.business.exp.UniqueidExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface UniqueidConvert {

    UniqueidConvert INSTANCE = Mappers.getMapper(UniqueidConvert.class);

    UniqueidDTO convert(UniqueidDO entity);

    @InheritInverseConfiguration(name="convert")
    UniqueidDO convertToDO(UniqueidDTO dto);

    UniqueidDTO convertToDTO(UniqueidPageReq req);

    UniqueidDTO convertToDTO(UniqueidCreateReq req);

    UniqueidDTO convertToDTO(UniqueidUpdateReq req);

    UniqueidPageResp convertToPageResp(UniqueidDTO dto);

    UniqueidViewResp convertToViewResp(UniqueidDTO dto);

    UniqueidCreateResp convertToCreateResp(UniqueidDTO dto);

    UniqueidExp convertToExp(UniqueidDTO dto);

}