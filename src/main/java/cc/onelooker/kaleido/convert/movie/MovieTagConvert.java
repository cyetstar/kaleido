package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieTagDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieTagCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieTagPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieTagUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagViewResp;
import cc.onelooker.kaleido.entity.movie.MovieTagDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 电影标签Convert
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Mapper
public interface MovieTagConvert {

    MovieTagConvert INSTANCE = Mappers.getMapper(MovieTagConvert.class);

    MovieTagDTO convert(MovieTagDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieTagDO convertToDO(MovieTagDTO dto);

    MovieTagDTO convertToDTO(MovieTagPageReq req);

    MovieTagDTO convertToDTO(MovieTagCreateReq req);

    MovieTagDTO convertToDTO(MovieTagUpdateReq req);

    MovieTagPageResp convertToPageResp(MovieTagDTO dto);

    MovieTagViewResp convertToViewResp(MovieTagDTO dto);

    MovieTagCreateResp convertToCreateResp(MovieTagDTO dto);

}