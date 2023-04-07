package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieDirectorService;
import cc.onelooker.kaleido.entity.business.MovieDirectorDO;
import cc.onelooker.kaleido.dto.business.MovieDirectorDTO;
import cc.onelooker.kaleido.convert.business.MovieDirectorConvert;
import cc.onelooker.kaleido.mapper.business.MovieDirectorMapper;

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
public class MovieDirectorServiceImpl extends AbstractBaseServiceImpl<MovieDirectorMapper, MovieDirectorDO, MovieDirectorDTO> implements MovieDirectorService {

    MovieDirectorConvert convert = MovieDirectorConvert.INSTANCE;

    @Override
    protected Wrapper<MovieDirectorDO> genQueryWrapper(MovieDirectorDTO dto) {
        LambdaQueryWrapper<MovieDirectorDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieDirectorDTO convertToDTO(MovieDirectorDO movieDirectorDO) {
        return convert.convert(movieDirectorDO);
    }

    @Override
    public MovieDirectorDO convertToDO(MovieDirectorDTO movieDirectorDTO) {
        return convert.convertToDO(movieDirectorDTO);
    }
}