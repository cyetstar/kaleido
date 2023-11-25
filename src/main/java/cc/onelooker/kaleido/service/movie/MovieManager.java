package cc.onelooker.kaleido.service.movie;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetMovies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author xiadawei
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
    private MovieBasicCountryService movieBasicCountryService;

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private PlexApiService plexApiService;

    @Transactional
    public void syncPlexMovieById(String libraryPath, Long movieId) {
        GetMovies.Metadata metadata = plexApiService.findMovieById(movieId);
        syncPlexMovie(libraryPath, metadata);
    }

    @Transactional
    public void syncPlexMovie(String libraryPath, GetMovies.Metadata metadata) {
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
    }

    private void syncCountry(List<GetMovies.Tag> countryList, Long movieId) {
        if (countryList == null) {
            return;
        }
        for (GetMovies.Tag tag : countryList) {
            MovieCountryDTO movieCountryDTO = movieCountryService.findByTag(tag.getTag());
            if (movieCountryDTO == null) {
                movieCountryDTO = movieCountryService.insertByTag(tag.getTag());
            }
            MovieBasicCountryDTO movieBasicCountryDTO = movieBasicCountryService.findByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            if (movieBasicCountryDTO == null) {
                movieBasicCountryService.insertByMovieIdAndCountryId(movieId, movieCountryDTO.getId());
            }
        }
    }

    private void syncGenre(List<GetMovies.Tag> genreList, Long movieId) {
        if (genreList == null) {
            return;
        }
        for (GetMovies.Tag tag : genreList) {
            MovieGenreDTO movieGenreDTO = movieGenreService.findByTag(tag.getTag());
            if (movieGenreDTO == null) {
                movieGenreDTO = movieGenreService.insertByTag(tag.getTag());
            }
            MovieBasicGenreDTO movieBasicGenreDTO = movieBasicGenreService.findByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            if (movieBasicGenreDTO == null) {
                movieBasicGenreService.insertByMovieIdAndGenreId(movieId, movieGenreDTO.getId());
            }
        }
    }

    private void syncCollection(List<GetMovies.Tag> collectionList, Long movieId) {
        if (collectionList == null) {
            return;
        }
        for (GetMovies.Tag tag : collectionList) {
            MovieCollectionDTO movieCollectionDTO = movieCollectionService.findByTitle(tag.getTag());
            if (movieCollectionDTO == null) {
                movieCollectionDTO = movieCollectionService.insertByTitle(tag.getTag());
            }
            MovieBasicCollectionDTO movieBasicCollectionDTO = movieBasicCollectionService.findByMovieIdAndCollectionId(movieId, movieCollectionDTO.getId());
            if (movieBasicCollectionDTO == null) {
                movieBasicCollectionService.insertByMovieIdAndCollectionId(movieId, movieCollectionDTO.getId());
            }
        }
    }

}
