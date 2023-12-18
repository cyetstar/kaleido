package cc.onelooker.kaleido.service.movie.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieThreadFilenameService;
import cc.onelooker.kaleido.entity.movie.MovieThreadFilenameDO;
import cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.convert.movie.MovieThreadFilenameConvert;
import cc.onelooker.kaleido.mapper.movie.MovieThreadFilenameMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 电影发布文件ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 */
@DS("tmm")
@Service
public class MovieThreadFilenameServiceImpl extends AbstractBaseServiceImpl<MovieThreadFilenameMapper, MovieThreadFilenameDO, MovieThreadFilenameDTO> implements MovieThreadFilenameService {

    MovieThreadFilenameConvert convert = MovieThreadFilenameConvert.INSTANCE;

    @Override
    protected Wrapper<MovieThreadFilenameDO> genQueryWrapper(MovieThreadFilenameDTO dto) {
        LambdaQueryWrapper<MovieThreadFilenameDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getValue()), MovieThreadFilenameDO::getValue, dto.getValue());
        query.eq(Objects.nonNull(dto.getThreadId()), MovieThreadFilenameDO::getThreadId, dto.getThreadId());
        return query;
    }

    @Override
    public MovieThreadFilenameDTO convertToDTO(MovieThreadFilenameDO movieThreadFilenameDO) {
        return convert.convert(movieThreadFilenameDO);
    }

    @Override
    public MovieThreadFilenameDO convertToDO(MovieThreadFilenameDTO movieThreadFilenameDTO) {
        return convert.convertToDO(movieThreadFilenameDTO);
    }

    @Override
    public MovieThreadFilenameDTO findByValue(String value) {
        MovieThreadFilenameDTO param = new MovieThreadFilenameDTO();
        param.setValue(value);
        return find(param);
    }
}