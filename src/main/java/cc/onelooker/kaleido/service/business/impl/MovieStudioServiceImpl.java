package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieStudioService;
import cc.onelooker.kaleido.entity.business.MovieStudioDO;
import cc.onelooker.kaleido.dto.business.MovieStudioDTO;
import cc.onelooker.kaleido.convert.business.MovieStudioConvert;
import cc.onelooker.kaleido.mapper.business.MovieStudioMapper;

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
public class MovieStudioServiceImpl extends AbstractBaseServiceImpl<MovieStudioMapper, MovieStudioDO, MovieStudioDTO> implements MovieStudioService {

    MovieStudioConvert convert = MovieStudioConvert.INSTANCE;

    @Override
    protected Wrapper<MovieStudioDO> genQueryWrapper(MovieStudioDTO dto) {
        LambdaQueryWrapper<MovieStudioDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieStudioDTO convertToDTO(MovieStudioDO movieStudioDO) {
        return convert.convert(movieStudioDO);
    }

    @Override
    public MovieStudioDO convertToDO(MovieStudioDTO movieStudioDTO) {
        return convert.convertToDO(movieStudioDTO);
    }
}