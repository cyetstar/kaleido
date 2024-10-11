package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieCollectionConvert;
import cc.onelooker.kaleido.dto.MovieCollectionDTO;
import cc.onelooker.kaleido.entity.MovieCollectionDO;
import cc.onelooker.kaleido.mapper.MovieCollectionMapper;
import cc.onelooker.kaleido.service.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.MovieCollectionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;

/**
 * 电影集合ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieCollectionServiceImpl extends AbstractBaseServiceImpl<MovieCollectionMapper, MovieCollectionDO, MovieCollectionDTO> implements MovieCollectionService {

    MovieCollectionConvert convert = MovieCollectionConvert.INSTANCE;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Override
    protected Wrapper<MovieCollectionDO> genQueryWrapper(MovieCollectionDTO dto) {
        LambdaQueryWrapper<MovieCollectionDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieCollectionDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getSummary()), MovieCollectionDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieCollectionDO::getThumb, dto.getThumb());
        query.eq(Objects.nonNull(dto.getChildCount()), MovieCollectionDO::getChildCount, dto.getChildCount());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieCollectionDO::getDoubanId, dto.getDoubanId());
        return query;
    }

    @Override
    public MovieCollectionDTO convertToDTO(MovieCollectionDO movieCollectionDO) {
        return convert.convert(movieCollectionDO);
    }

    @Override
    public MovieCollectionDO convertToDO(MovieCollectionDTO movieCollectionDTO) {
        return convert.convertToDO(movieCollectionDTO);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        movieBasicCollectionService.deleteByCollectionId((String) id);
        return super.deleteById(id);
    }
}