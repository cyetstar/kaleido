package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.MovieFileAudioService;
import cc.onelooker.kaleido.entity.business.MovieFileAudioDO;
import cc.onelooker.kaleido.dto.business.MovieFileAudioDTO;
import cc.onelooker.kaleido.convert.business.MovieFileAudioConvert;
import cc.onelooker.kaleido.mapper.business.MovieFileAudioMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * 电影文件音频信息ServiceImpl
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Service
public class MovieFileAudioServiceImpl extends AbstractBaseServiceImpl<MovieFileAudioMapper, MovieFileAudioDO, MovieFileAudioDTO> implements MovieFileAudioService {

    MovieFileAudioConvert convert = MovieFileAudioConvert.INSTANCE;

    @Override
    protected Wrapper<MovieFileAudioDO> genQueryWrapper(MovieFileAudioDTO dto) {
        LambdaQueryWrapper<MovieFileAudioDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieFileId()), MovieFileAudioDO::getMovieFileId, dto.getMovieFileId());
        query.eq(StringUtils.isNotEmpty(dto.getCodec()), MovieFileAudioDO::getCodec, dto.getCodec());
        query.eq(StringUtils.isNotEmpty(dto.getLanguage()), MovieFileAudioDO::getLanguage, dto.getLanguage());
        query.eq(StringUtils.isNotEmpty(dto.getChannels()), MovieFileAudioDO::getChannels, dto.getChannels());
        return query;
    }

    @Override
    public MovieFileAudioDTO convertToDTO(MovieFileAudioDO movieFileAudioDO) {
        return convert.convert(movieFileAudioDO);
    }

    @Override
    public MovieFileAudioDO convertToDO(MovieFileAudioDTO movieFileAudioDTO) {
        return convert.convertToDO(movieFileAudioDTO);
    }

    @Override
    @Transactional
    public boolean deleteByMovieFileId(Long movieFileId) {
        MovieFileAudioDTO param = new MovieFileAudioDTO();
        param.setMovieFileId(movieFileId);
        return delete(param);
    }

    @Override
    public List<MovieFileAudioDTO> listByMovieFileId(Long movieFileId) {
        MovieFileAudioDTO param = new MovieFileAudioDTO();
        param.setMovieFileId(movieFileId);
        return list(param);
    }
}