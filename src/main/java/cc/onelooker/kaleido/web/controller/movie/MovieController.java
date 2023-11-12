package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.system.FileInfoConvert;
import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.dto.system.req.FileInfoUploadReq;
import cc.onelooker.kaleido.dto.system.resp.FileInfoUploadResp;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.service.movie.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.annotation.CacheControl;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.convert.movie.MovieConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.dto.movie.exp.MovieExp;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import java.util.stream.Collectors;

/**
 * 电影前端控制器
 *
 * @author cyetstar
 * @date 2023-04-18 23:04:56
 */

@Api(tags = "电影")
@RestController
@RequestMapping("/movie")
public class MovieController extends AbstractCrudController<MovieDTO> {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRatingService movieRatingService;

    @Autowired
    private MovieUniqueidService movieUniqueidService;

    @Autowired
    private MovieTagService movieTagService;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieSetService movieSetService;

    @Autowired
    private MovieAkaService movieAkaService;

    @Autowired
    private MovieFileService movieFileService;

    @Autowired
    private MovieFileVideoService movieFileVideoService;

    @Autowired
    private MovieFileAudioService movieFileAudioService;

    @Autowired
    private MovieFileSubtitleService movieFileSubtitleService;

    private boolean importing = false;

    @Override
    protected IBaseService getService() {
        return movieService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影")
    public CommonResult<PageResult<MoviePageResp>> page(MoviePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieConvert.INSTANCE::convertToDTO, MovieConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影详情")
    public CommonResult<MovieViewResp> view(Long id) {
        MovieDTO movieDTO = movieService.findById(id);

        List<MovieUniqueidDTO> uniqueidDTOList = movieUniqueidService.listByMovieId(id);
        List<MovieRatingDTO> ratingDTOList = movieRatingService.listByMovieId(id);
        String doubanId = uniqueidDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_DOUBAN)).map(s -> s.getUid()).findFirst().orElse(null);
        String imdbId = uniqueidDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_IMDB)).map(s -> s.getUid()).findFirst().orElse(null);
        MovieRatingDTO doubanRatingDTO = ratingDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_DOUBAN)).findFirst().orElse(new MovieRatingDTO());
        MovieRatingDTO imdbRatingDTO = ratingDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), KaleidoConstants.BSLX_IMDB)).findFirst().orElse(new MovieRatingDTO());
        List<MovieTagDTO> tagDTOList = movieTagService.listByMovieId(id);
        List<MovieSetDTO> setDTOList = movieSetService.listByMovieId(id);
        List<MovieAkaDTO> akaDTOList = movieAkaService.listByMovieId(id);
        MovieFileDTO movieFileDTO = movieFileService.findByMovieId(id);
        if (movieFileDTO != null) {
            MovieFileVideoDTO movieFileVideoDTO = movieFileVideoService.findByMovieFileId(movieFileDTO.getId());
            List<MovieFileAudioDTO> movieFileAudioDTOList = movieFileAudioService.listByMovieFileId(movieFileDTO.getId());
            List<MovieFileSubtitleDTO> movieFileSubtitleDTOList = movieFileSubtitleService.listByMovieFileId(movieFileDTO.getId());
            movieFileDTO.setMovieFileVideoDTO(movieFileVideoDTO);
            movieFileDTO.setMovieFileAudioDTOList(movieFileAudioDTOList);
            movieFileDTO.setMovieFileSubtitleDTOList(movieFileSubtitleDTOList);
        }
        List<String> dylxList = StringUtils.isNotEmpty(movieDTO.getDylx()) ? Lists.newArrayList(StringUtils.split(movieDTO.getDylx(), Constants.COMMA)) : Lists.newArrayList();
        List<String> gjdqList = StringUtils.isNotEmpty(movieDTO.getGjdq()) ? Lists.newArrayList(StringUtils.split(movieDTO.getGjdq(), Constants.COMMA)) : Lists.newArrayList();
        List<String> dyyyList = StringUtils.isNotEmpty(movieDTO.getDyyy()) ? Lists.newArrayList(StringUtils.split(movieDTO.getDyyy(), Constants.COMMA)) : Lists.newArrayList();
        List<String> tagList = tagDTOList.stream().map(MovieTagDTO::getMc).collect(Collectors.toList());
        List<String> akaList = akaDTOList.stream().map(MovieAkaDTO::getDymc).collect(Collectors.toList());
        List<MovieActorDTO> directorDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Director.name());
        List<MovieActorDTO> writerDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Writer.name());
        List<MovieActorDTO> actorDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Actor.name());

        MovieViewResp.MovieViewRatingResp doubanRating = MovieConvert.INSTANCE.convertToViewRatingResp(doubanRatingDTO);
        MovieViewResp.MovieViewRatingResp imdbRating = MovieConvert.INSTANCE.convertToViewRatingResp(imdbRatingDTO);
        List<MovieViewResp.MovieViewSetResp> setList = MovieConvert.INSTANCE.convertToViewSetResp(setDTOList);
        List<MovieViewResp.MovieViewActorResp> directorList = MovieConvert.INSTANCE.convertToViewActorResp(directorDTOList);
        List<MovieViewResp.MovieViewActorResp> writerList = MovieConvert.INSTANCE.convertToViewActorResp(writerDTOList);
        List<MovieViewResp.MovieViewActorResp> actorList = MovieConvert.INSTANCE.convertToViewActorResp(actorDTOList);
        MovieViewResp.MovieViewFileResp file = MovieConvert.INSTANCE.convertToViewFileResp(movieFileDTO);
        MovieViewResp resp = MovieConvert.INSTANCE.convertToViewResp(movieDTO);

        resp.setDoubanId(doubanId);
        resp.setImdbId(imdbId);
        resp.setDoubanRating(doubanRating);
        resp.setImdbRating(imdbRating);
        resp.setDylxList(dylxList);
        resp.setGjdqList(gjdqList);
        resp.setDyyyList(dyyyList);
        resp.setTagList(tagList);
        resp.setSetList(setList);
        resp.setAkaList(akaList);
        resp.setDirectorList(directorList);
        resp.setWriterList(writerList);
        resp.setActorList(actorList);
        resp.setFile(file);
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影")
    public CommonResult<MovieCreateResp> create(@RequestBody MovieCreateReq req) {
        return super.create(req, MovieConvert.INSTANCE::convertToDTO, MovieConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影")
    public CommonResult<Boolean> update(@RequestBody MovieUpdateReq req) {
        MovieDTO movieDTO = MovieConvert.INSTANCE.convertToDTO(req);
        MovieUniqueidDTO doubanUniqueidDTO = MovieUniqueidDTO.douban(req.getDoubanId());
        MovieUniqueidDTO imdbUniqueidDTO = MovieUniqueidDTO.imdb(req.getImdbId());
        MovieRatingDTO doubanRatingDTO = MovieRatingDTO.douban(req.getDoubanRating().getPjf(), req.getDoubanRating().getTps());
        MovieRatingDTO imdbRatingDTO = MovieRatingDTO.imdb(req.getImdbRating().getPjf(), req.getImdbRating().getTps());
        List<MovieUniqueidDTO> movieUniqueidDTOList = ImmutableList.of(doubanUniqueidDTO, imdbUniqueidDTO);
        List<MovieRatingDTO> movieRatingDTOList = ImmutableList.of(doubanRatingDTO, imdbRatingDTO);
        List<MovieAkaDTO> movieAkaDTOList = req.getAkaList().stream().map(s -> {
            MovieAkaDTO movieAkaDTO = new MovieAkaDTO();
            movieAkaDTO.setDymc(s);
            return movieAkaDTO;
        }).collect(Collectors.toList());
        List<MovieTagDTO> movieTagDTOList = req.getTagList().stream().map(s -> {
            MovieTagDTO movieTagDTO = new MovieTagDTO();
            movieTagDTO.setMc(s);
            return movieTagDTO;
        }).collect(Collectors.toList());
        movieDTO.setMovieUniqueidDTOList(movieUniqueidDTOList);
        movieDTO.setMovieRatingDTOList(movieRatingDTOList);
        movieDTO.setDylx(StringUtils.join(req.getDylxList(), Constants.COMMA));
        movieDTO.setGjdq(StringUtils.join(req.getGjdqList(), Constants.COMMA));
        movieDTO.setDyyy(StringUtils.join(req.getDyyyList(), Constants.COMMA));
        movieDTO.setMovieAkaDTOList(movieAkaDTOList);
        movieDTO.setMovieTagDTOList(movieTagDTOList);
        boolean result = movieService.update(movieDTO);
        return CommonResult.success(result);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影")
    public void export(MoviePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieExp.class, MovieConvert.INSTANCE::convertToDTO, MovieConvert.INSTANCE::convertToExp, response);
    }

    @GetMapping("checkImportNFO")
    public CommonResult<Boolean> checkImportNFO() {
        return CommonResult.success(importing);
    }

    @PostMapping("importNFO")
    public CommonResult<Boolean> importNFO() {
        importing = true;
        String libraryPath = ConfigUtils.getSysConfig("movieLibraryPath");
        String excludePath = ConfigUtils.getSysConfig("movieExcludePath");
        String[] excludePaths = StringUtils.isNotBlank(excludePath) ? StringUtils.split(excludePath, Constants.LINE_END_TAG) : null;
        try {
            movieService.importNFO(libraryPath, excludePaths);
        } finally {
            importing = false;
            return CommonResult.success(true);
        }
    }

    @ApiOperation("上传海报")
    @PostMapping("uploadPoster")
    public CommonResult<Boolean> uploadPoster(MovieUploadPosterReq req) throws IOException {
        movieService.uploadPoster(req.getId(), req.getFile());
        return CommonResult.success(true);
    }

    @GetMapping("/{type}/{id}")
    public HttpEntity<byte[]> image(@PathVariable String type, @PathVariable Long id) {
        MovieFileDTO movieFileDTO = movieFileService.findByMovieId(id);
        try {
            File file = Paths.get(movieFileDTO.getWjlj(), type + ".jpg").toFile();
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            IOUtils.copy(fis, bos);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bos.toByteArray());
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

}