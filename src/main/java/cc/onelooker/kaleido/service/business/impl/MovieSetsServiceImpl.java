package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieSetsService;
import cc.onelooker.kaleido.entity.business.MovieSetsDO;
import cc.onelooker.kaleido.dto.business.MovieSetsDTO;
import cc.onelooker.kaleido.convert.business.MovieSetsConvert;
import cc.onelooker.kaleido.mapper.business.MovieSetsMapper;

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
public class MovieSetsServiceImpl extends AbstractBaseServiceImpl<MovieSetsMapper, MovieSetsDO, MovieSetsDTO> implements MovieSetsService {

    MovieSetsConvert convert = MovieSetsConvert.INSTANCE;

    @Override
    protected Wrapper<MovieSetsDO> genQueryWrapper(MovieSetsDTO dto) {
        LambdaQueryWrapper<MovieSetsDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieSetsDTO convertToDTO(MovieSetsDO movieSetsDO) {
        return convert.convert(movieSetsDO);
    }

    @Override
    public MovieSetsDO convertToDO(MovieSetsDTO movieSetsDTO) {
        return convert.convertToDO(movieSetsDTO);
    }
}