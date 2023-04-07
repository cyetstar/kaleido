package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.SubtitleDTO;
import cc.onelooker.kaleido.entity.business.SubtitleDO;
import cc.onelooker.kaleido.dto.business.req.SubtitlePageReq;
import cc.onelooker.kaleido.dto.business.req.SubtitleCreateReq;
import cc.onelooker.kaleido.dto.business.req.SubtitleUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.SubtitlePageResp;
import cc.onelooker.kaleido.dto.business.resp.SubtitleViewResp;
import cc.onelooker.kaleido.dto.business.resp.SubtitleCreateResp;
import cc.onelooker.kaleido.dto.business.exp.SubtitleExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface SubtitleConvert {

    SubtitleConvert INSTANCE = Mappers.getMapper(SubtitleConvert.class);

    SubtitleDTO convert(SubtitleDO entity);

    @InheritInverseConfiguration(name="convert")
    SubtitleDO convertToDO(SubtitleDTO dto);

    SubtitleDTO convertToDTO(SubtitlePageReq req);

    SubtitleDTO convertToDTO(SubtitleCreateReq req);

    SubtitleDTO convertToDTO(SubtitleUpdateReq req);

    SubtitlePageResp convertToPageResp(SubtitleDTO dto);

    SubtitleViewResp convertToViewResp(SubtitleDTO dto);

    SubtitleCreateResp convertToCreateResp(SubtitleDTO dto);

    SubtitleExp convertToExp(SubtitleDTO dto);

}