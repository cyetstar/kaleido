package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetMovies;
import cc.onelooker.kaleido.plex.resp.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author cyetstar
 * @Date 2023-10-01 16:09:00
 * @Description TODO
 */
@Slf4j
@Component
public class MovieManager {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private MovieCountryService movieCountryService;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieBasicCountryService movieBasicCountryService;

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Autowired
    private PlexApiService plexApiService;

    @Transactional
    public void syncPlexMovieById(Long movieId) {
        GetMovies.Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(metadata);
    }

    @Transactional
    public void syncPlexMovie(GetMovies.Metadata metadata) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(metadata.getRatingKey());
        if (movieBasicDTO == null) {
            movieBasicDTO = new MovieBasicDTO();
            movieBasicDTO.setId(metadata.getRatingKey());
            movieBasicDTO.setStudio(metadata.getStudio());
            movieBasicDTO.setTitle(metadata.getTitle());
            movieBasicDTO.setTitleSort(metadata.getTitleSort());
            movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
            movieBasicDTO.setContentRating(metadata.getContentRating());
            movieBasicDTO.setSummary(metadata.getSummary());
            movieBasicDTO.setRating(metadata.getRating());
            movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
            movieBasicDTO.setYear(metadata.getYear());
            movieBasicDTO.setThumb(metadata.getThumb());
            movieBasicDTO.setArt(metadata.getArt());
            movieBasicDTO.setDuration(metadata.getDuration());
            movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            movieBasicDTO.setAddedAt(metadata.getAddedAt());
            movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieBasicDTO = movieBasicService.insert(movieBasicDTO);
        } else {
            movieBasicDTO.setStudio(metadata.getStudio());
            movieBasicDTO.setTitle(metadata.getTitle());
            movieBasicDTO.setTitleSort(metadata.getTitleSort());
            movieBasicDTO.setOriginalTitle(metadata.getOriginalTitle());
            movieBasicDTO.setContentRating(metadata.getContentRating());
            movieBasicDTO.setSummary(metadata.getSummary());
            movieBasicDTO.setRating(metadata.getRating());
            movieBasicDTO.setLastViewedAt(metadata.getLastViewedAt());
            movieBasicDTO.setYear(metadata.getYear());
            movieBasicDTO.setThumb(metadata.getThumb());
            movieBasicDTO.setArt(metadata.getArt());
            movieBasicDTO.setDuration(metadata.getDuration());
            movieBasicDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            movieBasicDTO.setUpdatedAt(metadata.getUpdatedAt());
            movieBasicService.update(movieBasicDTO);
        }
        syncCountry(metadata.getCountryList(), movieBasicDTO.getId());
        syncGenre(metadata.getGenreList(), movieBasicDTO.getId());
        syncCollection(metadata.getCollectionList(), movieBasicDTO.getId());
        syncActor(metadata.getDirectorList(), movieBasicDTO.getId(), ActorRole.Director);
        syncActor(metadata.getWriterList(), movieBasicDTO.getId(), ActorRole.Director);
        syncActor(metadata.getRoleList(), movieBasicDTO.getId(), ActorRole.Actor);
    }

    private void syncActor(List<Tag> directorList, Long movieId, ActorRole actorRole) {
        if (directorList == null) {
            return;
        }
        for (Tag tag : directorList) {
            MovieActorDTO movieActorDTO = movieActorService.findById(tag.getId());
            if (movieActorDTO == null) {
                movieActorDTO = new MovieActorDTO();
                movieActorDTO.setId(tag.getId());
                movieActorDTO.setName(tag.getTag());
                movieActorDTO.setOriginalName(tag.getTag());
                movieActorDTO = movieActorService.insert(movieActorDTO);
            }
            MovieBasicActorDTO movieBasicActorDTO = movieBasicActorService.findByMovieIdAndActorId(movieId, movieActorDTO.getId());
            if (movieBasicActorDTO == null) {
                movieBasicActorService.insertByMovieIdAndActorIdAndRole(movieId, movieActorDTO.getId(), actorRole.name());
            }
        }
    }

    private void syncCountry(List<Tag> countryList, Long movieId) {
        if (countryList == null) {
            return;
        }
        for (Tag tag : countryList) {
            MovieCountryDTO movieCountryDTO = movieCountryService.findById(tag.getId());
            if (movieCountryDTO == null) {
                movieCountryDTO = movieCountryService.insert(tag.getId(), tag.getTag());
            }
            MovieBasicCountryDTO movieBasicCountryDTO = movieBasicCountryService.findByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            if (movieBasicCountryDTO == null) {
                movieBasicCountryService.insertByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            }
        }
    }

    private void syncGenre(List<Tag> genreList, Long movieId) {
        if (genreList == null) {
            return;
        }
        for (Tag tag : genreList) {
            MovieGenreDTO movieGenreDTO = movieGenreService.findById(tag.getId());
            if (movieGenreDTO == null) {
                movieGenreDTO = movieGenreService.insert(tag.getId(), tag.getTag());
            }
            MovieBasicGenreDTO movieBasicGenreDTO = movieBasicGenreService.findByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            if (movieBasicGenreDTO == null) {
                movieBasicGenreService.insertByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            }
        }
    }

    private void syncCollection(List<Tag> collectionList, Long movieId) {
        if (collectionList == null) {
            return;
        }
        for (Tag tag : collectionList) {
            MovieCollectionDTO movieCollectionDTO = movieCollectionService.findById(tag.getId());
            if (movieCollectionDTO == null) {
                movieCollectionDTO = movieCollectionService.insert(tag.getId(), tag.getTag());
            }
            MovieBasicCollectionDTO movieBasicCollectionDTO = movieBasicCollectionService.findByMovieIdAndCollectionId(movieId, movieCollectionDTO.getId());
            if (movieBasicCollectionDTO == null) {
                movieBasicCollectionService.insertByMovieIdAndCollectionId(movieId, movieCollectionDTO.getId());
            }
        }
    }

}
