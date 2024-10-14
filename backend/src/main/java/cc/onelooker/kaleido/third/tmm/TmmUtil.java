package cc.onelooker.kaleido.third.tmm;

import cc.onelooker.kaleido.dto.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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
        movieBasicDTO.setThumb(movie.getPoster());
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
        if (StringUtils.isEmpty(comicSeriesDTO.getTitle())) {
            //防止被覆盖
            comicSeriesDTO.setTitle(StringUtils.defaultIfEmpty(comic.getSeries(), comic.getOriginalSeries()));
        }
        //防止被覆盖
        comicSeriesDTO.setOriginalTitle(StringUtils.defaultIfEmpty(comicSeriesDTO.getOriginalTitle(), comic.getOriginalSeries()));
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
        if (CollectionUtils.isNotEmpty(comic.getVolumes())) {
            List<ComicBookDTO> comicBookDTOList = comic.getVolumes().stream().map(TmmUtil::toComicBookDTO).collect(Collectors.toList());
            comicSeriesDTO.setBookList(comicBookDTOList);
        }
        return comicSeriesDTO;
    }

    public static ComicBookDTO toComicBookDTO(Comic.Volume volume) {
        return toComicBookDTO(new ComicBookDTO(), volume);
    }

    public static ComicBookDTO toComicBookDTO(ComicBookDTO comicBookDTO, Comic.Volume volume) {
        if (volume == null) {
            return comicBookDTO;
        }
        String title = StringUtils.defaultIfEmpty(volume.getTitle(), volume.getOriginalTitle());
        title = StringUtils.defaultIfBlank(title, "卷 " + volume.getVolumeNumber());
        comicBookDTO.setTitle(title);
        comicBookDTO.setOriginalTitle(volume.getOriginalTitle());
        comicBookDTO.setBgmId(volume.getBgmId());
        if (StringUtils.isNotEmpty(volume.getBgmId())) {
            comicBookDTO.setWeb("https://bgm.tv/subject/" + volume.getBgmId());
        }
        comicBookDTO.setBookNumber(volume.getVolumeNumber());
        return comicBookDTO;
    }

    public static TvshowShowDTO toTvshowShow(Tvshow tvshow) {
        TvshowShowDTO tvshowShowDTO = new TvshowShowDTO();
        return toTvshowShow(tvshowShowDTO, tvshow);
    }

    public static TvshowShowDTO toTvshowShow(TvshowShowDTO tvshowShowDTO, Tvshow tvshow) {
        tvshowShowDTO.setImdbId(tvshow.getImdbId());
        tvshowShowDTO.setTmdbId(tvshow.getTmdbId());
        tvshowShowDTO.setDoubanId(tvshow.getDoubanId());
        tvshowShowDTO.setTitle(tvshow.getTitle());
        tvshowShowDTO.setOriginalTitle(tvshow.getOriginalTitle());
        tvshowShowDTO.setSummary(tvshow.getPlot());
        tvshowShowDTO.setYear(tvshow.getYear());
        tvshowShowDTO.setOriginallyAvailableAt(tvshow.getPremiered());
        tvshowShowDTO.setRating(tvshow.getAverage());
        tvshowShowDTO.setContentRating(tvshow.getMpaa());
        tvshowShowDTO.setAkaList(tvshow.getAkas());
        tvshowShowDTO.setGenreList(tvshow.getGenres());
        tvshowShowDTO.setThumb(tvshow.getPoster());
        if (CollectionUtils.isNotEmpty(tvshow.getSeasons())) {
            tvshowShowDTO.setSeasonList(tvshow.getSeasons().stream().map(TmmUtil::toTvshowSeason).collect(Collectors.toList()));
        }
        return tvshowShowDTO;
    }

    public static TvshowSeasonDTO toTvshowSeason(Season season) {
        TvshowSeasonDTO tvshowSeasonDTO = new TvshowSeasonDTO();
        return toTvshowSeason(tvshowSeasonDTO, season);
    }

    public static TvshowSeasonDTO toTvshowSeason(TvshowSeasonDTO tvshowSeasonDTO, Season season) {
        tvshowSeasonDTO.setImdbId(season.getImdbId());
        tvshowSeasonDTO.setTmdbId(season.getTmdbId());
        tvshowSeasonDTO.setDoubanId(season.getDoubanId());
        tvshowSeasonDTO.setSeasonIndex(season.getSeasonNumber());
        tvshowSeasonDTO.setTitle(season.getTitle());
        tvshowSeasonDTO.setOriginalTitle(season.getOriginalTitle());
        tvshowSeasonDTO.setSeasonIndex(season.getSeasonNumber());
        tvshowSeasonDTO.setSummary(season.getPlot());
        tvshowSeasonDTO.setYear(season.getYear());
        tvshowSeasonDTO.setOriginallyAvailableAt(season.getPremiered());
        tvshowSeasonDTO.setRating(season.getAverage());
        tvshowSeasonDTO.setThumb(season.getPoster());
        if (CollectionUtils.isNotEmpty(season.getCredits())) {
            tvshowSeasonDTO.setWriterList(season.getCredits().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(season.getDirectors())) {
            tvshowSeasonDTO.setDirectorList(season.getDirectors().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(season.getActors())) {
            tvshowSeasonDTO.setActorList(season.getActors().stream().map(TmmUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(season.getEpisodes())) {
            tvshowSeasonDTO.setEpisodeList(season.getEpisodes().stream().map(TmmUtil::toTvshowEpisode).collect(Collectors.toList()));
        }
        return tvshowSeasonDTO;
    }

    public static TvshowEpisodeDTO toTvshowEpisode(Episode episode) {
        TvshowEpisodeDTO tvshowEpisodeDTO = new TvshowEpisodeDTO();
        return toTvshowEpisode(tvshowEpisodeDTO, episode);
    }

    public static TvshowEpisodeDTO toTvshowEpisode(TvshowEpisodeDTO tvshowEpisodeDTO, Episode episode) {
        if (episode == null) {
            return tvshowEpisodeDTO;
        }
        tvshowEpisodeDTO.setEpisodeIndex(episode.getEpisodeNumber());
        tvshowEpisodeDTO.setSeasonIndex(episode.getSeasonNumber());
        tvshowEpisodeDTO.setTmdbId(episode.getTmdbId());
        tvshowEpisodeDTO.setTitle(episode.getTitle());
        tvshowEpisodeDTO.setSummary(episode.getPlot());
        tvshowEpisodeDTO.setRating(episode.getAverage());
        tvshowEpisodeDTO.setYear(episode.getYear());
        tvshowEpisodeDTO.setDuration(episode.getRuntime());
        tvshowEpisodeDTO.setThumb(episode.getThumb());
        tvshowEpisodeDTO.setOriginallyAvailableAt(episode.getPremiered());
        return tvshowEpisodeDTO;
    }

    public static MusicAlbumDTO toMusicAlbumDTO(Album album) {
        MusicAlbumDTO musicAlbumDTO = new MusicAlbumDTO();
        return toMusicAlbumDTO(musicAlbumDTO, album);
    }

    public static MusicAlbumDTO toMusicAlbumDTO(MusicAlbumDTO musicAlbumDTO, Album album) {
        musicAlbumDTO.setNeteaseId(album.getNeteaseId());
        musicAlbumDTO.setMusicbrainzId(album.getMusicbrainzId());
        musicAlbumDTO.setTitle(album.getTitle());
        musicAlbumDTO.setOriginallyAvailableAt(album.getPublishTime());
        musicAlbumDTO.setSummary(album.getDescription());
        musicAlbumDTO.setYear(StringUtils.substring(album.getPublishTime(), 0, 4));
        musicAlbumDTO.setThumb(album.getPicUrl());
        if (CollectionUtils.isNotEmpty(album.getArtists())) {
            musicAlbumDTO.setArtistList(album.getArtists().stream().map(TmmUtil::toArtistDTO).collect(Collectors.toList()));
            musicAlbumDTO.setArtists(album.getArtists().stream().map(Artist::getName).collect(Collectors.joining("; ")));
        }
        if (CollectionUtils.isNotEmpty(album.getSongs())) {
            musicAlbumDTO.setTrackList(album.getSongs().stream().map(TmmUtil::toMusicTrackDTO).collect(Collectors.toList()));
        }
        return musicAlbumDTO;
    }

    public static MusicTrackDTO toMusicTrackDTO(Song song) {
        MusicTrackDTO musicTrackDTO = new MusicTrackDTO();
        return toMusicTrackDTO(musicTrackDTO, song);
    }

    public static MusicTrackDTO toMusicTrackDTO(MusicTrackDTO musicTrackDTO, Song song) {
        musicTrackDTO.setTitle(song.getTitle());
        musicTrackDTO.setDiscIndex(1);
        musicTrackDTO.setTrackIndex(song.getTrackIndex());
        musicTrackDTO.setDuration(song.getDuration());
        if (CollectionUtils.isNotEmpty(song.getArtists())) {
            musicTrackDTO.setArtists(song.getArtists().stream().map(Artist::getName).collect(Collectors.joining("; ")));
        }
        musicTrackDTO.setNeteaseId(song.getNeteaseId());
        musicTrackDTO.setMusicbrainzId(song.getMusicbrainzId());
        return musicTrackDTO;
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

    private static ArtistDTO toArtistDTO(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setTitle(StringUtils.defaultIfEmpty(artist.getTrans(), artist.getName()));
        artistDTO.setOriginalTitle(artist.getName());
        artistDTO.setThumb(artist.getPicUrl());
        artistDTO.setNeteaseId(artist.getNeteaseId());
        return artistDTO;
    }

}
