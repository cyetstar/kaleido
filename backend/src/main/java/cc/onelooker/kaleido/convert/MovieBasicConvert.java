package cc.onelooker.kaleido.convert;

import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.req.MovieBasicCreateReq;
import cc.onelooker.kaleido.dto.req.MovieBasicPageReq;
import cc.onelooker.kaleido.dto.req.MovieBasicUpdateReq;
import cc.onelooker.kaleido.dto.resp.*;
import cc.onelooker.kaleido.entity.MovieBasicDO;
import cc.onelooker.kaleido.third.tmm.Movie;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Map;

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

    MovieBasicDTO convertToDTO(MovieBasicPageReq req);

    MovieBasicDTO convertToDTO(MovieBasicCreateReq req);

    @Mappings({
            @Mapping(target = "directorList", ignore = true),
            @Mapping(target = "writerList", ignore = true),
            @Mapping(target = "actorList", ignore = true),
    })
    MovieBasicDTO convertToDTO(MovieBasicUpdateReq req);

    MovieBasicPageResp convertToPageResp(MovieBasicDTO dto);

    MovieBasicViewResp convertToViewResp(MovieBasicDTO dto);

    MovieBasicCreateResp convertToCreateResp(MovieBasicDTO dto);

    MovieBasicSearchInfoResp convertToSearchInfoResp(Movie movie);

    MovieBasicListByCollectionIdResp convertToListByCollectionIdResp(MovieBasicDTO dto);

    @Mappings({
            @Mapping(target = "idList", ignore = true),
            @Mapping(target = "akaList", ignore = true),
            @Mapping(target = "tagList", ignore = true),
            @Mapping(target = "directorList", ignore = true),
            @Mapping(target = "actorList", ignore = true),
            @Mapping(target = "writerList", ignore = true),
            @Mapping(target = "genreList", ignore = true),
            @Mapping(target = "countryList", ignore = true),
            @Mapping(target = "languageList", ignore = true),
    })
    MovieBasicDTO convertToDTO(Map<String, String> params);
}