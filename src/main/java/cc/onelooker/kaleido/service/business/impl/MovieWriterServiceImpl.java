package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieWriterService;
import cc.onelooker.kaleido.entity.business.MovieWriterDO;
import cc.onelooker.kaleido.dto.business.MovieWriterDTO;
import cc.onelooker.kaleido.convert.business.MovieWriterConvert;
import cc.onelooker.kaleido.mapper.business.MovieWriterMapper;

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
public class MovieWriterServiceImpl extends AbstractBaseServiceImpl<MovieWriterMapper, MovieWriterDO, MovieWriterDTO> implements MovieWriterService {

    MovieWriterConvert convert = MovieWriterConvert.INSTANCE;

    @Override
    protected Wrapper<MovieWriterDO> genQueryWrapper(MovieWriterDTO dto) {
        LambdaQueryWrapper<MovieWriterDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieWriterDTO convertToDTO(MovieWriterDO movieWriterDO) {
        return convert.convert(movieWriterDO);
    }

    @Override
    public MovieWriterDO convertToDO(MovieWriterDTO movieWriterDTO) {
        return convert.convertToDO(movieWriterDTO);
    }
}