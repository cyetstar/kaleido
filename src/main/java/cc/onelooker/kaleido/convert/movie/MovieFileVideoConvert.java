package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieFileVideoDTO;
import cc.onelooker.kaleido.entity.movie.MovieFileVideoDO;
import cc.onelooker.kaleido.dto.movie.req.MovieFileVideoPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileVideoCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileVideoUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileVideoPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileVideoViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileVideoCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieFileVideoExp;

/**
* 电影文件视频信息Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileVideoConvert {

    MovieFileVideoConvert INSTANCE = Mappers.getMapper(MovieFileVideoConvert.class);

    MovieFileVideoDTO convert(MovieFileVideoDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileVideoDO convertToDO(MovieFileVideoDTO dto);

    MovieFileVideoDTO convertToDTO(MovieFileVideoPageReq req);

    MovieFileVideoDTO convertToDTO(MovieFileVideoCreateReq req);

    MovieFileVideoDTO convertToDTO(MovieFileVideoUpdateReq req);

    MovieFileVideoPageResp convertToPageResp(MovieFileVideoDTO dto);

    MovieFileVideoViewResp convertToViewResp(MovieFileVideoDTO dto);

    MovieFileVideoCreateResp convertToCreateResp(MovieFileVideoDTO dto);

    MovieFileVideoExp convertToExp(MovieFileVideoDTO dto);

}