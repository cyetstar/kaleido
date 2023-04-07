package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.VideoDTO;
import cc.onelooker.kaleido.entity.business.VideoDO;
import cc.onelooker.kaleido.dto.business.req.VideoPageReq;
import cc.onelooker.kaleido.dto.business.req.VideoCreateReq;
import cc.onelooker.kaleido.dto.business.req.VideoUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.VideoPageResp;
import cc.onelooker.kaleido.dto.business.resp.VideoViewResp;
import cc.onelooker.kaleido.dto.business.resp.VideoCreateResp;
import cc.onelooker.kaleido.dto.business.exp.VideoExp;

/**
* Convert
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/
@Mapper
public interface VideoConvert {

    VideoConvert INSTANCE = Mappers.getMapper(VideoConvert.class);

    VideoDTO convert(VideoDO entity);

    @InheritInverseConfiguration(name="convert")
    VideoDO convertToDO(VideoDTO dto);

    VideoDTO convertToDTO(VideoPageReq req);

    VideoDTO convertToDTO(VideoCreateReq req);

    VideoDTO convertToDTO(VideoUpdateReq req);

    VideoPageResp convertToPageResp(VideoDTO dto);

    VideoViewResp convertToViewResp(VideoDTO dto);

    VideoCreateResp convertToCreateResp(VideoDTO dto);

    VideoExp convertToExp(VideoDTO dto);

}