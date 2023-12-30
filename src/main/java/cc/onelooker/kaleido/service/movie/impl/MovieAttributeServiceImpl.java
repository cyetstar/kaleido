package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieAttributeService;
import cc.onelooker.kaleido.entity.movie.MovieAttributeDO;
import cc.onelooker.kaleido.dto.movie.MovieAttributeDTO;
import cc.onelooker.kaleido.convert.movie.MovieAttributeConvert;
import cc.onelooker.kaleido.mapper.movie.MovieAttributeMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

/**
 * 电影属性值ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieAttributeServiceImpl extends AbstractBaseServiceImpl<MovieAttributeMapper, MovieAttributeDO, MovieAttributeDTO> implements MovieAttributeService {

    MovieAttributeConvert convert = MovieAttributeConvert.INSTANCE;

    @Override
    protected Wrapper<MovieAttributeDO> genQueryWrapper(MovieAttributeDTO dto) {
        LambdaQueryWrapper<MovieAttributeDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getAttribute()), MovieAttributeDO::getAttribute, dto.getAttribute());
        query.eq(StringUtils.isNotEmpty(dto.getType()), MovieAttributeDO::getType, dto.getType());
        return query;
    }

    @Override
    public MovieAttributeDTO convertToDTO(MovieAttributeDO movieAttributeDO) {
        return convert.convert(movieAttributeDO);
    }

    @Override
    public MovieAttributeDO convertToDO(MovieAttributeDTO movieAttributeDTO) {
        return convert.convertToDO(movieAttributeDTO);
    }
}