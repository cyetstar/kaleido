package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.RatingDTO;
import cc.onelooker.kaleido.entity.business.RatingDO;
import cc.onelooker.kaleido.dto.business.req.RatingPageReq;
import cc.onelooker.kaleido.dto.business.req.RatingCreateReq;
import cc.onelooker.kaleido.dto.business.req.RatingUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.RatingPageResp;
import cc.onelooker.kaleido.dto.business.resp.RatingViewResp;
import cc.onelooker.kaleido.dto.business.resp.RatingCreateResp;
import cc.onelooker.kaleido.dto.business.exp.RatingExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface RatingConvert {

    RatingConvert INSTANCE = Mappers.getMapper(RatingConvert.class);

    RatingDTO convert(RatingDO entity);

    @InheritInverseConfiguration(name="convert")
    RatingDO convertToDO(RatingDTO dto);

    RatingDTO convertToDTO(RatingPageReq req);

    RatingDTO convertToDTO(RatingCreateReq req);

    RatingDTO convertToDTO(RatingUpdateReq req);

    RatingPageResp convertToPageResp(RatingDTO dto);

    RatingViewResp convertToViewResp(RatingDTO dto);

    RatingCreateResp convertToCreateResp(RatingDTO dto);

    RatingExp convertToExp(RatingDTO dto);

}