package cc.onelooker.kaleido.convert.movie;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicDO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicViewResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCreateResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicExp;

/**
* 电影Convert
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/
@Mapper
public interface MovieBasicConvert {

    MovieBasicConvert INSTANCE = Mappers.getMapper(MovieBasicConvert.class);

    MovieBasicDTO convert(MovieBasicDO entity);

    @InheritInverseConfiguration(name="convert")
    MovieBasicDO convertToDO(MovieBasicDTO dto);

    MovieBasicDTO convertToDTO(MovieBasicPageReq req);

    MovieBasicDTO convertToDTO(MovieBasicCreateReq req);

    MovieBasicDTO convertToDTO(MovieBasicUpdateReq req);

    MovieBasicPageResp convertToPageResp(MovieBasicDTO dto);

    MovieBasicViewResp convertToViewResp(MovieBasicDTO dto);

    MovieBasicCreateResp convertToCreateResp(MovieBasicDTO dto);

    MovieBasicExp convertToExp(MovieBasicDTO dto);

}