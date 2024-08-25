package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieActorConvert;
import cc.onelooker.kaleido.dto.MovieActorDTO;
import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import cc.onelooker.kaleido.entity.MovieActorDO;
import cc.onelooker.kaleido.mapper.MovieActorMapper;
import cc.onelooker.kaleido.service.MovieActorService;
import cc.onelooker.kaleido.service.MovieBasicActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 演职员ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieActorServiceImpl extends AbstractBaseServiceImpl<MovieActorMapper, MovieActorDO, MovieActorDTO> implements MovieActorService {

    MovieActorConvert convert = MovieActorConvert.INSTANCE;

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Override
    protected Wrapper<MovieActorDO> genQueryWrapper(MovieActorDTO dto) {
        LambdaQueryWrapper<MovieActorDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieActorDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getName()), MovieActorDO::getName, dto.getName());
        query.like(StringUtils.isNotEmpty(dto.getThumb()), MovieActorDO::getThumb, dto.getThumb());
        query.eq(StringUtils.isNotEmpty(dto.getOriginalName()), MovieActorDO::getOriginalName, dto.getOriginalName());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), MovieActorDO::getName, dto.getKeyword());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), MovieActorDO::getId, dto.getIdList());
        return query;
    }

    @Override
    public MovieActorDTO convertToDTO(MovieActorDO movieActorDO) {
        return convert.convert(movieActorDO);
    }

    @Override
    public MovieActorDO convertToDO(MovieActorDTO movieActorDTO) {
        return convert.convertToDO(movieActorDTO);
    }

    @Override
    public List<MovieActorDTO> listByMovieId(String movieId) {
        List<MovieBasicActorDTO> movieBasicActorDTOList = movieBasicActorService.listByMovieId(movieId);
        return movieBasicActorDTOList.stream().map(s -> {
            MovieActorDTO movieActorDTO = findById(s.getActorId());
            movieActorDTO.setRole(s.getRole());
            movieActorDTO.setPlayRole(s.getPlayRole());
            return movieActorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public MovieActorDTO findByName(String name) {
        Validate.notEmpty(name);
        MovieActorDTO param = new MovieActorDTO();
        param.setName(name);
        return find(param);
    }

    @Override
    public MovieActorDTO findByThumb(String thumb) {
        Validate.notEmpty(thumb);
        MovieActorDTO param = new MovieActorDTO();
        param.setThumb(thumb);
        return find(param);
    }

    @Override
    public MovieActorDTO findByDoubanId(String doubanId) {
        Validate.notEmpty(doubanId);
        MovieActorDTO param = new MovieActorDTO();
        param.setDoubanId(doubanId);
        return find(param);
    }
}