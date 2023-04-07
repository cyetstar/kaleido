package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.ThumbDTO;
import cc.onelooker.kaleido.entity.business.ThumbDO;
import cc.onelooker.kaleido.dto.business.req.ThumbPageReq;
import cc.onelooker.kaleido.dto.business.req.ThumbCreateReq;
import cc.onelooker.kaleido.dto.business.req.ThumbUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.ThumbPageResp;
import cc.onelooker.kaleido.dto.business.resp.ThumbViewResp;
import cc.onelooker.kaleido.dto.business.resp.ThumbCreateResp;
import cc.onelooker.kaleido.dto.business.exp.ThumbExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface ThumbConvert {

    ThumbConvert INSTANCE = Mappers.getMapper(ThumbConvert.class);

    ThumbDTO convert(ThumbDO entity);

    @InheritInverseConfiguration(name="convert")
    ThumbDO convertToDO(ThumbDTO dto);

    ThumbDTO convertToDTO(ThumbPageReq req);

    ThumbDTO convertToDTO(ThumbCreateReq req);

    ThumbDTO convertToDTO(ThumbUpdateReq req);

    ThumbPageResp convertToPageResp(ThumbDTO dto);

    ThumbViewResp convertToViewResp(ThumbDTO dto);

    ThumbCreateResp convertToCreateResp(ThumbDTO dto);

    ThumbExp convertToExp(ThumbDTO dto);

}