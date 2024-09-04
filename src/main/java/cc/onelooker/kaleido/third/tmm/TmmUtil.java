package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.dto.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-08-27 23:34:00
 * @Description TODO
 */
public class TmmUtil {

    public static MovieBasicDTO toMovieBasicDTO(Movie movie) {
        return toMovieBasicDTO(new MovieBasicDTO(), movie);
    }

    public static MovieBasicDTO toMovieBasicDTO(MovieBasicDTO movieBasicDTO, Movie movie) {
        if (movie == null) {
            return movieBasicDTO;
        }
        movieBasicDTO.setDoubanId(movie.getDoubanId());
        movieBasicDTO.setImdbId(movie.getImdbId());
        movieBasicDTO.setTmdbId(movie.getTmdbId());
        movieBasicDTO.setTitle(StringUtils.defaultIfEmpty(movie.getTitle(), movie.getOriginalTitle()));
        movieBasicDTO.setOriginalTitle(movie.getOriginalTitle());
        movieBasicDTO.setYear(movie.getYear());
        movieBasicDTO.setRating(movie.getAverage());
        movieBasicDTO.setSummary(movie.getPlot());
        movieBasicDTO.setContentRating(movie.getMpaa());
        movieBasicDTO.setPoster(movie.getPoster());
        movieBasicDTO.setGenreList(movie.getGenres());
        movieBasicDTO.setCountryList(movie.getCountries());
        movieBasicDTO.setLanguageList(movie.getLanguages());
        movieBasicDTO.setAkaList(movie.getAkas());
        movieBasicDTO.setTagList(movie.getTags());
        if (movie.getDirectors() != null) {
            movieBasicDTO.setDirectorList(movie.getDirectors().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (movie.getWriters() != null) {
            movieBasicDTO.setWriterList(movie.getWriters().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (movie.getActors() != null) {
            movieBasicDTO.setActorList(movie.getActors().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        return movieBasicDTO;
    }

    public static ComicSeriesDTO toComicSeriesDTO(Comic comic) {
        return toComicSeriesDTO(new ComicSeriesDTO(), comic);
    }

    public static ComicSeriesDTO toComicSeriesDTO(ComicSeriesDTO comicSeriesDTO, Comic comic) {
        if (comic == null) {
            return comicSeriesDTO;
        }
        comicSeriesDTO.setTitle(StringUtils.defaultIfEmpty(comic.getSeries(), comic.getOriginalSeries()));
        comicSeriesDTO.setOriginalTitle(comic.getOriginalSeries());
        comicSeriesDTO.setBookCount(comic.getVolumeCount());
        comicSeriesDTO.setYear(comic.getYear());
        comicSeriesDTO.setSummary(comic.getSummary());
        if (CollectionUtils.isNotEmpty(comic.getPublishers())) {
            comic.getPublishers().stream().findFirst().ifPresent(comicSeriesDTO::setPublisher);
        }
        if (comic.getAverage() != null) {
            comicSeriesDTO.setRating(Float.parseFloat(comic.getAverage()));
        }
        comicSeriesDTO.setBgmId(comic.getBgmId());
        if (StringUtils.isEmpty(comicSeriesDTO.getStatus())) {
            if (comic.getTags().stream().anyMatch(s -> StringUtils.equalsAny(s, "完结", "已完结", "全一卷"))) {
                comicSeriesDTO.setStatus("ENDED");
            } else {
                comicSeriesDTO.setStatus("ONGOING");
            }
        }
        comicSeriesDTO.setTagList(comic.getTags());
        comicSeriesDTO.setAlternateTitleList(comic.getAkas());
        if (comic.getAuthors() != null) {
            comicSeriesDTO.setWriterList(comic.getAuthors().stream().filter(s -> StringUtils.equals(s.getRole(), "作者")).map(TmmUtil::toAuthorDTO).collect(Collectors.toList()));
            comicSeriesDTO.setPencillerList(comic.getAuthors().stream().filter(s -> StringUtils.equals(s.getRole(), "作画")).map(TmmUtil::toAuthorDTO).collect(Collectors.toList()));
        }
        return comicSeriesDTO;
    }

    public static ComicBookDTO toComicBookDTO(Comic.Volume volume, Integer number) {
        return toComicBookDTO(new ComicBookDTO(), volume, number);
    }

    public static ComicBookDTO toComicBookDTO(ComicBookDTO comicBookDTO, Comic.Volume volume, Integer number) {
        if (volume == null) {
            return comicBookDTO;
        }
        comicBookDTO.setTitle(StringUtils.defaultIfBlank(StringUtils.defaultIfEmpty(volume.getTitle(), volume.getOriginalTitle()), number != null ? "卷" + number : "全一卷"));
        comicBookDTO.setOriginalTitle(volume.getOriginalTitle());
        comicBookDTO.setBgmId(volume.getBgmId());
        comicBookDTO.setWeb("https://bgm.tv/subject/" + volume.getBgmId());
        comicBookDTO.setBookNumber(number);
        return comicBookDTO;
    }

    private static ActorDTO toActorDTO(Actor actor) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(actor.getCnName());
        actorDTO.setOriginalName(actor.getEnName());
        actorDTO.setPlayRole(actor.getRole());
        actorDTO.setThumb(actor.getThumb());
        actorDTO.setDoubanId(actor.getDoubanId());
        return actorDTO;
    }

    private static AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    public static TvshowShowDTO toTvshowShow(Tvshow tvshow) {
        return null;
    }

    public static TvshowSeasonDTO toTvshowSeason(Season season) {
        TvshowSeasonDTO tvshowSeasonDTO = new TvshowSeasonDTO();
        tvshowSeasonDTO.setTitle(season.getTitle());
        tvshowSeasonDTO.setOriginalTitle(season.getOriginalTitle());
        tvshowSeasonDTO.setSeasonIndex(season.getSeasonNumber());
        tvshowSeasonDTO.setSummary(season.getPlot());
        tvshowSeasonDTO.setYear(season.getYear());
//        tvshowSeasonDTO.setOriginallyAvailableAt(season.getPremiered());
//        List<RatingNFO> ratingNFOList = Lists.newArrayList();
//        CollectionUtils.addIgnoreNull(ratingNFOList, toRatingNFO(season.getAverage()));
//        tvshowSeasonDTO.setRatings(ratingNFOList);
//        List<UniqueidNFO> uniqueidNFOList = Lists.newArrayList();
//        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.douban, season.getDoubanId()));
//        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.imdb, season.getImdbId()));
//        CollectionUtils.addIgnoreNull(uniqueidNFOList, toUniqueidNFO(SourceType.tmdb, season.getTmdbId()));
//        tvshowSeasonDTO.setUniqueids(uniqueidNFOList);
//        if (CollectionUtils.isNotEmpty(season.getCredits())) {
//            tvshowSeasonDTO.setCredits(season.getCredits().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
//        }
//        if (CollectionUtils.isNotEmpty(season.getDirectors())) {
//            tvshowSeasonDTO.setDirectors(season.getDirectors().stream().map(s -> StringUtils.defaultString(s.getCnName(), s.getEnName())).collect(Collectors.toList()));
//        }
//        tvshowSeasonDTO.setActors(toActorNFOs(season.getActors()));
//        tvshowSeasonDTO.setAkas(tvshow.getAkas());
//        tvshowSeasonDTO.setGenres(tvshow.getGenres());
//        tvshowSeasonDTO.setMpaa(tvshow.getMpaa());
//        tvshowSeasonDTO.setStudios(tvshow.getStudios());
        return tvshowSeasonDTO;
    }
}
