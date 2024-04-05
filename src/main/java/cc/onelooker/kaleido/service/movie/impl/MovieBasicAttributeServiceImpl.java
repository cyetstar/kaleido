package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieBasicAttributeConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicAttributeDTO;
import cc.onelooker.kaleido.entity.movie.MovieBasicAttributeDO;
import cc.onelooker.kaleido.mapper.movie.MovieBasicAttributeMapper;
import cc.onelooker.kaleido.service.movie.MovieBasicAttributeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 电影属性值关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-29 16:15:43
 */
@Service
public class MovieBasicAttributeServiceImpl extends AbstractBaseServiceImpl<MovieBasicAttributeMapper, MovieBasicAttributeDO, MovieBasicAttributeDTO> implements MovieBasicAttributeService {

    MovieBasicAttributeConvert convert = MovieBasicAttributeConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicAttributeDO> genQueryWrapper(MovieBasicAttributeDTO dto) {
        LambdaQueryWrapper<MovieBasicAttributeDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicAttributeDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getAttributeId()), MovieBasicAttributeDO::getAttributeId, dto.getAttributeId());
        return query;
    }

    @Override
    public MovieBasicAttributeDTO convertToDTO(MovieBasicAttributeDO movieBasicAttributeDO) {
        return convert.convert(movieBasicAttributeDO);
    }

    @Override
    public MovieBasicAttributeDO convertToDO(MovieBasicAttributeDTO movieBasicAttributeDTO) {
        return convert.convertToDO(movieBasicAttributeDTO);
    }
}