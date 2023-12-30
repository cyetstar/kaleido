package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import cc.onelooker.kaleido.entity.movie.MovieCollectionDO;
import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.convert.movie.MovieCollectionConvert;
import cc.onelooker.kaleido.mapper.movie.MovieCollectionMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 电影集合ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieCollectionServiceImpl extends AbstractBaseServiceImpl<MovieCollectionMapper, MovieCollectionDO, MovieCollectionDTO> implements MovieCollectionService {

    MovieCollectionConvert convert = MovieCollectionConvert.INSTANCE;

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
}