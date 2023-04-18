package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieSetLinkService;
import cc.onelooker.kaleido.entity.business.MovieSetLinkDO;
import cc.onelooker.kaleido.dto.business.MovieSetLinkDTO;
import cc.onelooker.kaleido.convert.business.MovieSetLinkConvert;
import cc.onelooker.kaleido.mapper.business.MovieSetLinkMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;

/**
 * 电影集合关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieSetLinkServiceImpl extends AbstractBaseServiceImpl<MovieSetLinkMapper, MovieSetLinkDO, MovieSetLinkDTO> implements MovieSetLinkService {

    MovieSetLinkConvert convert = MovieSetLinkConvert.INSTANCE;

    @Override
    protected Wrapper<MovieSetLinkDO> genQueryWrapper(MovieSetLinkDTO dto) {
        LambdaQueryWrapper<MovieSetLinkDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieSetLinkDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getSetsId()), MovieSetLinkDO::getSetsId, dto.getSetsId());
        return query;
    }

    @Override
    public MovieSetLinkDTO convertToDTO(MovieSetLinkDO movieSetLinkDO) {
        return convert.convert(movieSetLinkDO);
    }

    @Override
    public MovieSetLinkDO convertToDO(MovieSetLinkDTO movieSetLinkDTO) {
        return convert.convertToDO(movieSetLinkDTO);
    }
}