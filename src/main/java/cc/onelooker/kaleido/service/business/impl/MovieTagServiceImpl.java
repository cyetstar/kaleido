package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieTagService;
import cc.onelooker.kaleido.entity.business.MovieTagDO;
import cc.onelooker.kaleido.dto.business.MovieTagDTO;
import cc.onelooker.kaleido.convert.business.MovieTagConvert;
import cc.onelooker.kaleido.mapper.business.MovieTagMapper;

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
public class MovieTagServiceImpl extends AbstractBaseServiceImpl<MovieTagMapper, MovieTagDO, MovieTagDTO> implements MovieTagService {

    MovieTagConvert convert = MovieTagConvert.INSTANCE;

    @Override
    protected Wrapper<MovieTagDO> genQueryWrapper(MovieTagDTO dto) {
        LambdaQueryWrapper<MovieTagDO> query = new LambdaQueryWrapper<>();
        return query;
    }

    @Override
    public MovieTagDTO convertToDTO(MovieTagDO movieTagDO) {
        return convert.convert(movieTagDO);
    }

    @Override
    public MovieTagDO convertToDO(MovieTagDTO movieTagDTO) {
        return convert.convertToDO(movieTagDTO);
    }
}