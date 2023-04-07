package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieProducerService;
import cc.onelooker.kaleido.entity.business.MovieProducerDO;
import cc.onelooker.kaleido.dto.business.MovieProducerDTO;
import cc.onelooker.kaleido.convert.business.MovieProducerConvert;
import cc.onelooker.kaleido.mapper.business.MovieProducerMapper;

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
public class MovieProducerServiceImpl extends AbstractBaseServiceImpl<MovieProducerMapper, MovieProducerDO, MovieProducerDTO> implements MovieProducerService {

    MovieProducerConvert convert = MovieProducerConvert.INSTANCE;

    @Override
    protected Wrapper<MovieProducerDO> genQueryWrapper(MovieProducerDTO dto) {
        LambdaQueryWrapper<MovieProducerDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieProducerDTO convertToDTO(MovieProducerDO movieProducerDO) {
        return convert.convert(movieProducerDO);
    }

    @Override
    public MovieProducerDO convertToDO(MovieProducerDTO movieProducerDTO) {
        return convert.convertToDO(movieProducerDTO);
    }
}