package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieCountryLinkService;
import cc.onelooker.kaleido.entity.business.MovieCountryLinkDO;
import cc.onelooker.kaleido.dto.business.MovieCountryLinkDTO;
import cc.onelooker.kaleido.convert.business.MovieCountryLinkConvert;
import cc.onelooker.kaleido.mapper.business.MovieCountryLinkMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;

/**
 * 电影国家地区关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieCountryLinkServiceImpl extends AbstractBaseServiceImpl<MovieCountryLinkMapper, MovieCountryLinkDO, MovieCountryLinkDTO> implements MovieCountryLinkService {

    MovieCountryLinkConvert convert = MovieCountryLinkConvert.INSTANCE;

    @Override
    protected Wrapper<MovieCountryLinkDO> genQueryWrapper(MovieCountryLinkDTO dto) {
        LambdaQueryWrapper<MovieCountryLinkDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieCountryLinkDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getMovieCountryId()), MovieCountryLinkDO::getMovieCountryId, dto.getMovieCountryId());
        return query;
    }

    @Override
    public MovieCountryLinkDTO convertToDTO(MovieCountryLinkDO movieCountryLinkDO) {
        return convert.convert(movieCountryLinkDO);
    }

    @Override
    public MovieCountryLinkDO convertToDO(MovieCountryLinkDTO movieCountryLinkDTO) {
        return convert.convertToDO(movieCountryLinkDTO);
    }
}