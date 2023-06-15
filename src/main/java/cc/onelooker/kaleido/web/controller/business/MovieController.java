package cc.onelooker.kaleido.web.controller.business;

import cc.onelooker.kaleido.convert.business.MovieActorConvert;
import cc.onelooker.kaleido.dto.business.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.service.business.*;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.annotation.CacheControl;
import com.zjjcnt.common.file.FileInfo;
import com.zjjcnt.common.file.enums.FileStorageType;
import com.zjjcnt.common.file.enums.ThumbnailSize;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.convert.business.MovieConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieExp;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;
import java.util.stream.Collectors;

import com.zjjcnt.common.core.annotation.StringDateFormat;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

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
    private MovieGenreService movieGenreService;

    @Autowired
    private MovieLanguageService movieLanguageService;

    @Autowired
    private MovieCountryService movieCountryService;

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

        List<MovieRatingDTO> ratingDTOList = movieRatingService.listByMovieId(id);
        List<MovieUniqueidDTO> uniqueidDTOList = movieUniqueidService.listByMovieId(id);
        String doubanId = uniqueidDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), "douban")).map(s -> s.getUid()).findFirst().orElseGet(null);
        String imdbId = uniqueidDTOList.stream().filter(s -> StringUtils.equals(s.getBslx(), "imdb")).map(s -> s.getUid()).findFirst().orElseGet(null);

        List<MovieGenreDTO> genreDTOList = movieGenreService.listByMovieId(id);
        List<MovieLanguageDTO> languageDTOList = movieLanguageService.listByMovieId(id);
        List<MovieCountryDTO> countryDTOList = movieCountryService.listByMovieId(id);
        List<MovieTagDTO> tagDTOList = movieTagService.listByMovieId(id);
        List<MovieSetDTO> setDTOList = movieSetService.listByMovieId(id);
        List<MovieAkaDTO> akaDTOList = movieAkaService.listByMovieId(id);

        List<MovieActorDTO> directorDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Director.name());
        List<MovieActorDTO> writerDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Writer.name());
        List<MovieActorDTO> actorDTOList = movieActorService.listByMovieIdAndJs(id, ActorRole.Actor.name());
        List<MovieViewResp.MovieViewRatingResp> ratingList = MovieConvert.INSTANCE.convertToViewRatingResp(ratingDTOList);
        List<MovieViewResp.MovieViewGenreResp> genreList = MovieConvert.INSTANCE.convertToViewGenreResp(genreDTOList);
        List<MovieViewResp.MovieViewLanguageResp> languageList = MovieConvert.INSTANCE.convertToViewLanguageResp(languageDTOList);
        List<MovieViewResp.MovieViewCountryResp> countryList = MovieConvert.INSTANCE.convertToViewCountryResp(countryDTOList);
        List<MovieViewResp.MovieViewTagResp> tagList = MovieConvert.INSTANCE.convertToViewTagResp(tagDTOList);
        List<MovieViewResp.MovieViewSetResp> setList = MovieConvert.INSTANCE.convertToViewSetResp(setDTOList);
        List<MovieViewResp.MovieViewAkaResp> akaList = MovieConvert.INSTANCE.convertToViewAkaResp(akaDTOList);
        List<MovieViewResp.MovieViewActorResp> directorList = MovieConvert.INSTANCE.convertToViewActorResp(directorDTOList);
        List<MovieViewResp.MovieViewActorResp> writerList = MovieConvert.INSTANCE.convertToViewActorResp(writerDTOList);
        List<MovieViewResp.MovieViewActorResp> actorList = MovieConvert.INSTANCE.convertToViewActorResp(actorDTOList);
        MovieViewResp resp = MovieConvert.INSTANCE.convertToViewResp(movieDTO);
        resp.setDoubanId(doubanId);
        resp.setImdbId(imdbId);
        resp.setRatingList(ratingList);
        resp.setGenreList(genreList);
        resp.setLanguageList(languageList);
        resp.setCountryList(countryList);
        resp.setTagList(tagList);
        resp.setSetList(setList);
        resp.setAkaList(akaList);
        resp.setDirectorList(directorList);
        resp.setWriterList(writerList);
        resp.setActorList(actorList);
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
        return super.update(req, MovieConvert.INSTANCE::convertToDTO);
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
        String movieExcludePath = ConfigUtils.getSysConfig("movieExcludePath");
        String[] excludePaths = StringUtils.isNotBlank(movieExcludePath) ? StringUtils.split(movieExcludePath, Constants.COMMA) : null;
        try {
            movieService.importNFO(libraryPath, excludePaths);
        } finally {
            importing = false;
            return CommonResult.success(true);
        }
    }

    @CacheControl(maxAge = 60 * 60 * 24)
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