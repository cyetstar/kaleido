package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import cc.onelooker.kaleido.dto.movie.MovieBasicCountryDTO;
import cc.onelooker.kaleido.dto.movie.MovieBasicDTO;
import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicDO;
import cc.onelooker.kaleido.mapper.movie.MovieBasicMapper;
import cc.onelooker.kaleido.service.movie.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.util.constant.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 电影ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicServiceImpl extends AbstractBaseServiceImpl<MovieBasicMapper, MovieBasicDO, MovieBasicDTO> implements MovieBasicService {

    MovieBasicConvert convert = MovieBasicConvert.INSTANCE;

    @Autowired
    private MovieBasicCountryService movieBasicCountryService;

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Autowired
    private MovieBasicLanguageService movieBasicLanguageService;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Autowired
    private MovieAkaService movieAkaService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieTagService movieTagService;

    @Override
    protected Wrapper<MovieBasicDO> genQueryWrapper(MovieBasicDTO dto) {
        LambdaQueryWrapper<MovieBasicDO> query = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(dto.getKeyword())) {
            query.and(q -> q.like(MovieBasicDO::getTitle, dto.getKeyword()).or().like(MovieBasicDO::getOriginalTitle, dto.getKeyword()).or().eq(MovieBasicDO::getDoubanId, dto.getKeyword()).or().eq(MovieBasicDO::getImdbId, dto.getKeyword()));
        }
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieBasicDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), MovieBasicDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(StringUtils.isNotEmpty(dto.getTitleSort()), MovieBasicDO::getTitleSort, dto.getTitleSort());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MovieBasicDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieBasicDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getArt()), MovieBasicDO::getArt, dto.getArt());
        query.eq(Objects.nonNull(dto.getUserRating()), MovieBasicDO::getUserRating, dto.getUserRating());
        query.eq(Objects.nonNull(dto.getSummary()), MovieBasicDO::getSummary, dto.getSummary());
        query.eq(Objects.nonNull(dto.getDuration()), MovieBasicDO::getDuration, dto.getDuration());
        query.eq(StringUtils.isNotEmpty(dto.getContentRating()), MovieBasicDO::getContentRating, dto.getContentRating());
        query.ge(StringUtils.isNotEmpty(dto.getOriginallyAvailableAtStart()), MovieBasicDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAtStart());
        query.le(StringUtils.isNotEmpty(dto.getOriginallyAvailableAtEnd()), MovieBasicDO::getOriginallyAvailableAt, dto.getOriginallyAvailableAtEnd());
        query.eq(StringUtils.isNotEmpty(dto.getStudio()), MovieBasicDO::getStudio, dto.getStudio());
        query.eq(Objects.nonNull(dto.getRating()), MovieBasicDO::getRating, dto.getRating());
        query.eq(Objects.nonNull(dto.getLastViewedAt()), MovieBasicDO::getLastViewedAt, dto.getLastViewedAt());
        query.eq(Objects.nonNull(dto.getViewCount()), MovieBasicDO::getViewCount, dto.getViewCount());
        query.eq(StringUtils.isNotEmpty(dto.getImdbId()), MovieBasicDO::getImdbId, dto.getImdbId());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieBasicDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getTmdbId()), MovieBasicDO::getTmdbId, dto.getTmdbId());
        query.eq(Objects.nonNull(dto.getAddedAt()), MovieBasicDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MovieBasicDO::getUpdatedAt, dto.getUpdatedAt());
        query.likeRight(StringUtils.length(dto.getDecade()) > 3, MovieBasicDO::getYear, StringUtils.substring(dto.getDecade(), 0, 3));
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), MovieBasicDO::getId, dto.getIdList());
        query.eq(StringUtils.isNotEmpty(dto.getMultipleFiles()), MovieBasicDO::getMultipleFiles, dto.getMultipleFiles());
        query.eq(StringUtils.isNotEmpty(dto.getLowQuality()), MovieBasicDO::getLowQuality, dto.getLowQuality());
        query.eq(StringUtils.isNotEmpty(dto.getMandarin()), MovieBasicDO::getMandarin, dto.getMandarin());
        query.eq(StringUtils.isNotEmpty(dto.getNoSubtitle()), MovieBasicDO::getNoSubtitle, dto.getNoSubtitle());
        return query;
    }

    @Override
    public PageResult<MovieBasicDTO> page(@Nullable MovieBasicDTO dto, Page page) {
        if (dto != null && Objects.nonNull(dto.getGenreId())) {
            List<MovieBasicGenreDTO> movieBasicGenreDTOList = movieBasicGenreService.listByGenreId(dto.getGenreId());
            dto.setIdList(movieBasicGenreDTOList.stream().map(MovieBasicGenreDTO::getMovieId).collect(Collectors.toList()));
        }
        if (dto != null && Objects.nonNull(dto.getCountryId())) {
            List<MovieBasicCountryDTO> movieBasicCountryDTOList = movieBasicCountryService.listByCountryId(dto.getCountryId());
            dto.setIdList(movieBasicCountryDTOList.stream().map(MovieBasicCountryDTO::getMovieId).collect(Collectors.toList()));
        }
        if (dto != null && Objects.nonNull(dto.getActorId())) {
            List<MovieBasicActorDTO> movieBasicActorDTOList = movieBasicActorService.listActorId(dto.getActorId());
            dto.setIdList(movieBasicActorDTOList.stream().map(MovieBasicActorDTO::getMovieId).collect(Collectors.toList()));
        }
        return super.page(dto, page);
    }

    @Override
    public MovieBasicDTO convertToDTO(MovieBasicDO movieBasicDO) {
        return convert.convert(movieBasicDO);
    }

    @Override
    public MovieBasicDO convertToDO(MovieBasicDTO movieBasicDTO) {
        return convert.convertToDO(movieBasicDTO);
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }

    @Override
    public Boolean updateDoubanId(Long id, String doubanId) {
        MovieBasicDO movieBasicDO = new MovieBasicDO();
        movieBasicDO.setDoubanId(doubanId);
        movieBasicDO.setId(id);
        return SqlHelper.retBool(baseMapper.updateById(movieBasicDO));
    }

    @Override
    public MovieBasicDTO findByDoubanId(String doubanId) {
        Validate.notEmpty(doubanId);
        MovieBasicDTO param = new MovieBasicDTO();
        param.setDoubanId(doubanId);
        return find(param);
    }

    @Override
    public MovieBasicDTO findByImdb(String imdb) {
        Validate.notEmpty(imdb);
        MovieBasicDTO param = new MovieBasicDTO();
        param.setImdbId(imdb);
        return find(param);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        movieBasicCountryService.deleteByMovieId((Long) id);
        movieBasicGenreService.deleteByMovieId((Long) id);
        movieBasicLanguageService.deleteByMovieId((Long) id);
        movieBasicActorService.deleteByMovieId((Long) id);
        movieAkaService.deleteByMovieId((Long) id);
        movieTagService.deleteByMovieId((Long) id);
        movieBasicCollectionService.updateStatusByMovieId(Constants.NO, (Long) id);
        return super.deleteById(id);
    }

}