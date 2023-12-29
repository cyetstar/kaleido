package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieDoubanWeeklyConvert;
import cc.onelooker.kaleido.dto.movie.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.entity.movie.MovieDoubanWeeklyDO;
import cc.onelooker.kaleido.mapper.movie.MovieDoubanWeeklyMapper;
import cc.onelooker.kaleido.service.movie.MovieDoubanWeeklyService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 豆瓣电影口碑榜ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */
@Service
public class MovieDoubanWeeklyServiceImpl extends AbstractBaseServiceImpl<MovieDoubanWeeklyMapper, MovieDoubanWeeklyDO, MovieDoubanWeeklyDTO> implements MovieDoubanWeeklyService {

    MovieDoubanWeeklyConvert convert = MovieDoubanWeeklyConvert.INSTANCE;

    @Override
    protected Wrapper<MovieDoubanWeeklyDO> genQueryWrapper(MovieDoubanWeeklyDTO dto) {
        LambdaQueryWrapper<MovieDoubanWeeklyDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieDoubanWeeklyDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), MovieDoubanWeeklyDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MovieDoubanWeeklyDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieDoubanWeeklyDO::getThumb, dto.getThumb());
        query.ge(StringUtils.isNotEmpty(dto.getListingDateStart()), MovieDoubanWeeklyDO::getListingDate, dto.getListingDateStart());
        query.le(StringUtils.isNotEmpty(dto.getListingDateEnd()), MovieDoubanWeeklyDO::getListingDate, dto.getListingDateEnd());
        query.ge(StringUtils.isNotEmpty(dto.getDelistingDateStart()), MovieDoubanWeeklyDO::getDelistingDate, dto.getDelistingDateStart());
        query.le(StringUtils.isNotEmpty(dto.getDelistingDateEnd()), MovieDoubanWeeklyDO::getDelistingDate, dto.getDelistingDateEnd());
        query.eq(Objects.nonNull(dto.getTop()), MovieDoubanWeeklyDO::getTop, dto.getTop());
        query.eq(StringUtils.isNotEmpty(dto.getMemo()), MovieDoubanWeeklyDO::getMemo, dto.getMemo());
        return query;
    }

    @Override
    public MovieDoubanWeeklyDTO convertToDTO(MovieDoubanWeeklyDO movieDoubanWeeklyDO) {
        return convert.convert(movieDoubanWeeklyDO);
    }

    @Override
    public MovieDoubanWeeklyDO convertToDO(MovieDoubanWeeklyDTO movieDoubanWeeklyDTO) {
        return convert.convertToDO(movieDoubanWeeklyDTO);
    }
}