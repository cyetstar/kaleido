package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.nfo.*;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    public static List<MovieCountryDTO> toMovieCountryDTOList(List<String> countries) {
        if (countries == null) {
            return null;
        }
        return countries.stream().map(s -> {
            MovieCountryDTO movieCountryDTO = new MovieCountryDTO();
            movieCountryDTO.setTag(s);
            return movieCountryDTO;
        }).collect(Collectors.toList());
    }

    public static List<MovieAkaDTO> toMovieAkaDTOList(List<String> akas) {
        if (akas == null) {
            return null;
        }
        return akas.stream().map(s -> {
            MovieAkaDTO movieAkaDTO = new MovieAkaDTO();
            movieAkaDTO.setTitle(s);
            return movieAkaDTO;
        }).collect(Collectors.toList());
    }

    public static List<MovieTagDTO> toMovieTagDTOList(List<String> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(s -> {
            MovieTagDTO movieTagDTO = new MovieTagDTO();
            movieTagDTO.setTag(s);
            return movieTagDTO;
        }).collect(Collectors.toList());
    }

//
//    public static MovieNFO toMovieNFO(Movie movie) {
//        MovieNFO movieNFO = new MovieNFO();
//        movieNFO.setTitle(movie.getTitle());
//        movieNFO.setOriginaltitle(movie.getOriginaltitle());
//        movieNFO.setSorttitle(movie.getSorttitle());
//        movieNFO.setRating(toRatingNFOs(movie.getRatings()));
//        movieNFO.setUserrating(movie.getUserrating());
//        movieNFO.setTop250(movie.getTop250());
//        movieNFO.setOutline(movie.getOutline());
//        movieNFO.setPlot(movie.getPlot());
//        movieNFO.setTagline(movie.getTagline());
//        movieNFO.setRuntime(movie.getRuntime());
//        movieNFO.setThumb(toThumbNFOs(movie.getThumbs()));
//        if (movieNFO.getThumb() != null) {
//            List<ThumbNFO> fanartThumbList = movieNFO.getThumb().stream().filter(s -> Aspect.fanart.name().equals(s.getAspect())).collect(Collectors.toList());
//            if (!fanartThumbList.isEmpty()) {
//                FanartNFO fanartNFO = new FanartNFO();
//                fanartNFO.setThumb(fanartThumbList);
//                movieNFO.setFanart(fanartNFO);
//            }
//        }
//
//        movieNFO.setMpaa(movie.getMpaa());
//        movieNFO.setPlaycount(movie.getPlaycount());
//        movieNFO.setLastplayed(movie.getLastplayed());
//        movieNFO.setId(movie.getKodiid());
//        movieNFO.setUniqueid(toUniqueidNFOs(movie.getUniqueids()));
//        movieNFO.setGenre(toValues(movie.getGenres()));
//        movieNFO.setCountry(toValues(movie.getCountries()));
//        movieNFO.setLanguage(toValues(movie.getLanguages()));
//        movieNFO.setSet(toSetNFOs(movie.getSets()));
//        movieNFO.setTag(toValues(movie.getTags()));
//        movieNFO.setCredits(toValues(movie.getWriters()));
//        movieNFO.setDirector(toValues(movie.getDirectors()));
//        movieNFO.setPremiered(movie.getPremiered());
//        movieNFO.setYear(movie.getYear());
//        movieNFO.setStatus(movie.getStatus());
//        movieNFO.setCode(movie.getCode());
//        movieNFO.setAired(movie.getAired());
//        movieNFO.setStudio(toValues(movie.getStudios()));
//        movieNFO.setTrailer(movie.getTrailer());
//        movieNFO.setFileinfo(toFileinfoNFO(movie.getFileinfos()));
//        movieNFO.setActor(toActorNFOs(movie.getActors()));
//        movieNFO.setShowlink(movie.getShowlink());
//        movieNFO.setResume(toResumeNFO(movie.getResumes()));
//        movieNFO.setDateadded(movie.getDateadded());
//        movieNFO.setEpbookmark(movie.getEpbookmark());
//        movieNFO.setCertification(movie.getCertification());
//        movieNFO.setProducer(toActorNFOs(movie.getProducers()));
//        movieNFO.setWatched(movie.getWatched());
//        movieNFO.setTmdbid(movie.getTmdbid());
//        movieNFO.setTmdbCollectionId(movie.getTmdbCollectionId());
//        movieNFO.setSource(movie.getSource());
//        movieNFO.setEdition(movie.getEdition());
//        movieNFO.setOriginalfilename(movie.getOriginalFilename());
//        movieNFO.setUsernote(movie.getUserNote());
//        movieNFO.setNum(movie.getNumber());
//        movieNFO.setWebsite(movie.getWebsite());
//        movieNFO.setAka(toValues(movie.getAkas()));
//        return movieNFO;
//    }

}
