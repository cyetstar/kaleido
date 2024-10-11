package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieBasicActorConvert;
import cc.onelooker.kaleido.dto.MovieBasicActorDTO;
import cc.onelooker.kaleido.entity.MovieBasicActorDO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.mapper.MovieBasicActorMapper;
import cc.onelooker.kaleido.service.MovieBasicActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 电影演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */
@Service
public class MovieBasicActorServiceImpl extends AbstractBaseServiceImpl<MovieBasicActorMapper, MovieBasicActorDO, MovieBasicActorDTO> implements MovieBasicActorService {

    MovieBasicActorConvert convert = MovieBasicActorConvert.INSTANCE;

    @Override
    protected Wrapper<MovieBasicActorDO> genQueryWrapper(MovieBasicActorDTO dto) {
        LambdaQueryWrapper<MovieBasicActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieBasicActorDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getActorId()), MovieBasicActorDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), MovieBasicActorDO::getRole, dto.getRole());
        return query;
    }

    @Override
    public MovieBasicActorDTO convertToDTO(MovieBasicActorDO movieBasicActorDO) {
        return convert.convert(movieBasicActorDO);
    }

    @Override
    public MovieBasicActorDO convertToDO(MovieBasicActorDTO movieBasicActorDTO) {
        return convert.convertToDO(movieBasicActorDTO);
    }

    @Override
    public List<MovieBasicActorDTO> listByMovieId(String movieId) {
        Validate.notNull(movieId);
        MovieBasicActorDTO param = new MovieBasicActorDTO();
        param.setMovieId(movieId);
        return list(param);
    }

    @Override
    public List<MovieBasicActorDTO> listActorId(String actorId) {
        Validate.notNull(actorId);
        MovieBasicActorDTO param = new MovieBasicActorDTO();
        param.setActorId(actorId);
        return list(param);
    }

    @Override
    public MovieBasicActorDTO findByMovieIdAndActorId(String movieId, String actorId) {
        Validate.notEmpty(movieId);
        Validate.notEmpty(actorId);
        MovieBasicActorDTO param = new MovieBasicActorDTO();
        param.setMovieId(movieId);
        param.setActorId(actorId);
        return find(param);
    }

    @Override
    public MovieBasicActorDTO insertByMovieIdAndActorIdAndRole(String movieId, String actorId, ActorRole role) {
        MovieBasicActorDTO movieBasicActorDTO = new MovieBasicActorDTO();
        movieBasicActorDTO.setMovieId(movieId);
        movieBasicActorDTO.setActorId(actorId);
        movieBasicActorDTO.setRole(role.name());
        return insert(movieBasicActorDTO);
    }

    @Override
    public boolean deleteByMovieId(String movieId) {
        Validate.notNull(movieId);
        MovieBasicActorDTO param = new MovieBasicActorDTO();
        param.setMovieId(movieId);
        return delete(param);
    }

    @Override
    public boolean deleteByMovieIdAndRole(String movieId, ActorRole role) {
        Validate.notEmpty(movieId);
        Validate.notNull(role);
        MovieBasicActorDTO param = new MovieBasicActorDTO();
        param.setMovieId(movieId);
        param.setRole(role.name());
        return delete(param);
    }
}