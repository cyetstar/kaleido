package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieFileSubtitleDTO;
import cc.onelooker.kaleido.entity.movie.MovieFileSubtitleDO;
import cc.onelooker.kaleido.dto.movie.req.MovieFileSubtitlePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileSubtitleCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileSubtitleUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileSubtitlePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileSubtitleViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileSubtitleCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieFileSubtitleExp;

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