package cc.onelooker.kaleido.service.tvshow;

import cc.onelooker.kaleido.dto.tvshow.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.plex.GetEpisodes;
import cc.onelooker.kaleido.third.plex.GetSeasons;
import cc.onelooker.kaleido.third.plex.GetTvshows;
import cc.onelooker.kaleido.third.plex.Tag;
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
public class TvshowManager {

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Autowired
    private TvshowGenreService tvshowGenreService;

    @Autowired
    private TvshowActorService tvshowActorService;

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Autowired
    private TvshowShowActorService tvshowShowActorService;

    @Autowired
    private TvshowEpisodeActorService tvshowEpisodeActorService;

    @Autowired
    private PlexApiService plexApiService;

    @Transactional
    public void syncPlexEpisodeById(Long showId) {
        GetEpisodes.Metadata metadata = plexApiService.findEpisodeById(showId);
        syncPlexEpisode(metadata);
    }

    @Transactional
    public void syncPlexEpisode(GetEpisodes.Metadata metadata) {
        TvshowEpisodeDTO tvshowEpisodeDTO = tvshowEpisodeService.findById(metadata.getRatingKey());
        if (tvshowEpisodeDTO == null) {
            tvshowEpisodeDTO = new TvshowEpisodeDTO();
            tvshowEpisodeDTO.setId(metadata.getRatingKey());
            tvshowEpisodeDTO.setSeasonId(metadata.getParentRatingKey());
            tvshowEpisodeDTO.setTitle(metadata.getTitle());
            tvshowEpisodeDTO.setContentRating(metadata.getContentRating());
            tvshowEpisodeDTO.setSummary(metadata.getSummary());
            tvshowEpisodeDTO.setEpisodeIndex(metadata.getIndex());
            tvshowEpisodeDTO.setRating(metadata.getRating());
            tvshowEpisodeDTO.setYear(metadata.getYear());
            tvshowEpisodeDTO.setThumb(metadata.getThumb());
            tvshowEpisodeDTO.setArt(metadata.getArt());
            tvshowEpisodeDTO.setDuration(metadata.getDuration());
            tvshowEpisodeDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            tvshowEpisodeDTO.setAddedAt(metadata.getAddedAt());
            tvshowEpisodeDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowEpisodeDTO = tvshowEpisodeService.insert(tvshowEpisodeDTO);
        } else {
            tvshowEpisodeDTO.setTitle(metadata.getTitle());
            tvshowEpisodeDTO.setContentRating(metadata.getContentRating());
            tvshowEpisodeDTO.setSummary(metadata.getSummary());
            tvshowEpisodeDTO.setEpisodeIndex(metadata.getIndex());
            tvshowEpisodeDTO.setRating(metadata.getRating());
            tvshowEpisodeDTO.setYear(metadata.getYear());
            tvshowEpisodeDTO.setThumb(metadata.getThumb());
            tvshowEpisodeDTO.setArt(metadata.getArt());
            tvshowEpisodeDTO.setDuration(metadata.getDuration());
            tvshowEpisodeDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            tvshowEpisodeDTO.setAddedAt(metadata.getAddedAt());
            tvshowEpisodeDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowEpisodeService.update(tvshowEpisodeDTO);
        }
        syncPlexActor(metadata.getDirectorList(), null, tvshowEpisodeDTO.getId(), ActorRole.Director);
        syncPlexActor(metadata.getWriterList(), null, tvshowEpisodeDTO.getId(), ActorRole.Writer);

        syncPlexSeason(metadata.getParentRatingKey());
    }

    private void syncPlexShow(Long tvshowId) {
        GetTvshows.Metadata metadata = plexApiService.findTvshowById(tvshowId);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowId);
        if (tvshowShowDTO == null) {
            tvshowShowDTO = new TvshowShowDTO();
            tvshowShowDTO.setId(metadata.getRatingKey());
            tvshowShowDTO.setTitle(metadata.getTitle());
            tvshowShowDTO.setStudio(metadata.getStudio());
            tvshowShowDTO.setContentRating(metadata.getContentRating());
            tvshowShowDTO.setSummary(metadata.getSummary());
            tvshowShowDTO.setRating(metadata.getRating());
            tvshowShowDTO.setYear(metadata.getYear());
            tvshowShowDTO.setThumb(metadata.getThumb());
            tvshowShowDTO.setArt(metadata.getArt());
            tvshowShowDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            tvshowShowDTO.setTotalSeasons(metadata.getChildCount());
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.insert(tvshowShowDTO);
        } else {
            tvshowShowDTO.setTitle(metadata.getTitle());
            tvshowShowDTO.setStudio(metadata.getStudio());
            tvshowShowDTO.setContentRating(metadata.getContentRating());
            tvshowShowDTO.setSummary(metadata.getSummary());
            tvshowShowDTO.setRating(metadata.getRating());
            tvshowShowDTO.setYear(metadata.getYear());
            tvshowShowDTO.setThumb(metadata.getThumb());
            tvshowShowDTO.setArt(metadata.getArt());
            tvshowShowDTO.setOriginallyAvailableAt(metadata.getOriginallyAvailableAt());
            tvshowShowDTO.setTotalSeasons(metadata.getChildCount());
            tvshowShowDTO.setAddedAt(metadata.getAddedAt());
            tvshowShowDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowShowService.update(tvshowShowDTO);
        }
        syncPlexGenre(metadata.getGenreList(), tvshowShowDTO.getId());
        syncPlexActor(metadata.getRoleList(), tvshowShowDTO.getId(), null, ActorRole.Actor);
    }

    public void syncPlexSeason(Long seasonId) {
        GetSeasons.Metadata metadata = plexApiService.findSeasonById(seasonId);
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(seasonId);
        if (tvshowSeasonDTO == null) {
            tvshowSeasonDTO = new TvshowSeasonDTO();
            tvshowSeasonDTO.setId(metadata.getRatingKey());
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.insert(tvshowSeasonDTO);
        } else {
            tvshowSeasonDTO.setTitle(metadata.getTitle());
            tvshowSeasonDTO.setSummary(metadata.getSummary());
            tvshowSeasonDTO.setSeasonIndex(metadata.getIndex());
            tvshowSeasonDTO.setThumb(metadata.getThumb());
            tvshowSeasonDTO.setArt(metadata.getArt());
            tvshowSeasonDTO.setAddedAt(metadata.getAddedAt());
            tvshowSeasonDTO.setUpdatedAt(metadata.getUpdatedAt());
            tvshowSeasonService.update(tvshowSeasonDTO);
        }
        syncPlexShow(metadata.getParentRatingKey());
    }

    private void syncPlexActor(List<Tag> directorList, Long showId, Long episodeId, ActorRole actorRole) {
        if (directorList == null) {
            return;
        }
        for (Tag tag : directorList) {
            TvshowActorDTO tvshowActorDTO = tvshowActorService.findById(tag.getId());
            if (tvshowActorDTO == null) {
                tvshowActorDTO = new TvshowActorDTO();
                tvshowActorDTO.setId(tag.getId());
                tvshowActorDTO.setName(tag.getTag());
                tvshowActorDTO.setOriginalName(tag.getTag());
                tvshowActorDTO = tvshowActorService.insert(tvshowActorDTO);
            }
            if (showId != null) {
                TvshowShowActorDTO tvshowShowActorDTO = tvshowShowActorService.findByShowIdAndActorId(showId, tvshowActorDTO.getId());
                if (tvshowShowActorDTO == null) {
                    tvshowShowActorService.insert(showId, tvshowActorDTO.getId(), actorRole.name());
                }
            } else if (episodeId != null) {
                TvshowEpisodeActorDTO tvshowEpisodeActorDTO = tvshowEpisodeActorService.findByEpisodeIdAndActorId(episodeId, tvshowActorDTO.getId());
                if (tvshowEpisodeActorDTO == null) {
                    tvshowEpisodeActorService.insert(episodeId, tvshowActorDTO.getId(), actorRole.name());
                }
            }

        }
    }

    private void syncPlexGenre(List<Tag> genreList, Long tvshowId) {
        if (genreList == null) {
            return;
        }
        for (Tag tag : genreList) {
            TvshowGenreDTO tvshowGenreDTO = tvshowGenreService.findById(tag.getId());
            if (tvshowGenreDTO == null) {
                tvshowGenreDTO = tvshowGenreService.insert(tag.getId(), tag.getTag());
            }
            TvshowShowGenreDTO tvshowShowGenreDTO = tvshowShowGenreService.findByShowIdAndGenreId(tvshowId, tvshowGenreDTO.getId());
            if (tvshowShowGenreDTO == null) {
                tvshowShowGenreService.insert(tvshowId, tvshowGenreDTO.getId());
            }
        }
    }

}
