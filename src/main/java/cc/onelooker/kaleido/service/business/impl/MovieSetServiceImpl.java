package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieSetService;
import cc.onelooker.kaleido.entity.business.MovieSetDO;
import cc.onelooker.kaleido.dto.business.MovieSetDTO;
import cc.onelooker.kaleido.convert.business.MovieSetConvert;
import cc.onelooker.kaleido.mapper.business.MovieSetMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 电影集合ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieSetServiceImpl extends AbstractBaseServiceImpl<MovieSetMapper, MovieSetDO, MovieSetDTO> implements MovieSetService {

    MovieSetConvert convert = MovieSetConvert.INSTANCE;

    @Override
    protected Wrapper<MovieSetDO> genQueryWrapper(MovieSetDTO dto) {
        LambdaQueryWrapper<MovieSetDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MovieSetDO::getMc, dto.getMc());
        query.eq(StringUtils.isNotEmpty(dto.getJhsm()), MovieSetDO::getJhsm, dto.getJhsm());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), MovieSetDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), MovieSetDO::getXgsj, dto.getXgsj());
        return query;
    }

    @Override
    public MovieSetDTO convertToDTO(MovieSetDO movieSetDO) {
        return convert.convert(movieSetDO);
    }

    @Override
    public MovieSetDO convertToDO(MovieSetDTO movieSetDTO) {
        return convert.convertToDO(movieSetDTO);
    }
}