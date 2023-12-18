package cc.onelooker.kaleido.convert.movie;

import cc.onelooker.kaleido.dto.movie.MovieActorDTO;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.third.douban.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.mapstruct.InheritInverseConfiguration;

import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicDO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicUpdateReq;
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

    @InheritInverseConfiguration(name = "convert")
    MovieBasicDO convertToDO(MovieBasicDTO dto);

    @Mappings({
            @Mapping(source = "genre", target = "genreId"),
            @Mapping(source = "country", target = "countryId")
    })
    MovieBasicDTO convertToDTO(MovieBasicPageReq req);

    MovieBasicDTO convertToDTO(MovieBasicCreateReq req);

    MovieBasicDTO convertToDTO(MovieBasicUpdateReq req);

    MovieBasicPageResp convertToPageResp(MovieBasicDTO dto);

    MovieBasicViewResp convertToViewResp(MovieBasicDTO dto);

    MovieBasicViewResp.Actor convertToViewResp(MovieActorDTO dto);

    MovieBasicCreateResp convertToCreateResp(MovieBasicDTO dto);

    MovieBasicExp convertToExp(MovieBasicDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "doubanId"),
            @Mapping(source = "images.large", target = "picUrl")
    })
    MovieBasicSearchDoubanResp convertToSearchDoubanResp(Movie movie);

    MovieBasicListByCollectionIdResp convertToListByCollectionIdResp(MovieBasicDTO dto);
}