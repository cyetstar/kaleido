package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.AudioDTO;
import cc.onelooker.kaleido.entity.business.AudioDO;
import cc.onelooker.kaleido.dto.business.req.AudioPageReq;
import cc.onelooker.kaleido.dto.business.req.AudioCreateReq;
import cc.onelooker.kaleido.dto.business.req.AudioUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.AudioPageResp;
import cc.onelooker.kaleido.dto.business.resp.AudioViewResp;
import cc.onelooker.kaleido.dto.business.resp.AudioCreateResp;
import cc.onelooker.kaleido.dto.business.exp.AudioExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface AudioConvert {

    AudioConvert INSTANCE = Mappers.getMapper(AudioConvert.class);

    AudioDTO convert(AudioDO entity);

    @InheritInverseConfiguration(name="convert")
    AudioDO convertToDO(AudioDTO dto);

    AudioDTO convertToDTO(AudioPageReq req);

    AudioDTO convertToDTO(AudioCreateReq req);

    AudioDTO convertToDTO(AudioUpdateReq req);

    AudioPageResp convertToPageResp(AudioDTO dto);

    AudioViewResp convertToViewResp(AudioDTO dto);

    AudioCreateResp convertToCreateResp(AudioDTO dto);

    AudioExp convertToExp(AudioDTO dto);

}