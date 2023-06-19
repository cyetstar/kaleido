package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieFileVideoService;
import cc.onelooker.kaleido.entity.business.MovieFileVideoDO;
import cc.onelooker.kaleido.dto.business.MovieFileVideoDTO;
import cc.onelooker.kaleido.convert.business.MovieFileVideoConvert;
import cc.onelooker.kaleido.mapper.business.MovieFileVideoMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;

/**
 * 电影文件视频信息ServiceImpl
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Service
public class MovieFileVideoServiceImpl extends AbstractBaseServiceImpl<MovieFileVideoMapper, MovieFileVideoDO, MovieFileVideoDTO> implements MovieFileVideoService {

    MovieFileVideoConvert convert = MovieFileVideoConvert.INSTANCE;

    @Override
    protected Wrapper<MovieFileVideoDO> genQueryWrapper(MovieFileVideoDTO dto) {
        LambdaQueryWrapper<MovieFileVideoDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieFileId()), MovieFileVideoDO::getMovieFileId, dto.getMovieFileId());
        query.eq(StringUtils.isNotEmpty(dto.getCodec()), MovieFileVideoDO::getCodec, dto.getCodec());
        query.eq(StringUtils.isNotEmpty(dto.getAspect()), MovieFileVideoDO::getAspect, dto.getAspect());
        query.eq(Objects.nonNull(dto.getWidth()), MovieFileVideoDO::getWidth, dto.getWidth());
        query.eq(Objects.nonNull(dto.getHeight()), MovieFileVideoDO::getHeight, dto.getHeight());
        query.eq(Objects.nonNull(dto.getDurationinseconds()), MovieFileVideoDO::getDurationinseconds, dto.getDurationinseconds());
        query.eq(StringUtils.isNotEmpty(dto.getStereomode()), MovieFileVideoDO::getStereomode, dto.getStereomode());
        return query;
    }

    @Override
    public MovieFileVideoDTO convertToDTO(MovieFileVideoDO movieFileVideoDO) {
        return convert.convert(movieFileVideoDO);
    }

    @Override
    public MovieFileVideoDO convertToDO(MovieFileVideoDTO movieFileVideoDTO) {
        return convert.convertToDO(movieFileVideoDTO);
    }

    @Override
    @Transactional
    public boolean deleteByMovieFileId(Long movieFileId) {
        MovieFileVideoDTO param = new MovieFileVideoDTO();
        param.setMovieFileId(movieFileId);
        return delete(param);
    }

    @Override
    public MovieFileVideoDTO findByMovieFileId(Long movieFileId) {
        MovieFileVideoDTO param = new MovieFileVideoDTO();
        param.setMovieFileId(movieFileId);
        return find(param);
    }
}