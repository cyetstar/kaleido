package cc.onelooker.kaleido.service.business.impl;

import cc.onelooker.kaleido.dto.business.*;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.nfo.UniqueidNFO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.service.ExBaseServiceImpl;
import cc.onelooker.kaleido.service.business.*;
import cc.onelooker.kaleido.utils.ConvertUtils;
import cn.hutool.core.util.IdUtil;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.onelooker.kaleido.entity.business.MovieDO;
import cc.onelooker.kaleido.convert.business.MovieConvert;
import cc.onelooker.kaleido.mapper.business.MovieMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import java.lang.String;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * 电影ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */
@Slf4j
@Service
public class MovieServiceImpl extends ExBaseServiceImpl<MovieMapper, MovieDO, MovieDTO> implements MovieService {

    MovieConvert convert = MovieConvert.INSTANCE;

    @Autowired
    private MovieUniqueidService movieUniqueidService;

    @Autowired
    private MovieRatingService movieRatingService;

    @Autowired
    private MovieGenreService movieGenreService;

    @Autowired
    private MovieGenreLinkService movieGenreLinkService;

    @Autowired
    private MovieLanguageService movieLanguageService;

    @Autowired
    private MovieLanguageLinkService movieLanguageLinkService;

    @Autowired
    private MovieCountryService movieCountryService;

    @Autowired
    private MovieCountryLinkService movieCountryLinkService;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieActorLinkService movieActorLinkService;

    @Autowired
    private MovieTagService movieTagService;

    @Autowired
    private MovieSetService movieSetService;

    @Autowired
    private MovieSetLinkService movieSetLinkService;

    @Autowired
    private MovieAkaService movieAkaService;

    @Autowired
    private MovieFileService movieFileService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected Wrapper<MovieDO> genQueryWrapper(MovieDTO dto) {
        LambdaQueryWrapper<MovieDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getDymc()), MovieDO::getDymc, dto.getDymc());
        query.eq(StringUtils.isNotEmpty(dto.getYpm()), MovieDO::getYpm, dto.getYpm());
        query.eq(Objects.nonNull(dto.getYhpf()), MovieDO::getYhpf, dto.getYhpf());
        query.eq(Objects.nonNull(dto.getDyjj()), MovieDO::getDyjj, dto.getDyjj());
        query.eq(StringUtils.isNotEmpty(dto.getDyby()), MovieDO::getDyby, dto.getDyby());
        query.eq(Objects.nonNull(dto.getYpsc()), MovieDO::getYpsc, dto.getYpsc());
        query.eq(StringUtils.isNotEmpty(dto.getDydj()), MovieDO::getDydj, dto.getDydj());
        query.eq(StringUtils.isNotEmpty(dto.getSyrq()), MovieDO::getSyrq, dto.getSyrq());
        query.eq(StringUtils.isNotEmpty(dto.getGwdz()), MovieDO::getGwdz, dto.getGwdz());
        query.eq(StringUtils.isNotEmpty(dto.getGkbz()), MovieDO::getGkbz, dto.getGkbz());
        query.eq(StringUtils.isNotEmpty(dto.getGksj()), MovieDO::getGksj, dto.getGksj());
        query.eq(StringUtils.isNotEmpty(dto.getScbz()), MovieDO::getScbz, dto.getScbz());
        query.eq(StringUtils.isNotEmpty(dto.getScsj()), MovieDO::getScsj, dto.getScsj());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MovieDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getCjsj()), MovieDO::getCjsj, dto.getCjsj());
        query.eq(StringUtils.isNotEmpty(dto.getXgsj()), MovieDO::getXgsj, dto.getXgsj());
        return query;
    }

    @Override
    public MovieDTO convertToDTO(MovieDO movieDO) {
        return convert.convert(movieDO);
    }

    @Override
    public MovieDO convertToDO(MovieDTO movieDTO) {
        return convert.convertToDO(movieDTO);
    }

    @Override
    public synchronized void importNFO(String libraryPath, String[] filterPaths) throws IOException {
        log.info("开始导入NFO文件, 导入路径【{}】", libraryPath);
        Instant start = Instant.now();
        Stream<Path> paths = Files.walk(Paths.get(libraryPath));
        paths.forEach(s -> {
            if (match(filterPaths, s.toString()) || !s.toString().endsWith(".nfo")) {
                return;
            }
            try {
                processNFO(s.toFile());
                log.info("导入【{}】完成", s);
            } catch (Exception e) {
                log.info("导入【{}】发生错误", s);
                log.error("导入【" + s + "】发生错误", e);
            }
        });
        Instant finish = Instant.now();
        log.info("导入NFO文件完成, 耗时【{}分】", Duration.between(start, finish).toMinutes());
    }

    public MovieDTO findByUniqueidList(List<MovieUniqueidDTO> movieUniqueidDTOList) {
        if (CollectionUtils.isEmpty(movieUniqueidDTOList)) {
            return null;
        }
        for (MovieUniqueidDTO movieUniqueidDTO : movieUniqueidDTOList) {
            MovieDTO movieDTO = movieUniqueidService.findByUidAndBslx(movieUniqueidDTO.getUid(), movieUniqueidDTO.getBslx());
            if (movieDTO != null) {
                return movieDTO;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public MovieDTO insert(MovieDTO dto) {
        MovieDTO exist = findByUniqueidList(dto.getMovieUniqueidDTOList());
        if (exist != null) {
            throw new ServiceException(3002, "已存在该记录");
        }
        Long id = IdUtil.createSnowflake(1, 1).nextId();
        dto.setId(id);
        insertMovieUniqueid(dto);
        insertMovieRating(dto);
        insertMovieGenre(dto);
        insertMovieLanguage(dto);
        insertMovieCountry(dto);
        insertMovieActor(dto);
        insertMovieTag(dto);
        insertMovieSet(dto);
        insertMovieAka(dto);
        insertMovieFile(dto);
        return super.insert(dto);
    }

    @Override
    @Transactional
    public boolean update(MovieDTO dto) {
        MovieDTO exist = findByUniqueidList(dto.getMovieUniqueidDTOList());
        if (exist != null && !exist.getId().equals(dto.getId())) {
            throw new ServiceException(3002, "已存在该记录");
        }
        movieUniqueidService.deleteByMovieId(dto.getId());
        movieRatingService.deleteByMovieId(dto.getId());
        movieGenreLinkService.deleteByMovieId(dto.getId());
        movieCountryLinkService.deleteByMovieId(dto.getId());
        movieLanguageLinkService.deleteByMovieId(dto.getId());
        movieActorLinkService.deleteByMovieId(dto.getId());
        movieTagService.deleteByMovieId(dto.getId());
        movieAkaService.deleteByMovieId(dto.getId());

        insertMovieRating(dto);
        insertMovieUniqueid(dto);
        insertMovieGenre(dto);
        insertMovieLanguage(dto);
        insertMovieCountry(dto);
        insertMovieActor(dto);
        insertMovieTag(dto);
        insertMovieAka(dto);
        return super.update(dto);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        Long movieId = (Long) id;
        movieUniqueidService.deleteByMovieId(movieId);
        movieRatingService.deleteByMovieId(movieId);
        movieGenreLinkService.deleteByMovieId(movieId);
        movieCountryLinkService.deleteByMovieId(movieId);
        movieLanguageLinkService.deleteByMovieId(movieId);
        movieActorLinkService.deleteByMovieId(movieId);
        movieSetLinkService.deleteByMovieId(movieId);
        movieTagService.deleteByMovieId(movieId);
        movieAkaService.deleteByMovieId(movieId);
        movieFileService.deleteByMovieId(movieId);
        return super.deleteById(id);
    }

    private void insertMovieFile(MovieDTO movieDTO) {
        MovieFileDTO movieFileDTO = movieDTO.getMovieFileDTO();
        movieFileDTO.setMovieId(movieDTO.getId());
        movieFileService.insert(movieFileDTO);
    }

    private void insertMovieAka(MovieDTO movieDTO) {
        List<MovieAkaDTO> movieAkaDTOList = movieDTO.getMovieAkaDTOList();
        if (CollectionUtils.isEmpty(movieAkaDTOList)) {
            return;
        }
        for (MovieAkaDTO movieAkaDTO : movieAkaDTOList) {
            movieAkaDTO.setMovieId(movieDTO.getId());
            movieAkaService.insert(movieAkaDTO);
        }
    }

    private void insertMovieSet(MovieDTO movieDTO) {
        List<MovieSetDTO> movieSetDTOList = movieDTO.getMovieSetDTOList();
        if (CollectionUtils.isEmpty(movieSetDTOList)) {
            return;
        }
        for (MovieSetDTO dto : movieSetDTOList) {
            MovieSetDTO movieSetDTO = movieSetService.findByMc(dto.getMc());
            if (movieSetDTO == null) {
                movieSetDTO = movieSetService.insert(dto);
            }
            movieSetLinkService.insert(movieDTO.getId(), movieSetDTO.getId());
        }
    }

    private void insertMovieTag(MovieDTO movieDTO) {
        List<MovieTagDTO> movieTagDTOList = movieDTO.getMovieTagDTOList();
        if (CollectionUtils.isEmpty(movieTagDTOList)) {
            return;
        }
        for (MovieTagDTO dto : movieTagDTOList) {
            dto.setMovieId(movieDTO.getId());
            movieTagService.insert(dto);
        }
    }

    private void insertMovieUniqueid(MovieDTO movieDTO) {
        List<MovieUniqueidDTO> movieUniqueidDTOList = movieDTO.getMovieUniqueidDTOList();
        if (CollectionUtils.isEmpty(movieUniqueidDTOList)) {
            return;
        }
        for (MovieUniqueidDTO dto : movieUniqueidDTOList) {
            dto.setMovieId(movieDTO.getId());
            movieUniqueidService.insert(dto);
        }
    }

    private void insertMovieRating(MovieDTO movieDTO) {
        List<MovieRatingDTO> movieRatingDTOList = movieDTO.getMovieRatingDTOList();
        if (CollectionUtils.isEmpty(movieRatingDTOList)) {
            return;
        }
        for (MovieRatingDTO dto : movieRatingDTOList) {
            dto.setMovieId(movieDTO.getId());
            movieRatingService.insert(dto);
        }
    }

    private void insertMovieGenre(MovieDTO movieDTO) {
        List<MovieGenreDTO> movieGenreDTOList = movieDTO.getMovieGenreDTOList();
        if (CollectionUtils.isEmpty(movieGenreDTOList)) {
            return;
        }
        for (MovieGenreDTO dto : movieGenreDTOList) {
            if (dto.getId() != null) {
                movieGenreLinkService.insert(movieDTO.getId(), dto.getId());
                continue;
            }
            MovieGenreDTO movieGenreDTO = movieGenreService.findByMc(dto.getMc());
            if (movieGenreDTO == null) {
                movieGenreDTO = movieGenreService.insert(dto);
            }
            movieGenreLinkService.insert(movieDTO.getId(), movieGenreDTO.getId());
        }
    }

    private void insertMovieLanguage(MovieDTO movieDTO) {
        List<MovieLanguageDTO> movieLanguageDTOList = movieDTO.getMovieLanguageDTOList();
        if (CollectionUtils.isEmpty(movieLanguageDTOList)) {
            return;
        }
        for (MovieLanguageDTO dto : movieLanguageDTOList) {
            if (dto.getId() != null) {
                movieLanguageLinkService.insert(movieDTO.getId(), dto.getId());
                continue;
            }
            MovieLanguageDTO movieLanguageDTO = movieLanguageService.findByMc(dto.getMc());
            if (movieLanguageDTO == null) {
                movieLanguageDTO = movieLanguageService.insert(dto);
            }
            movieLanguageLinkService.insert(movieDTO.getId(), movieLanguageDTO.getId());
        }
    }

    private void insertMovieCountry(MovieDTO movieDTO) {
        List<MovieCountryDTO> movieCountryDTOList = movieDTO.getMovieCountryDTOList();
        if (CollectionUtils.isEmpty(movieCountryDTOList)) {
            return;
        }
        for (MovieCountryDTO dto : movieCountryDTOList) {
            if (dto.getId() != null) {
                movieCountryLinkService.insert(movieDTO.getId(), dto.getId());
                continue;
            }
            MovieCountryDTO movieCountryDTO = movieCountryService.findByMc(dto.getMc());
            if (movieCountryDTO == null) {
                movieCountryDTO = movieCountryService.insert(dto);
            }
            movieCountryLinkService.insert(movieDTO.getId(), movieCountryDTO.getId());
        }
    }

    private void insertMovieActor(MovieDTO movieDTO) {
        List<MovieActorDTO> directorList = movieDTO.getDirectorList();
        List<MovieActorDTO> writerList = movieDTO.getWriterList();
        List<MovieActorDTO> actorList = movieDTO.getActorList();
        if (CollectionUtils.isNotEmpty(directorList)) {
            for (MovieActorDTO dto : directorList) {
                if (dto.getId() != null) {
                    movieActorLinkService.insert(movieDTO.getId(), dto.getId(), ActorRole.Director);
                    continue;
                }
                MovieActorDTO movieActorDTO = movieActorService.findByXm(dto.getXm());
                if (movieActorDTO == null) {
                    movieActorDTO = movieActorService.insert(dto);
                }
                movieActorLinkService.insert(movieDTO.getId(), movieActorDTO.getId(), ActorRole.Director);
            }
        }
        if (CollectionUtils.isNotEmpty(writerList)) {
            for (MovieActorDTO dto : writerList) {
                if (dto.getId() != null) {
                    movieActorLinkService.insert(movieDTO.getId(), dto.getId(), ActorRole.Writer);
                    continue;
                }
                MovieActorDTO movieActorDTO = movieActorService.findByXm(dto.getXm());
                if (movieActorDTO == null) {
                    movieActorDTO = movieActorService.insert(dto);
                }
                movieActorLinkService.insert(movieDTO.getId(), movieActorDTO.getId(), ActorRole.Writer);
            }
        }
        if (CollectionUtils.isNotEmpty(actorList)) {
            for (MovieActorDTO dto : actorList) {
                if (dto.getId() != null) {
                    movieActorLinkService.insert(movieDTO.getId(), dto.getId(), ActorRole.Actor);
                    continue;
                }
                MovieActorDTO movieActorDTO = movieActorService.findByXm(dto.getXm());
                if (movieActorDTO == null) {
                    movieActorDTO = movieActorService.insert(dto);
                }
                movieActorLinkService.insert(movieDTO.getId(), movieActorDTO.getId(), ActorRole.Actor);
            }
        }
    }

    private boolean match(String[] filterPaths, String path) {
        if (filterPaths == null) {
            return false;
        }
        for (String filterPath : filterPaths) {
            return antPathMatcher.match(filterPath, path);
        }
        return false;
    }

    private void processNFO(File file) throws JAXBException {
        MovieNFO movieNFO = parseNfo(file);
        MovieDTO movieDTO = ConvertUtils.convert(movieNFO);
        MovieFileDTO movieFileDTO = movieDTO.getMovieFileDTO();
        if (movieFileDTO == null) {
            movieFileDTO = new MovieFileDTO();
        }
        movieFileDTO.setWjm(StringUtils.defaultString(movieFileDTO.getWjm(), movieNFO.getOriginalFilename()));
        movieFileDTO.setWjlj(StringUtils.defaultString(movieFileDTO.getWjlj(), file.getParent().toString()));
        insert(movieDTO);
    }

    protected MovieNFO parseNfo(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(MovieNFO.class);
        Unmarshaller movieUnmarshaller = context.createUnmarshaller();
        return (MovieNFO) movieUnmarshaller.unmarshal(file);
    }

}