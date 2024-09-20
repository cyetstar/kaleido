package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.dto.*;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * @Author xiadawei
 * @Date 2024-09-02 20:49:00
 * @Description TODO
 */
public class PlexUtil {

    public static void toMovieBasicDTO(MovieBasicDTO movieBasicDTO, Metadata metadata) {
        movieBasicDTO.setId(metadata.getRatingKey());
        movieBasicDTO.setTitle(metadata.getTitle());
        movieBasicDTO.setSortTitle(metadata.getTitleSort());
        movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
        movieBasicDTO.setContentRating(metadata.getContentRating());
        movieBasicDTO.setStudio(metadata.getStudio());
        movieBasicDTO.setSummary(metadata.getSummary());
        movieBasicDTO.setRating(metadata.getRating());
        movieBasicDTO.setYear(metadata.getYear());
        movieBasicDTO.setThumb(metadata.getThumb());
        movieBasicDTO.setArt(metadata.getArt());
        movieBasicDTO.setDuration(metadata.getDuration());
        movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
        movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        String fullPath = FilenameUtils.getFullPath(metadata.getMedia().getPart().getFile());
        Path path = KaleidoUtils.getMovieBasicPath(fullPath);
        movieBasicDTO.setPath(path.toString());
        movieBasicDTO.setAddedAt(metadata.getAddedAt());
        movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
        if (metadata.getCountryList() != null) {
            movieBasicDTO.setCountryList(metadata.getCountryList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getGenreList() != null) {
            movieBasicDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getDirectorList() != null) {
            movieBasicDTO.setDirectorList(metadata.getDirectorList().stream().map(PlexUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (metadata.getWriterList() != null) {
            movieBasicDTO.setWriterList(metadata.getWriterList().stream().map(PlexUtil::toActorDTO).collect(Collectors.toList()));
        }
        if (metadata.getRoleList() != null) {
            movieBasicDTO.setActorList(metadata.getRoleList().stream().map(PlexUtil::toActorDTO).collect(Collectors.toList()));
        }
    }

    public static void toTvshowShowDTO(TvshowShowDTO tvshowShowDTO, Metadata metadata) {
        tvshowShowDTO.setId(metadata.getRatingKey());
        tvshowShowDTO.setTitle(metadata.getTitle());
        tvshowShowDTO.setStudio(metadata.getStudio());
        tvshowShowDTO.setContentRating(metadata.getContentRating());
        tvshowShowDTO.setSummary(metadata.getSummary());
        tvshowShowDTO.setRating(metadata.getRating());
        //plex无法维护年份，即使后期nfo文件有年份，也无法覆盖
        tvshowShowDTO.setYear(StringUtils.defaultIfEmpty(tvshowShowDTO.getYear(), metadata.getYear()));
        tvshowShowDTO.setThumb(metadata.getThumb());
        tvshowShowDTO.setArt(metadata.getArt());
        tvshowShowDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        tvshowShowDTO.setTotalSeasons(metadata.getChildCount());
        Path path = KaleidoUtils.getTvshowBasicPath(metadata.getLocation().getPath());
        tvshowShowDTO.setPath(path.toString());
        tvshowShowDTO.setAddedAt(metadata.getAddedAt());
        tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
        if (metadata.getGenreList() != null) {
            tvshowShowDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
    }

    public static void toTvshowSeasonDTO(TvshowSeasonDTO tvshowSeasonDTO, Metadata metadata) {
        tvshowSeasonDTO.setId(metadata.getRatingKey());
        tvshowSeasonDTO.setShowId(metadata.getParentRatingKey());
        tvshowSeasonDTO.setTitle(metadata.getTitle());
        tvshowSeasonDTO.setSummary(metadata.getSummary());
        tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
        tvshowSeasonDTO.setThumb(metadata.getThumb());
        tvshowSeasonDTO.setArt(metadata.getArt());
        tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
        tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
    }

    public static void toTvshowEpisodeDTO(TvshowEpisodeDTO tvshowEpisodeDTO, Metadata metadata) {
        tvshowEpisodeDTO.setId(metadata.getRatingKey());
        tvshowEpisodeDTO.setShowId(metadata.getGrandparentRatingKey());
        tvshowEpisodeDTO.setSeasonId(metadata.getParentRatingKey());
        tvshowEpisodeDTO.setTitle(metadata.getTitle());
        tvshowEpisodeDTO.setContentRating(metadata.getContentRating());
        tvshowEpisodeDTO.setSummary(metadata.getSummary());
        tvshowEpisodeDTO.setSeasonIndex(metadata.getParentIndex());
        tvshowEpisodeDTO.setEpisodeIndex(metadata.getIndex());
        tvshowEpisodeDTO.setRating(metadata.getRating());
        tvshowEpisodeDTO.setYear(metadata.getYear());
        tvshowEpisodeDTO.setThumb(metadata.getThumb());
        tvshowEpisodeDTO.setArt(metadata.getArt());
        String filename = FilenameUtils.getName(metadata.getMedia().getPart().getFile());
        tvshowEpisodeDTO.setFilename(filename);
        tvshowEpisodeDTO.setDuration(metadata.getDuration());
        tvshowEpisodeDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        tvshowEpisodeDTO.setAddedAt(metadata.getAddedAt());
        tvshowEpisodeDTO.setUpdatedAt(metadata.getUpdatedAt());
    }

    public static void toArtistDTO(ArtistDTO artistDTO, Metadata metadata) {
        artistDTO.setId(metadata.getRatingKey());
        artistDTO.setTitle(metadata.getTitle());
        artistDTO.setThumb(metadata.getThumb());
        artistDTO.setSummary(metadata.getSummary());
        artistDTO.setAddedAt(metadata.getAddedAt());
        artistDTO.setUpdatedAt(metadata.getUpdatedAt());
        if (metadata.getStyleList() != null) {
            artistDTO.setStyleList(metadata.getStyleList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getGenreList() != null) {
            artistDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
    }

    public static void toMusicAblumDTO(MusicAlbumDTO musicAlbumDTO, Metadata metadata, Metadata childMetadata) {
        musicAlbumDTO.setId(metadata.getRatingKey());
        musicAlbumDTO.setTitle(metadata.getTitle());
        musicAlbumDTO.setArtists(metadata.getParentTitle());
        musicAlbumDTO.setSummary(metadata.getSummary());
        musicAlbumDTO.setThumb(metadata.getThumb());
        musicAlbumDTO.setYear(metadata.getYear());
        musicAlbumDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
        musicAlbumDTO.setAddedAt(metadata.getAddedAt());
        musicAlbumDTO.setUpdatedAt(metadata.getUpdatedAt());
        String fullPath = FilenameUtils.getFullPath(childMetadata.getMedia().getPart().getFile());
        Path path = KaleidoUtils.getMusicBasicPath(fullPath);
        musicAlbumDTO.setPath(path.toString());
        if (metadata.getStyleList() != null) {
            musicAlbumDTO.setStyleList(metadata.getStyleList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getGenreList() != null) {
            musicAlbumDTO.setGenreList(metadata.getGenreList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        if (metadata.getMoodList() != null) {
            musicAlbumDTO.setMoodList(metadata.getMoodList().stream().map(Tag::getTag).collect(Collectors.toList()));
        }
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setId(metadata.getParentRatingKey());
        artistDTO.setTitle(metadata.getParentTitle());
        artistDTO.setThumb(metadata.getParentThumb());
        musicAlbumDTO.setArtistList(Lists.newArrayList(artistDTO));
    }

    public static void toMusicTrackDTO(MusicTrackDTO musicTrackDTO, Metadata metadata) {
        musicTrackDTO.setId(metadata.getRatingKey());
        musicTrackDTO.setTitle(metadata.getTitle());
        musicTrackDTO.setDuration(metadata.getDuration());
        musicTrackDTO.setAlbumId(metadata.getParentRatingKey());
        musicTrackDTO.setFormat(metadata.getMedia().getContainer());
        String filename = FilenameUtils.getName(metadata.getMedia().getPart().getFile());
        musicTrackDTO.setFilename(filename);
        musicTrackDTO.setAddedAt(metadata.getAddedAt());
        musicTrackDTO.setUpdatedAt(metadata.getUpdatedAt());
    }

    private static ActorDTO toActorDTO(Tag tag) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(tag.getTag());
        actorDTO.setPlayRole(tag.getRole());
        actorDTO.setThumb(tag.getThumb());
        return actorDTO;
    }

}
