package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieService;
import cc.onelooker.kaleido.entity.business.MovieDO;
import cc.onelooker.kaleido.dto.business.MovieDTO;
import cc.onelooker.kaleido.convert.business.MovieConvert;
import cc.onelooker.kaleido.mapper.business.MovieMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Boolean;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class MovieServiceImpl extends AbstractBaseServiceImpl<MovieMapper, MovieDO, MovieDTO> implements MovieService {

    MovieConvert convert = MovieConvert.INSTANCE;

    @Override
    protected Wrapper<MovieDO> genQueryWrapper(MovieDTO dto) {
        LambdaQueryWrapper<MovieDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getActor()), MovieDO::getActor, dto.getActor());
        query.eq(Objects.nonNull(dto.getAired()), MovieDO::getAired, dto.getAired());
        query.eq(Objects.nonNull(dto.getCategory()), MovieDO::getCategory, dto.getCategory());
        query.eq(Objects.nonNull(dto.getCertification()), MovieDO::getCertification, dto.getCertification());
        query.eq(Objects.nonNull(dto.getCode()), MovieDO::getCode, dto.getCode());
        query.eq(Objects.nonNull(dto.getCountry()), MovieDO::getCountry, dto.getCountry());
        query.eq(Objects.nonNull(dto.getCreatedAt()), MovieDO::getCreatedAt, dto.getCreatedAt());
        query.eq(Objects.nonNull(dto.getDateadded()), MovieDO::getDateadded, dto.getDateadded());
        query.eq(Objects.nonNull(dto.getDoubanId()), MovieDO::getDoubanId, dto.getDoubanId());
        query.eq(Objects.nonNull(dto.getDoubanRating()), MovieDO::getDoubanRating, dto.getDoubanRating());
        query.eq(Objects.nonNull(dto.getDoubanResult()), MovieDO::getDoubanResult, dto.getDoubanResult());
        query.eq(Objects.nonNull(dto.getDoubanTop250()), MovieDO::getDoubanTop250, dto.getDoubanTop250());
        query.eq(Objects.nonNull(dto.getDoubanUpdatedAt()), MovieDO::getDoubanUpdatedAt, dto.getDoubanUpdatedAt());
        query.eq(Objects.nonNull(dto.getDoubanVotes()), MovieDO::getDoubanVotes, dto.getDoubanVotes());
        query.eq(Objects.nonNull(dto.getEdition()), MovieDO::getEdition, dto.getEdition());
        query.eq(Objects.nonNull(dto.getEpbookmark()), MovieDO::getEpbookmark, dto.getEpbookmark());
        query.eq(Objects.nonNull(dto.getFilePath()), MovieDO::getFilePath, dto.getFilePath());
        query.eq(Objects.nonNull(dto.getHasFanart()), MovieDO::getHasFanart, dto.getHasFanart());
        query.eq(Objects.nonNull(dto.getHasFile()), MovieDO::getHasFile, dto.getHasFile());
        query.eq(Objects.nonNull(dto.getHasNfo()), MovieDO::getHasNfo, dto.getHasNfo());
        query.eq(Objects.nonNull(dto.getHasPoster()), MovieDO::getHasPoster, dto.getHasPoster());
        query.eq(Objects.nonNull(dto.getHasSubtitle()), MovieDO::getHasSubtitle, dto.getHasSubtitle());
        query.eq(Objects.nonNull(dto.getImdb()), MovieDO::getImdb, dto.getImdb());
        query.eq(Objects.nonNull(dto.getImdbRating()), MovieDO::getImdbRating, dto.getImdbRating());
        query.eq(Objects.nonNull(dto.getImdbVotes()), MovieDO::getImdbVotes, dto.getImdbVotes());
        query.eq(Objects.nonNull(dto.getKodiid()), MovieDO::getKodiid, dto.getKodiid());
        query.eq(Objects.nonNull(dto.getLastplayed()), MovieDO::getLastplayed, dto.getLastplayed());
        query.eq(Objects.nonNull(dto.getMpaa()), MovieDO::getMpaa, dto.getMpaa());
        query.eq(Objects.nonNull(dto.getNfoPath()), MovieDO::getNfoPath, dto.getNfoPath());
        query.eq(Objects.nonNull(dto.getNumber()), MovieDO::getNumber, dto.getNumber());
        query.eq(Objects.nonNull(dto.getNumberRating()), MovieDO::getNumberRating, dto.getNumberRating());
        query.eq(Objects.nonNull(dto.getNumberVotes()), MovieDO::getNumberVotes, dto.getNumberVotes());
        query.eq(Objects.nonNull(dto.getOriginalFilename()), MovieDO::getOriginalFilename, dto.getOriginalFilename());
        query.eq(Objects.nonNull(dto.getOriginaltitle()), MovieDO::getOriginaltitle, dto.getOriginaltitle());
        query.eq(Objects.nonNull(dto.getOutline()), MovieDO::getOutline, dto.getOutline());
        query.eq(Objects.nonNull(dto.getPlaycount()), MovieDO::getPlaycount, dto.getPlaycount());
        query.eq(Objects.nonNull(dto.getPlot()), MovieDO::getPlot, dto.getPlot());
        query.eq(Objects.nonNull(dto.getPremiered()), MovieDO::getPremiered, dto.getPremiered());
        query.eq(Objects.nonNull(dto.getRuntime()), MovieDO::getRuntime, dto.getRuntime());
        query.eq(Objects.nonNull(dto.getShowlink()), MovieDO::getShowlink, dto.getShowlink());
        query.eq(Objects.nonNull(dto.getSorttitle()), MovieDO::getSorttitle, dto.getSorttitle());
        query.eq(Objects.nonNull(dto.getSource()), MovieDO::getSource, dto.getSource());
        query.eq(Objects.nonNull(dto.getStatus()), MovieDO::getStatus, dto.getStatus());
        query.eq(Objects.nonNull(dto.getTagline()), MovieDO::getTagline, dto.getTagline());
        query.eq(Objects.nonNull(dto.getTitle()), MovieDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getTmdbCollectionId()), MovieDO::getTmdbCollectionId, dto.getTmdbCollectionId());
        query.eq(Objects.nonNull(dto.getTmdbid()), MovieDO::getTmdbid, dto.getTmdbid());
        query.eq(Objects.nonNull(dto.getTop250()), MovieDO::getTop250, dto.getTop250());
        query.eq(Objects.nonNull(dto.getTrailer()), MovieDO::getTrailer, dto.getTrailer());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MovieDO::getUpdatedAt, dto.getUpdatedAt());
        query.eq(Objects.nonNull(dto.getUserNote()), MovieDO::getUserNote, dto.getUserNote());
        query.eq(Objects.nonNull(dto.getUserrating()), MovieDO::getUserrating, dto.getUserrating());
        query.eq(Objects.nonNull(dto.getValidatedPath()), MovieDO::getValidatedPath, dto.getValidatedPath());
        query.eq(Objects.nonNull(dto.getWatched()), MovieDO::getWatched, dto.getWatched());
        query.eq(Objects.nonNull(dto.getWebsite()), MovieDO::getWebsite, dto.getWebsite());
        query.eq(Objects.nonNull(dto.getYear()), MovieDO::getYear, dto.getYear());
        return query;
    }

    @Override
    public MovieDTO convertToDTO(MovieDO movieDO) {
        return convert.convert(movieDO);
    }

    @Override
    public MovieDO convertToDO(MovieDTO movieDTO) {
        return convert.convertToDO(movieDTO);
    }
}