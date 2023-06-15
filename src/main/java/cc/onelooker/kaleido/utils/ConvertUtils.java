package cc.onelooker.kaleido.utils;

import cc.onelooker.kaleido.dto.business.*;
import cc.onelooker.kaleido.nfo.*;
import com.zjjcnt.common.util.DateTimeUtils;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertUtils {

    public static MovieDTO convert(MovieNFO movieNFO) {
        if (movieNFO == null) {
            return null;
        }
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDymc(movieNFO.getTitle());
        movieDTO.setYpm(movieNFO.getOriginaltitle());
        movieDTO.setYhpf(movieNFO.getUserrating() != null ? movieNFO.getUserrating().intValue() : null);
        movieDTO.setDyjj(movieNFO.getPlot());
        movieDTO.setDyby(movieNFO.getTagline());
        movieDTO.setYpsc(movieNFO.getRuntime());
        movieDTO.setGwdz(movieNFO.getWebsite());
        movieDTO.setDydj(movieNFO.getMpaa());
        movieDTO.setSynf(StringUtils.defaultString(StringUtils.substring(movieNFO.getPremiered(), 0, 4), movieNFO.getYear()));
        movieDTO.setSyrq(DateTimeUtils.formatDateTime(movieNFO.getPremiered(), "yyyy-MM-dd", DateTimeUtils.DATE_PATTERN));
        List<MovieCountryDTO> movieCountryDTOList = toMovieCountryDTOList(movieNFO.getCountries());
        List<MovieLanguageDTO> movieLanguageDTOList = toMovieLanguageDTOList(movieNFO.getLanguages());
        List<MovieGenreDTO> movieGenreDTOList = toMovieGenreDTOList(movieNFO.getGenres());
        List<MovieSetDTO> movieSetDTOList = toMovieSetDTOList(movieNFO.getSets());
        List<MovieRatingDTO> movieRatingDTOList = toMoveRatingDTOList(movieNFO.getRatings());
        List<MovieUniqueidDTO> movieUniqueidDTOList = toMovieUniqueidDTOList(movieNFO.getUniqueids());
        List<MovieAkaDTO> movieAkaDTOList = toMovieAkaDTOList(movieNFO.getAkas());
        List<MovieTagDTO> movieTagDTOList = toMovieTagDTOList(movieNFO.getTags());
        List<MovieActorDTO> directorList = toMovieActorDTOList(movieNFO.getDirectors());
        List<MovieActorDTO> writerList = toMovieActorDTOList(movieNFO.getCredits());
        List<MovieActorDTO> actorList = toMovieActorDTOList(movieNFO.getActors());
        MovieFileDTO movieFileDTO = toMovieFileDTO(movieNFO.getFileinfo());

        movieDTO.setMovieCountryDTOList(movieCountryDTOList);
        movieDTO.setMovieLanguageDTOList(movieLanguageDTOList);
        movieDTO.setMovieGenreDTOList(movieGenreDTOList);
        movieDTO.setMovieSetDTOList(movieSetDTOList);
        movieDTO.setMovieRatingDTOList(movieRatingDTOList);
        movieDTO.setMovieUniqueidDTOList(movieUniqueidDTOList);
        movieDTO.setMovieAkaDTOList(movieAkaDTOList);
        movieDTO.setMovieTagDTOList(movieTagDTOList);
        movieDTO.setMovieFileDTO(movieFileDTO);

        movieDTO.setDirectorList(directorList);
        movieDTO.setWriterList(writerList);
        movieDTO.setActorList(actorList);
        return movieDTO;
    }

    private static List<MovieActorDTO> toMovieActorDTOList(List<?> actorList) {
        if (actorList == null) {
            return null;
        }
        return actorList.stream().map(s -> {
            MovieActorDTO movieCountryDTO = new MovieActorDTO();
            if (s instanceof String) {
                movieCountryDTO.setXm((String) s);
            } else if (s instanceof ActorNFO) {
                ActorNFO actorNFO = (ActorNFO) s;
                movieCountryDTO.setXm(actorNFO.getName());
            }
            return movieCountryDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieCountryDTO> toMovieCountryDTOList(List<String> countries) {
        if (countries == null) {
            return null;
        }
        return countries.stream().map(s -> {
            MovieCountryDTO movieCountryDTO = new MovieCountryDTO();
            movieCountryDTO.setMc(s);
            return movieCountryDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieLanguageDTO> toMovieLanguageDTOList(List<String> languages) {
        if (languages == null) {
            return null;
        }
        return languages.stream().map(s -> {
            MovieLanguageDTO movieLanguageDTO = new MovieLanguageDTO();
            movieLanguageDTO.setMc(s);
            return movieLanguageDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieGenreDTO> toMovieGenreDTOList(List<String> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream().map(s -> {
            MovieGenreDTO movieGenreDTO = new MovieGenreDTO();
            movieGenreDTO.setMc(s);
            return movieGenreDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieAkaDTO> toMovieAkaDTOList(List<String> akas) {
        if (akas == null) {
            return null;
        }
        return akas.stream().map(s -> {
            MovieAkaDTO movieAkaDTO = new MovieAkaDTO();
            movieAkaDTO.setDymc(s);
            return movieAkaDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieTagDTO> toMovieTagDTOList(List<String> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(s -> {
            MovieTagDTO movieTagDTO = new MovieTagDTO();
            movieTagDTO.setMc(s);
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

    private static List<MovieRatingDTO> toMoveRatingDTOList(List<RatingNFO> ratingNFOList) {
        if (ratingNFOList == null) {
            return null;
        }
        return ratingNFOList.stream().filter(s -> s != null).map(s -> {
            MovieRatingDTO movieRatingDTO = new MovieRatingDTO();
            movieRatingDTO.setPjf(new BigDecimal(s.getValue()));
            movieRatingDTO.setTps(s.getVotes());
            movieRatingDTO.setPflx(s.getName());
            movieRatingDTO.setZdz(s.getMax());
            movieRatingDTO.setSfmr(s.getDef() ? Constants.YES : Constants.NO);
            return movieRatingDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieSetDTO> toMovieSetDTOList(List<SetNFO> setNFOList) {
        if (setNFOList == null) {
            return null;
        }
        return setNFOList.stream().filter(s -> s != null && StringUtils.isNotBlank(s.getName())).map(s -> {
            MovieSetDTO movieSetDTO = new MovieSetDTO();
            movieSetDTO.setMc(s.getName());
            movieSetDTO.setJhsm(s.getOverview());
            return movieSetDTO;
        }).collect(Collectors.toList());
    }

    private static List<MovieUniqueidDTO> toMovieUniqueidDTOList(List<UniqueidNFO> uniqueidNFOList) {
        if (uniqueidNFOList == null) {
            return null;
        }
        return uniqueidNFOList.stream().map(s -> {
            MovieUniqueidDTO movieUniqueidDTO = new MovieUniqueidDTO();
            movieUniqueidDTO.setBslx(s.getType());
            movieUniqueidDTO.setSfmr(s.getDef() ? Constants.YES : Constants.NO);
            movieUniqueidDTO.setUid(s.getValue());
            return movieUniqueidDTO;
        }).collect(Collectors.toList());
    }

    private static MovieFileDTO toMovieFileDTO(FileinfoNFO fileinfoNFO) {
        if (fileinfoNFO == null || fileinfoNFO.getStreamdetails() == null) {
            return null;
        }
        MovieFileDTO movieFileDTO = new MovieFileDTO();
        VideoNFO videoNFO = fileinfoNFO.getStreamdetails().getVideo();
        List<AudioNFO> audioNFOList = fileinfoNFO.getStreamdetails().getAudio();
        List<SubtitleNFO> subtitleNFOList = fileinfoNFO.getStreamdetails().getSubtitle();
        if (videoNFO != null) {
            MovieFileVideoDTO movieFileVideoDTO = new MovieFileVideoDTO();
            movieFileVideoDTO.setCodec(videoNFO.getCodec());
            movieFileVideoDTO.setAspect(videoNFO.getAspect());
            if (StringUtils.isNotEmpty(videoNFO.getWidth())) {
                movieFileVideoDTO.setWidth(Integer.valueOf(videoNFO.getWidth()));
            }
            if (StringUtils.isNotEmpty(videoNFO.getHeight())) {
                movieFileVideoDTO.setHeight(Integer.valueOf(videoNFO.getHeight()));
            }
            if (StringUtils.isNotEmpty(videoNFO.getDurationinseconds())) {
                movieFileVideoDTO.setDurationinseconds(Integer.valueOf(videoNFO.getDurationinseconds()));
            }
            movieFileVideoDTO.setStereomode(videoNFO.getStereomode());
            movieFileDTO.setMovieFileVideoDTO(movieFileVideoDTO);
        }
        if (audioNFOList != null) {
            List<MovieFileAudioDTO> movieFileAudioDTOList = audioNFOList.stream().map(s -> {
                MovieFileAudioDTO movieFileAudioDTO = new MovieFileAudioDTO();
                movieFileAudioDTO.setCodec(s.getCodec());
                movieFileAudioDTO.setLanguage(s.getLanguage());
                movieFileAudioDTO.setChannels(s.getChannels());
                return movieFileAudioDTO;
            }).collect(Collectors.toList());
            movieFileDTO.setMovieFileAudioDTOList(movieFileAudioDTOList);
        }
        if (subtitleNFOList != null) {
            List<MovieFileSubtitleDTO> movieFileSubtitleDTOList = subtitleNFOList.stream().filter(s -> StringUtils.isNotBlank(s.getLanguage())).map(s -> {
                MovieFileSubtitleDTO movieFileSubtitleDTO = new MovieFileSubtitleDTO();
                movieFileSubtitleDTO.setLanuage(s.getLanguage());
                return movieFileSubtitleDTO;
            }).collect(Collectors.toList());
            movieFileDTO.setMovieFileSubtitleDTOList(movieFileSubtitleDTOList);
        }
        return movieFileDTO;
    }

}
