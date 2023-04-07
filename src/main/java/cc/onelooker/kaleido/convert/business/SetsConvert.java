package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.SetsDTO;
import cc.onelooker.kaleido.entity.business.SetsDO;
import cc.onelooker.kaleido.dto.business.req.SetsPageReq;
import cc.onelooker.kaleido.dto.business.req.SetsCreateReq;
import cc.onelooker.kaleido.dto.business.req.SetsUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.SetsPageResp;
import cc.onelooker.kaleido.dto.business.resp.SetsViewResp;
import cc.onelooker.kaleido.dto.business.resp.SetsCreateResp;
import cc.onelooker.kaleido.dto.business.exp.SetsExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface SetsConvert {

    SetsConvert INSTANCE = Mappers.getMapper(SetsConvert.class);

    SetsDTO convert(SetsDO entity);

    @InheritInverseConfiguration(name="convert")
    SetsDO convertToDO(SetsDTO dto);

    SetsDTO convertToDTO(SetsPageReq req);

    SetsDTO convertToDTO(SetsCreateReq req);

    SetsDTO convertToDTO(SetsUpdateReq req);

    SetsPageResp convertToPageResp(SetsDTO dto);

    SetsViewResp convertToViewResp(SetsDTO dto);

    SetsCreateResp convertToCreateResp(SetsDTO dto);

    SetsExp convertToExp(SetsDTO dto);

}