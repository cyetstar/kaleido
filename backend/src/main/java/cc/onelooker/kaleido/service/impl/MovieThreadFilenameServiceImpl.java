package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MovieThreadFilenameConvert;
import cc.onelooker.kaleido.dto.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.entity.MovieThreadFilenameDO;
import cc.onelooker.kaleido.mapper.MovieThreadFilenameMapper;
import cc.onelooker.kaleido.service.MovieThreadFilenameService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        Validate.notEmpty(value);
        MovieThreadFilenameDTO param = new MovieThreadFilenameDTO();
        param.setValue(value);
        return find(param);
    }

    @Override
    public List<MovieThreadFilenameDTO> listByThreadId(Long threadId) {
        Validate.notNull(threadId);
        MovieThreadFilenameDTO param = new MovieThreadFilenameDTO();
        param.setThreadId(threadId);
        return list(param);
    }
}