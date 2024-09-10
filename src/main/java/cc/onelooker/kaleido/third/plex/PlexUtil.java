package cc.onelooker.kaleido.third.plex;

import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import org.apache.commons.io.FilenameUtils;

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
        Path path = KaleidoUtils.getMovieBasicPath(FilenameUtils.getFullPath(metadata.getMedia().getPart().getFile()));
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

    private static ActorDTO toActorDTO(Tag tag) {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setName(tag.getTag());
        actorDTO.setPlayRole(tag.getRole());
        actorDTO.setThumb(tag.getThumb());
        return actorDTO;
    }
}
