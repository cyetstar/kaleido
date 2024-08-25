package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieDoubanWeeklyConvert;
import cc.onelooker.kaleido.dto.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.entity.MovieDoubanWeeklyDO;
import cc.onelooker.kaleido.mapper.MovieDoubanWeeklyMapper;
import cc.onelooker.kaleido.service.MovieDoubanWeeklyService;
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
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieDoubanWeeklyServiceImpl extends AbstractBaseServiceImpl<MovieDoubanWeeklyMapper, MovieDoubanWeeklyDO, MovieDoubanWeeklyDTO> implements MovieDoubanWeeklyService {

    MovieDoubanWeeklyConvert convert = MovieDoubanWeeklyConvert.INSTANCE;

    @Override
    protected Wrapper<MovieDoubanWeeklyDO> genQueryWrapper(MovieDoubanWeeklyDTO dto) {
        LambdaQueryWrapper<MovieDoubanWeeklyDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieDoubanWeeklyDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieDoubanWeeklyDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalTitle()), MovieDoubanWeeklyDO::getOriginalTitle, dto.getOriginalTitle());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MovieDoubanWeeklyDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MovieDoubanWeeklyDO::getThumb, dto.getThumb());
        query.ge(StringUtils.isNotEmpty(dto.getStatus()), MovieDoubanWeeklyDO::getStatus, dto.getStatus());
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

    @Override
    public MovieDoubanWeeklyDTO findByDoubanId(String doubanId) {
        MovieDoubanWeeklyDTO param = new MovieDoubanWeeklyDTO();
        param.setDoubanId(doubanId);
        return find(param);
    }
}