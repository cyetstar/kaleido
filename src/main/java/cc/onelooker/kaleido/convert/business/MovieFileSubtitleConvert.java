package cc.onelooker.kaleido.convert.business;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.business.MovieFileSubtitleDTO;
import cc.onelooker.kaleido.entity.business.MovieFileSubtitleDO;
import cc.onelooker.kaleido.dto.business.req.MovieFileSubtitlePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileSubtitleCreateReq;
import cc.onelooker.kaleido.dto.business.req.MovieFileSubtitleUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieFileSubtitlePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileSubtitleViewResp;
import cc.onelooker.kaleido.dto.business.resp.MovieFileSubtitleCreateResp;
import cc.onelooker.kaleido.exp.business.MovieFileSubtitleExp;

import java.lang.Long;
import java.lang.String;
/**
* 电影文件字幕信息Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileSubtitleConvert {

    MovieFileSubtitleConvert INSTANCE = Mappers.getMapper(MovieFileSubtitleConvert.class);

    MovieFileSubtitleDTO convert(MovieFileSubtitleDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileSubtitleDO convertToDO(MovieFileSubtitleDTO dto);

    MovieFileSubtitleDTO convertToDTO(MovieFileSubtitlePageReq req);

    MovieFileSubtitleDTO convertToDTO(MovieFileSubtitleCreateReq req);

    MovieFileSubtitleDTO convertToDTO(MovieFileSubtitleUpdateReq req);

    MovieFileSubtitlePageResp convertToPageResp(MovieFileSubtitleDTO dto);

    MovieFileSubtitleViewResp convertToViewResp(MovieFileSubtitleDTO dto);

    MovieFileSubtitleCreateResp convertToCreateResp(MovieFileSubtitleDTO dto);

    MovieFileSubtitleExp convertToExp(MovieFileSubtitleDTO dto);

}