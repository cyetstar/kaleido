package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieBasicCollectionConvert;
import cc.onelooker.kaleido.dto.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.entity.MovieBasicCollectionDO;
import cc.onelooker.kaleido.mapper.MovieBasicCollectionMapper;
import cc.onelooker.kaleido.service.MovieBasicCollectionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 电影集合关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieBasicCollectionServiceImpl extends AbstractBaseServiceImpl<MovieBasicCollectionMapper, MovieBasicCollectionDO, MovieBasicCollectionDTO> implements MovieBasicCollectionService {

    MovieBasicCollectionConvert convert = MovieBasicCollectionConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicCollectionDO> genQueryWrapper(MovieBasicCollectionDTO dto) {
        LambdaQueryWrapper<MovieBasicCollectionDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicCollectionDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getCollectionId()), MovieBasicCollectionDO::getCollectionId, dto.getCollectionId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieBasicCollectionDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MovieBasicCollectionDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieBasicCollectionDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieBasicCollectionDO::getThumb, dto.getThumb());
        return query;
    }

    @Override
    public MovieBasicCollectionDTO convertToDTO(MovieBasicCollectionDO movieBasicCollectionDO) {
        return convert.convert(movieBasicCollectionDO);
    }

    @Override
    public MovieBasicCollectionDO convertToDO(MovieBasicCollectionDTO movieBasicCollectionDTO) {
        return convert.convertToDO(movieBasicCollectionDTO);
    }

    @Override
    public List<MovieBasicCollectionDTO> listByCollectionId(String collectionId) {
        Validate.notNull(collectionId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setCollectionId(collectionId);
        return list(param);
    }

    @Override
    public List<MovieBasicCollectionDTO> listMovieId(String movieId) {
        Validate.notNull(movieId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public boolean deleteByCollectionId(String collectionId) {
        Validate.notNull(collectionId);
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setCollectionId(collectionId);
        return delete(param);
    }

    @Override
    public MovieBasicCollectionDTO findByCollectionIdAndDoubanId(String collectionId, String doubanId) {
        MovieBasicCollectionDTO param = new MovieBasicCollectionDTO();
        param.setCollectionId(collectionId);
        param.setDoubanId(doubanId);
        return find(param);
    }

    @Override
    @Transactional
    public void updateStatusByMovieId(String status, String movieId) {
        Validate.notEmpty(status);
        Validate.notNull(movieId);
        MovieBasicCollectionDO param = new MovieBasicCollectionDO();
        param.setStatus(status);
        param.setMovieId(movieId);
        baseMapper.updateById(param);
    }
}