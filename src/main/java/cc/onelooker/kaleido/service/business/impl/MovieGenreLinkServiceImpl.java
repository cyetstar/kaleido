package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieGenreLinkService;
import cc.onelooker.kaleido.entity.business.MovieGenreLinkDO;
import cc.onelooker.kaleido.dto.business.MovieGenreLinkDTO;
import cc.onelooker.kaleido.convert.business.MovieGenreLinkConvert;
import cc.onelooker.kaleido.mapper.business.MovieGenreLinkMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;

/**
 * 电影类型关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Service
public class MovieGenreLinkServiceImpl extends AbstractBaseServiceImpl<MovieGenreLinkMapper, MovieGenreLinkDO, MovieGenreLinkDTO> implements MovieGenreLinkService {

    MovieGenreLinkConvert convert = MovieGenreLinkConvert.INSTANCE;

    @Override
    protected Wrapper<MovieGenreLinkDO> genQueryWrapper(MovieGenreLinkDTO dto) {
        LambdaQueryWrapper<MovieGenreLinkDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieGenreLinkDO::getMovieId, dto.getMovieId());
        query.eq(Objects.nonNull(dto.getMovieGenreId()), MovieGenreLinkDO::getMovieGenreId, dto.getMovieGenreId());
        return query;
    }

    @Override
    public MovieGenreLinkDTO convertToDTO(MovieGenreLinkDO movieGenreLinkDO) {
        return convert.convert(movieGenreLinkDO);
    }

    @Override
    public MovieGenreLinkDO convertToDO(MovieGenreLinkDTO movieGenreLinkDTO) {
        return convert.convertToDO(movieGenreLinkDTO);
    }
}