package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.dto.req.MusicTrackCreateReq;
import cc.onelooker.kaleido.dto.req.MusicTrackPageReq;
import cc.onelooker.kaleido.dto.req.MusicTrackUpdateReq;
import cc.onelooker.kaleido.dto.resp.MusicTrackCreateResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackListByAlbumIdResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackPageResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackViewResp;
import cc.onelooker.kaleido.entity.MusicTrackDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * 曲目Convert
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicTrackConvert {

    MusicTrackConvert INSTANCE = Mappers.getMapper(MusicTrackConvert.class);

    MusicTrackDTO convert(MusicTrackDO entity);

    @InheritInverseConfiguration(name = "convert")
    MusicTrackDO convertToDO(MusicTrackDTO dto);

    MusicTrackDTO convertToDTO(MusicTrackPageReq req);

    MusicTrackDTO convertToDTO(MusicTrackCreateReq req);

    MusicTrackDTO convertToDTO(MusicTrackUpdateReq req);

    MusicTrackPageResp convertToPageResp(MusicTrackDTO dto);

    MusicTrackViewResp convertToViewResp(MusicTrackDTO dto);

    MusicTrackCreateResp convertToCreateResp(MusicTrackDTO dto);

    MusicTrackListByAlbumIdResp convertToListByAlbumIdResp(MusicTrackDTO dto);
}