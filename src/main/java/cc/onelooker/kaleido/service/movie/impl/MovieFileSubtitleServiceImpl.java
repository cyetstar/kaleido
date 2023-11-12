package cc.onelooker.kaleido.service.movie.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieFileSubtitleService;
import cc.onelooker.kaleido.entity.movie.MovieFileSubtitleDO;
import cc.onelooker.kaleido.dto.movie.MovieFileSubtitleDTO;
import cc.onelooker.kaleido.convert.movie.MovieFileSubtitleConvert;
import cc.onelooker.kaleido.mapper.movie.MovieFileSubtitleMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;

/**
 * 电影文件字幕信息ServiceImpl
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Service
public class MovieFileSubtitleServiceImpl extends AbstractBaseServiceImpl<MovieFileSubtitleMapper, MovieFileSubtitleDO, MovieFileSubtitleDTO> implements MovieFileSubtitleService {

    MovieFileSubtitleConvert convert = MovieFileSubtitleConvert.INSTANCE;

    @Override
    protected Wrapper<MovieFileSubtitleDO> genQueryWrapper(MovieFileSubtitleDTO dto) {
        LambdaQueryWrapper<MovieFileSubtitleDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieFileId()), MovieFileSubtitleDO::getMovieFileId, dto.getMovieFileId());
        query.eq(StringUtils.isNotEmpty(dto.getLanguage()), MovieFileSubtitleDO::getLanguage, dto.getLanguage());
        return query;
    }

    @Override
    public MovieFileSubtitleDTO convertToDTO(MovieFileSubtitleDO movieFileSubtitleDO) {
        return convert.convert(movieFileSubtitleDO);
    }

    @Override
    public MovieFileSubtitleDO convertToDO(MovieFileSubtitleDTO movieFileSubtitleDTO) {
        return convert.convertToDO(movieFileSubtitleDTO);
    }

    @Override
    @Transactional
    public boolean deleteByMovieFileId(Long movieFileId) {
        MovieFileSubtitleDTO param = new MovieFileSubtitleDTO();
        param.setMovieFileId(movieFileId);
        return delete(param);
    }

    @Override
    public List<MovieFileSubtitleDTO> listByMovieFileId(Long movieFileId) {
        MovieFileSubtitleDTO param = new MovieFileSubtitleDTO();
        param.setMovieFileId(movieFileId);
        return list(param);
    }
}