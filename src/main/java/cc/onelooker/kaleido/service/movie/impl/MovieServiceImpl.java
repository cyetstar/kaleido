package cc.onelooker.kaleido.service.movie.impl;

import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.nfo.MovieNFO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.movie.*;
import cc.onelooker.kaleido.utils.ConvertUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cn.hutool.core.util.IdUtil;
import com.zjjcnt.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.onelooker.kaleido.entity.movie.MovieDO;
import cc.onelooker.kaleido.convert.movie.MovieConvert;
import cc.onelooker.kaleido.mapper.movie.MovieMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
public class MovieServiceImpl extends KaleidoBaseServiceImpl<MovieMapper, MovieDO, MovieDTO> implements MovieService {

    MovieConvert convert = MovieConvert.INSTANCE;

    @Autowired
    private MovieUniqueidService movieUniqueidService;

    @Autowired
    private MovieRatingService movieRatingService;

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
        if (StringUtils.isNotEmpty(dto.getDymc())) {
            query.like(MovieDO::getDymc, dto.getDymc()).or().like(MovieDO::getYdymc, dto.getYdymc());
        }
        query.eq(StringUtils.isNotEmpty(dto.getYdymc()), MovieDO::getYdymc, dto.getYdymc());
        query.eq(Objects.nonNull(dto.getYhpf()), MovieDO::getYhpf, dto.getYhpf());
        query.eq(Objects.nonNull(dto.getYhpf()), MovieDO::getYhpf, dto.getYhpf());
        query.like(StringUtils.isNotEmpty(dto.getDylx()), MovieDO::getDylx, dto.getDylx());
        query.like(StringUtils.isNotEmpty(dto.getGjdq()), MovieDO::getGjdq, dto.getGjdq());
        query.like(StringUtils.isNotEmpty(dto.getDyyy()), MovieDO::getDyyy, dto.getDyyy());
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

    public MovieDTO findByUniqueidList(List<MovieUniqueidDTO> movieUniqueidDTOList) {
        if (CollectionUtils.isEmpty(movieUniqueidDTOList)) {
            return null;
        }
        for (MovieUniqueidDTO movieUniqueidDTO : movieUniqueidDTOList) {
            if (StringUtils.equals(movieUniqueidDTO.getBslx(), "tmdbSet")) {
                continue;
            }
            MovieDTO movieDTO = movieUniqueidService.findByUidAndBslx(movieUniqueidDTO.getUid(), movieUniqueidDTO.getBslx());
            if (movieDTO != null) {
                return movieDTO;
            }
        }
        return null;
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

    private void processNFO(File file) throws JAXBException {
        MovieNFO movieNFO = parseNfo(file);
        MovieDTO movieDTO = ConvertUtils.convert(movieNFO);
        MovieFileDTO movieFileDTO = movieDTO.getMovieFileDTO();
        if (movieFileDTO == null) {
            movieFileDTO = new MovieFileDTO();
        }
        movieFileDTO.setWjm(StringUtils.defaultString(movieFileDTO.getWjm(), movieNFO.getOriginalFilename()));
        movieFileDTO.setWjlj(StringUtils.defaultString(movieFileDTO.getWjlj(), file.getParent().toString()));
        MovieDTO exist = findByUniqueidList(movieDTO.getMovieUniqueidDTOList());
        if (exist != null) {
            throw new ServiceException(3002, "已存在该记录");
        }
        Long id = IdUtil.createSnowflake(1, 1).nextId();
        movieDTO.setId(id);
        MovieRatingDTO movieRatingDTO = movieDTO.getMovieRatingDTOList().stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_DOUBAN) || StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_IMDB)).findFirst().orElse(null);
        if (movieRatingDTO != null) {
            movieDTO.setPf(movieRatingDTO.getPjf());
        }
        insertMovieUniqueid(movieDTO);
        insertMovieRating(movieDTO);
        insertMovieActor(movieDTO);
        insertMovieTag(movieDTO);
        insertMovieSet(movieDTO);
        insertMovieAka(movieDTO);
        insertMovieFile(movieDTO);
        super.insert(movieDTO);
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
        MovieRatingDTO movieRatingDTO = dto.getMovieRatingDTOList().stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_DOUBAN) || StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_IMDB)).findFirst().orElse(null);
        if (movieRatingDTO != null) {
            dto.setPf(movieRatingDTO.getPjf());
        }
        insertMovieUniqueid(dto);
        insertMovieRating(dto);
        insertMovieActor(dto);
        insertMovieTag(dto);
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
        MovieRatingDTO movieRatingDTO = dto.getMovieRatingDTOList().stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_DOUBAN) || StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_IMDB)).findFirst().orElse(null);
        if (movieRatingDTO != null) {
            dto.setPf(movieRatingDTO.getPjf());
        }
        updateMovieUniqueid(dto);
        updateMovieRating(dto);
        updateMovieActor(dto);
        updateMovieTag(dto);
        updateMovieAka(dto);
        return super.update(dto);
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        Long movieId = (Long) id;
        movieUniqueidService.deleteByMovieId(movieId);
        movieRatingService.deleteByMovieId(movieId);
        movieActorLinkService.deleteByMovieId(movieId);
        movieSetLinkService.deleteByMovieId(movieId);
        movieTagService.deleteByMovieId(movieId);
        movieAkaService.deleteByMovieId(movieId);
        movieFileService.deleteByMovieId(movieId);
        return super.deleteById(id);
    }

    @Override
    public void uploadPoster(Long id, MultipartFile multipartFile) throws IOException {
        MovieFileDTO movieFileDTO = movieFileService.findByMovieId(id);
        String wjlj = movieFileDTO.getWjlj();
        File file = Paths.get(wjlj, "poster.jpg").toFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
    }

    private void updateMovieAka(MovieDTO dto) {
        movieAkaService.deleteByMovieId(dto.getId());
        insertMovieAka(dto);
    }

    private void updateMovieTag(MovieDTO dto) {
        movieTagService.deleteByMovieId(dto.getId());
        insertMovieTag(dto);
    }

    private void updateMovieActor(MovieDTO dto) {
        movieActorLinkService.deleteByMovieId(dto.getId());
        insertMovieActor(dto);
    }

    private void updateMovieUniqueid(MovieDTO dto) {
        movieUniqueidService.deleteByMovieIdAndBslx(dto.getId(), KaleidoConstants.BSLX_IMDB);
        movieUniqueidService.deleteByMovieIdAndBslx(dto.getId(), KaleidoConstants.BSLX_DOUBAN);
        insertMovieUniqueid(dto);
    }

    private void updateMovieRating(MovieDTO dto) {
        movieRatingService.deleteByMovieIdAndBslx(dto.getId(), KaleidoConstants.BSLX_IMDB);
        movieRatingService.deleteByMovieIdAndBslx(dto.getId(), KaleidoConstants.BSLX_DOUBAN);
        insertMovieRating(dto);
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

    private MovieNFO parseNfo(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(MovieNFO.class);
        Unmarshaller movieUnmarshaller = context.createUnmarshaller();
        return (MovieNFO) movieUnmarshaller.unmarshal(file);
    }

}