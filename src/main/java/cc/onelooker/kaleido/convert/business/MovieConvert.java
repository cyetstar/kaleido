package cc.onelooker.kaleido.convert.business;

import cc.onelooker.kaleido.dto.business.*;
import cc.onelooker.kaleido.dto.business.exp.MovieExp;
import cc.onelooker.kaleido.dto.business.req.MovieCreateReq;
import cc.onelooker.kaleido.dto.business.req.MoviePageReq;
import cc.onelooker.kaleido.dto.business.req.MovieUpdateReq;
import cc.onelooker.kaleido.dto.business.resp.MovieCreateResp;
import cc.onelooker.kaleido.dto.business.resp.MoviePageResp;
import cc.onelooker.kaleido.dto.business.resp.MovieViewResp;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.entity.business.MovieDO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 电影Convert
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Mapper
public interface MovieConvert {

    MovieConvert INSTANCE = Mappers.getMapper(MovieConvert.class);

    MovieDTO convert(MovieDO entity);

    @InheritInverseConfiguration(name = "convert")
    MovieDO convertToDO(MovieDTO dto);

    MovieDTO convertToDTO(MoviePageReq req);

    MovieDTO convertToDTO(MovieCreateReq req);

    MovieDTO convertToDTO(MovieUpdateReq req);

    MovieDTO convertToDTO(MovieNFO movieNFO);

    MoviePageResp convertToPageResp(MovieDTO dto);

    MovieViewResp convertToViewResp(MovieDTO dto);

    MovieCreateResp convertToCreateResp(MovieDTO dto);

    MovieExp convertToExp(MovieDTO dto);

    List<MovieViewResp.MovieViewActorResp> convertToViewActorResp(List<MovieActorDTO> movieActorDTOList);

    List<MovieViewResp.MovieViewGenreResp> convertToViewGenreResp(List<MovieGenreDTO> movieGenreDTOList);

    List<MovieViewResp.MovieViewLanguageResp> convertToViewLanguageResp(List<MovieLanguageDTO> movieLanguageDTOList);

    List<MovieViewResp.MovieViewCountryResp> convertToViewCountryResp(List<MovieCountryDTO> movieCountryDTOList);

    List<MovieViewResp.MovieViewTagResp> convertToViewTagResp(List<MovieTagDTO> movieTagDTOList);

    List<MovieViewResp.MovieViewSetResp> convertToViewSetResp(List<MovieSetDTO> movieSetDTOList);

    List<MovieViewResp.MovieViewAkaResp> convertToViewAkaResp(List<MovieAkaDTO> movieAkaDTOList);

    MovieViewResp.MovieViewRatingResp convertToViewRatingResp(MovieRatingDTO movieRatingDTO);

    @Mappings({
            @Mapping(source = "movieFileVideoDTO", target = "video"),
            @Mapping(source = "movieFileAudioDTOList", target = "audioList"),
            @Mapping(source = "movieFileSubtitleDTOList", target = "subtitleList")
    })
    MovieViewResp.MovieViewFileResp convertToViewFileResp(MovieFileDTO movieFileDTO);

    MovieRatingDTO convertToDTO(MovieUpdateReq.MovieUpdateRatingReq req);
}