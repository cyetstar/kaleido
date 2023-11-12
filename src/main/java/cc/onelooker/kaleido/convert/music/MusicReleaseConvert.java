package cc.onelooker.kaleido.convert.music;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.entity.music.MusicReleaseDO;
import cc.onelooker.kaleido.dto.music.req.MusicReleasePageReq;
import cc.onelooker.kaleido.dto.music.req.MusicReleaseCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicReleaseUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicReleasePageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicReleaseViewResp;
import cc.onelooker.kaleido.dto.music.resp.MusicReleaseCreateResp;
import cc.onelooker.kaleido.exp.music.MusicReleaseExp;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateFormat;
import java.lang.Integer;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;
/**
* 发行品Convert
*
* @author cyetstar
* @date 2023-09-29 22:32:53
*/
@Mapper
public interface MusicReleaseConvert {

    MusicReleaseConvert INSTANCE = Mappers.getMapper(MusicReleaseConvert.class);

    MusicReleaseDTO convert(MusicReleaseDO entity);

    @InheritInverseConfiguration(name="convert")
    MusicReleaseDO convertToDO(MusicReleaseDTO dto);

    MusicReleaseDTO convertToDTO(MusicReleasePageReq req);

    MusicReleaseDTO convertToDTO(MusicReleaseCreateReq req);

    MusicReleaseDTO convertToDTO(MusicReleaseUpdateReq req);

    MusicReleasePageResp convertToPageResp(MusicReleaseDTO dto);

    MusicReleaseViewResp convertToViewResp(MusicReleaseDTO dto);

    MusicReleaseCreateResp convertToCreateResp(MusicReleaseDTO dto);

    MusicReleaseExp convertToExp(MusicReleaseDTO dto);

}