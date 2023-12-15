package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.convert.movie.MovieCollectionConvert;
import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.entity.movie.MovieCollectionDO;
import cc.onelooker.kaleido.mapper.movie.MovieCollectionMapper;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

/**
 * 电影集合ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
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
    public MovieCollectionDTO findByTitle(String title) {
        Validate.notNull(title);
        MovieCollectionDTO param = new MovieCollectionDTO();
        param.setTitle(title);
        return find(param);
    }

    @Override
    public MovieCollectionDTO insert(Long id, String title) {
        MovieCollectionDTO movieCollectionDTO = new MovieCollectionDTO();
        movieCollectionDTO.setId(id);
        movieCollectionDTO.setTitle(title);
        return insert(movieCollectionDTO);
    }

    @Override
    public boolean deleteById(Serializable id) {
        movieBasicCollectionService.deleteByCollectionId((Long) id);
        return super.deleteById(id);
    }
}