package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieFileDTO;
import cc.onelooker.kaleido.entity.movie.MovieFileDO;
import cc.onelooker.kaleido.dto.movie.req.MovieFilePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieFileUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieFilePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieFileCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieFileExp;

/**
* 电影文件Convert
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/
@Mapper
public interface MovieFileConvert {

    MovieFileConvert INSTANCE = Mappers.getMapper(MovieFileConvert.class);

    MovieFileDTO convert(MovieFileDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieFileDO convertToDO(MovieFileDTO dto);

    MovieFileDTO convertToDTO(MovieFilePageReq req);

    MovieFileDTO convertToDTO(MovieFileCreateReq req);

    MovieFileDTO convertToDTO(MovieFileUpdateReq req);

    MovieFilePageResp convertToPageResp(MovieFileDTO dto);

    MovieFileViewResp convertToViewResp(MovieFileDTO dto);

    MovieFileCreateResp convertToCreateResp(MovieFileDTO dto);

    MovieFileExp convertToExp(MovieFileDTO dto);

}