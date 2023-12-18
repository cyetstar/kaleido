package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.service.movie.MovieThreadFilenameService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.MovieThreadService;
import cc.onelooker.kaleido.entity.movie.MovieThreadDO;
import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.convert.movie.MovieThreadConvert;
import cc.onelooker.kaleido.mapper.movie.MovieThreadMapper;

import com.zjjcnt.common.core.utils.ColumnUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 电影发布记录ServiceImpl
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */
@DS("tmm")
@Service
public class MovieThreadServiceImpl extends AbstractBaseServiceImpl<MovieThreadMapper, MovieThreadDO, MovieThreadDTO> implements MovieThreadService {

    MovieThreadConvert convert = MovieThreadConvert.INSTANCE;

    @Autowired
    private MovieThreadFilenameService movieThreadFilenameService;

    @Override
    protected Wrapper<MovieThreadDO> genQueryWrapper(MovieThreadDTO dto) {
        LambdaQueryWrapper<MovieThreadDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCreatedAt()), MovieThreadDO::getCreatedAt, dto.getCreatedAt());
        query.eq(StringUtils.isNotEmpty(dto.getDoubanId()), MovieThreadDO::getDoubanId, dto.getDoubanId());
        query.eq(StringUtils.isNotEmpty(dto.getImdb()), MovieThreadDO::getImdb, dto.getImdb());
        query.eq(Objects.nonNull(dto.getLinks()), MovieThreadDO::getLinks, dto.getLinks());
        query.eq(StringUtils.isNotEmpty(dto.getPublishDate()), MovieThreadDO::getPublishDate, dto.getPublishDate());
        query.eq(Objects.nonNull(dto.getRating()), MovieThreadDO::getRating, dto.getRating());
        query.eq(Objects.nonNull(dto.getStatus()), MovieThreadDO::getStatus, dto.getStatus());
        query.eq(Objects.nonNull(dto.getThanks()), MovieThreadDO::getThanks, dto.getThanks());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MovieThreadDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getType()), MovieThreadDO::getType, dto.getType());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MovieThreadDO::getUpdatedAt, dto.getUpdatedAt());
        query.eq(StringUtils.isNotEmpty(dto.getUrl()), MovieThreadDO::getUrl, dto.getUrl());
        return query;
    }

    @Override
    public MovieThreadDTO convertToDTO(MovieThreadDO movieThreadDO) {
        return convert.convert(movieThreadDO);
    }

    @Override
    public MovieThreadDO convertToDO(MovieThreadDTO movieThreadDTO) {
        return convert.convertToDO(movieThreadDTO);
    }

    @Override
    public MovieThreadDTO findByFilename(String filename) {
        MovieThreadFilenameDTO movieThreadFilenameDTO = movieThreadFilenameService.findByValue(filename);
        if (movieThreadFilenameDTO != null) {
            return findById(movieThreadFilenameDTO.getThreadId());
        }
        return null;
    }
}