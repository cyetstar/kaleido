package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieFileSubtitleService;
import cc.onelooker.kaleido.entity.business.MovieFileSubtitleDO;
import cc.onelooker.kaleido.dto.business.MovieFileSubtitleDTO;
import cc.onelooker.kaleido.convert.business.MovieFileSubtitleConvert;
import cc.onelooker.kaleido.mapper.business.MovieFileSubtitleMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;
import java.lang.String;

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
        query.eq(StringUtils.isNotEmpty(dto.getLanuage()), MovieFileSubtitleDO::getLanuage, dto.getLanuage());
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
}