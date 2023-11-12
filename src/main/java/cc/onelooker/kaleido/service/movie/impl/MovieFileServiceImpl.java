package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.service.movie.MovieFileAudioService;
import cc.onelooker.kaleido.service.movie.MovieFileSubtitleService;
import cc.onelooker.kaleido.service.movie.MovieFileVideoService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieFileService;
import cc.onelooker.kaleido.entity.movie.MovieFileDO;
import cc.onelooker.kaleido.convert.movie.MovieFileConvert;
import cc.onelooker.kaleido.mapper.movie.MovieFileMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.lang.Long;

/**
 * 电影文件ServiceImpl
 *
 * @author xiadawei
 * @date 2023-06-13 20:35:11
 */
@Service
public class MovieFileServiceImpl extends AbstractBaseServiceImpl<MovieFileMapper, MovieFileDO, MovieFileDTO> implements MovieFileService {

    MovieFileConvert convert = MovieFileConvert.INSTANCE;

    @Autowired
    private MovieFileVideoService movieFileVideoService;

    @Autowired
    private MovieFileAudioService movieFileAudioService;

    @Autowired
    private MovieFileSubtitleService movieFileSubtitleService;

    @Override
    protected Wrapper<MovieFileDO> genQueryWrapper(MovieFileDTO dto) {
        LambdaQueryWrapper<MovieFileDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getMovieId()), MovieFileDO::getMovieId, dto.getMovieId());
        query.eq(StringUtils.isNotEmpty(dto.getWjm()), MovieFileDO::getWjm, dto.getWjm());
        query.eq(StringUtils.isNotEmpty(dto.getWjlj()), MovieFileDO::getWjlj, dto.getWjlj());
        query.eq(Objects.nonNull(dto.getWjdx()), MovieFileDO::getWjdx, dto.getWjdx());
        return query;
    }

    @Override
    public MovieFileDTO convertToDTO(MovieFileDO movieFileDO) {
        return convert.convert(movieFileDO);
    }

    @Override
    public MovieFileDO convertToDO(MovieFileDTO movieFileDTO) {
        return convert.convertToDO(movieFileDTO);
    }

    @Override
    @Transactional
    public MovieFileDTO insert(MovieFileDTO dto) {
        dto = super.insert(dto);
        MovieFileVideoDTO movieFileVideoDTO = dto.getMovieFileVideoDTO();
        if (movieFileVideoDTO != null) {
            movieFileVideoDTO.setMovieFileId(dto.getId());
            movieFileVideoService.insert(movieFileVideoDTO);
        }
        List<MovieFileAudioDTO> movieFileAudioDTOList = dto.getMovieFileAudioDTOList();
        if (CollectionUtils.isNotEmpty(movieFileAudioDTOList)) {
            for (MovieFileAudioDTO movieFileAudioDTO : movieFileAudioDTOList) {
                movieFileAudioDTO.setMovieFileId(dto.getId());
                movieFileAudioService.insert(movieFileAudioDTO);
            }
        }
        List<MovieFileSubtitleDTO> movieFileSubtitleDTOList = dto.getMovieFileSubtitleDTOList();
        if (CollectionUtils.isNotEmpty(movieFileSubtitleDTOList)) {
            for (MovieFileSubtitleDTO movieFileSubtitleDTO : movieFileSubtitleDTOList) {
                movieFileSubtitleDTO.setMovieFileId(dto.getId());
                movieFileSubtitleService.insert(movieFileSubtitleDTO);
            }
        }
        return dto;
    }

    @Override
    @Transactional
    public boolean deleteByMovieId(Long movieId) {
        MovieFileDTO movieFileDTO = findByMovieId(movieId);
        if (movieFileDTO != null) {
            movieFileVideoService.deleteByMovieFileId(movieFileDTO.getId());
            movieFileAudioService.deleteByMovieFileId(movieFileDTO.getId());
            movieFileSubtitleService.deleteByMovieFileId(movieFileDTO.getId());
            deleteById(movieFileDTO.getId());
        }
        return true;
    }

    @Override
    public MovieFileDTO findByMovieId(Long movieId) {
        MovieFileDTO param = new MovieFileDTO();
        param.setMovieId(movieId);
        return find(param);
    }

}