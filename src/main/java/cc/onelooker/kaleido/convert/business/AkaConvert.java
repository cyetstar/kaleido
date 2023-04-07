package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.AkaDTO;
import cc.onelooker.kaleido.entity.business.AkaDO;
import cc.onelooker.kaleido.dto.business.req.AkaPageReq;
import cc.onelooker.kaleido.dto.business.req.AkaCreateReq;
import cc.onelooker.kaleido.dto.business.req.AkaUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.AkaPageResp;
import cc.onelooker.kaleido.dto.business.resp.AkaViewResp;
import cc.onelooker.kaleido.dto.business.resp.AkaCreateResp;
import cc.onelooker.kaleido.dto.business.exp.AkaExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface AkaConvert {

    AkaConvert INSTANCE = Mappers.getMapper(AkaConvert.class);

    AkaDTO convert(AkaDO entity);

    @InheritInverseConfiguration(name="convert")
    AkaDO convertToDO(AkaDTO dto);

    AkaDTO convertToDTO(AkaPageReq req);

    AkaDTO convertToDTO(AkaCreateReq req);

    AkaDTO convertToDTO(AkaUpdateReq req);

    AkaPageResp convertToPageResp(AkaDTO dto);

    AkaViewResp convertToViewResp(AkaDTO dto);

    AkaCreateResp convertToCreateResp(AkaDTO dto);

    AkaExp convertToExp(AkaDTO dto);

}