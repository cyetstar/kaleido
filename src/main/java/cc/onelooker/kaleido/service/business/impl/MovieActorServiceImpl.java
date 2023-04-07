package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieActorService;
import cc.onelooker.kaleido.entity.business.MovieActorDO;
import cc.onelooker.kaleido.dto.business.MovieActorDTO;
import cc.onelooker.kaleido.convert.business.MovieActorConvert;
import cc.onelooker.kaleido.mapper.business.MovieActorMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class MovieActorServiceImpl extends AbstractBaseServiceImpl<MovieActorMapper, MovieActorDO, MovieActorDTO> implements MovieActorService {

    MovieActorConvert convert = MovieActorConvert.INSTANCE;

    @Override
    protected Wrapper<MovieActorDO> genQueryWrapper(MovieActorDTO dto) {
        LambdaQueryWrapper<MovieActorDO> query = new LambdaQueryWrapper<>();
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
}