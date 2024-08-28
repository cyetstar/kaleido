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
        MovieBasicDTO movieBasicDTO = new MovieBasicDTO();
        movieBasicDTO.setDoubanId(movie.getDoubanId());
        movieBasicDTO.setImdbId(movie.getImdbId());
        movieBasicDTO.setTmdbId(movie.getTmdbId());
        movieBasicDTO.setTitle(movie.getTitle());
        movieBasicDTO.setOriginalTitle(movie.getOriginalTitle());
        movieBasicDTO.setYear(movie.getYear());
        movieBasicDTO.setRating(movie.getAverage());
        movieBasicDTO.setSummary(movie.getPlot());
        movieBasicDTO.setContentRating(movie.getMpaa());
        movieBasicDTO.setGenreList(movie.getGenres());
        movieBasicDTO.setCountryList(movie.getCountries());
        movieBasicDTO.setLanguageList(movie.getLanguages());
        movieBasicDTO.setAkaList(movie.getAkas());
        movieBasicDTO.setTagList(movie.getTags());
        if (movie.getDirectors() != null) {
            movieBasicDTO.setDirectorList(movie.getDirectors().stream().map(TmmUtil::toMovieActorDTO).collect(Collectors.toList()));
        }
        if (movie.getWriters() != null) {
            movieBasicDTO.setWriterList(movie.getWriters().stream().map(TmmUtil::toMovieActorDTO).collect(Collectors.toList()));
        }
        if (movie.getActors() != null) {
            movieBasicDTO.setActorList(movie.getActors().stream().map(TmmUtil::toMovieActorDTO).collect(Collectors.toList()));
        }
        movieBasicDTO.setPoster(movie.getPoster());
        return movieBasicDTO;
    }

    public static ComicSeriesDTO toComicSeriesDTO(Comic comic) {
        if (comic == null) {
            return null;
        }
        ComicSeriesDTO comicSeriesDTO = new ComicSeriesDTO();
        comicSeriesDTO.setTitle(StringUtils.defaultIfEmpty(comic.getSeries(), comic.getOriginalSeries()));
        comicSeriesDTO.setOriginalTitle(comic.getOriginalSeries());
        comicSeriesDTO.setBookCount(comic.getVolumeCount());
        comicSeriesDTO.setYear(comic.getYear());
        comicSeriesDTO.setSummary(comic.getSummary());
        if (comic.getAuthors() != null) {
            comicSeriesDTO.setWriterList(comic.getAuthors().stream().filter(s -> StringUtils.equals(s.getRole(), "作者")).map(TmmUtil::toComicAuthorDTO).collect(Collectors.toList()));
            comicSeriesDTO.setPencillerList(comic.getAuthors().stream().filter(s -> StringUtils.equals(s.getRole(), "作画")).map(TmmUtil::toComicAuthorDTO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(comic.getPublishers())) {
            comic.getPublishers().stream().findFirst().ifPresent(comicSeriesDTO::setPublisher);
        }
        if (comic.getAverage() != null) {
            comicSeriesDTO.setRating(Float.parseFloat(comic.getAverage()));
        }
        comicSeriesDTO.setTagList(comic.getTags());
        comicSeriesDTO.setAlternateTitleList(comic.getAkas());
        comicSeriesDTO.setBgmId(comic.getBgmId());
        if (comic.getTags().stream().anyMatch(s -> StringUtils.equalsAny(s, "完结", "已完结", "全一卷"))) {
            comicSeriesDTO.setStatus("ENDED");
        } else {
            comicSeriesDTO.setStatus("ONGOING");
        }
        return comicSeriesDTO;
    }

    public static ComicBookDTO toComicBookDTO(Comic.Volume volume) {
        if (volume == null) {
            return null;
        }
        ComicBookDTO comicBookDTO = new ComicBookDTO();
        comicBookDTO.setTitle(volume.getTitle());
        comicBookDTO.setOriginalTitle(volume.getOriginalTitle());
        comicBookDTO.setBgmId(volume.getBgmId());
        comicBookDTO.setWeb("https://bgm.tv/subject/" + volume.getBgmId());
        return comicBookDTO;
    }

    private static MovieActorDTO toMovieActorDTO(Actor actor) {
        MovieActorDTO movieActorDTO = new MovieActorDTO();
        movieActorDTO.setName(actor.getCnName());
        movieActorDTO.setOriginalName(actor.getEnName());
        movieActorDTO.setPlayRole(actor.getRole());
        movieActorDTO.setThumb(actor.getThumb());
        movieActorDTO.setDoubanId(actor.getDoubanId());
        return movieActorDTO;
    }

    private static ComicAuthorDTO toComicAuthorDTO(Author author) {
        ComicAuthorDTO comicAuthorDTO = new ComicAuthorDTO();
        comicAuthorDTO.setName(author.getName());
        return comicAuthorDTO;
    }
}
