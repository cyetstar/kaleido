package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.convert.music.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicSearchDoubanResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicViewResp;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.exp.movie.MovieBasicExp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.movie.*;
import cc.onelooker.kaleido.third.douban.DoubanApiService;
import cc.onelooker.kaleido.third.douban.Movie;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 电影前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Slf4j
@Api(tags = "电影")
@RestController
@RequestMapping("/movieBasic")
public class MovieBasicController extends AbstractCrudController<MovieBasicDTO> {

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private MovieBasicCountryService movieBasicCountryService;

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Autowired
    private MovieBasicLanguageService movieBasicLanguageService;

    @Autowired
    private MovieActorService movieActorService;

    @Autowired
    private MovieAkaService movieAkaService;

    @Autowired
    private MovieTagService movieTagService;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private AsyncTaskManager taskManager;

    @Autowired
    private DoubanApiService doubanApiService;

    @Override
    protected IBaseService getService() {
        return movieBasicService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影")
    public CommonResult<PageResult<MovieBasicPageResp>> page(MovieBasicPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:added_at");
        return super.page(req, pageParam, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影详情")
    public CommonResult<MovieBasicViewResp> view(Long id) {
        MovieBasicViewResp resp = super.doView(id, MovieBasicConvert.INSTANCE::convertToViewResp);
        List<MovieBasicCountryDTO> movieBasicCountryDTOList = movieBasicCountryService.listByMovieId(id);
        List<MovieBasicGenreDTO> movieBasicGenreDTOList = movieBasicGenreService.listByMovieId(id);
        List<MovieBasicLanguageDTO> movieBasicLanguageDTOList = movieBasicLanguageService.listByMovieId(id);
        List<MovieActorDTO> movieActorDTOList = movieActorService.listByMovieId(id);
        List<MovieAkaDTO> movieAkaDTOList = movieAkaService.listByMovieId(id);
        List<MovieTagDTO> movieTagDTOList = movieTagService.listByMovieId(id);
        resp.setCountryList(movieBasicCountryDTOList.stream().map(s -> new MovieBasicViewResp.Country(String.valueOf(s.getCountryId()))).collect(Collectors.toList()));
        resp.setGenreList(movieBasicGenreDTOList.stream().map(s -> new MovieBasicViewResp.Genre(String.valueOf(s.getGenreId()))).collect(Collectors.toList()));
        resp.setLanguageList(movieBasicLanguageDTOList.stream().map(s -> new MovieBasicViewResp.Language(String.valueOf(s.getLanguageId()))).collect(Collectors.toList()));
        resp.setDirectorList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Director.name())).map(MovieBasicConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        resp.setWriterList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Writer.name())).map(MovieBasicConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        resp.setActorList(movieActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).map(MovieBasicConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        resp.setAkaList(movieAkaDTOList.stream().map(MovieAkaDTO::getTitle).collect(Collectors.toList()));
        resp.setTagList(movieTagDTOList.stream().map(MovieTagDTO::getTag).collect(Collectors.toList()));
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影")
    public CommonResult<MovieBasicCreateResp> create(@RequestBody MovieBasicCreateReq req) {
        return super.create(req, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影")
    public CommonResult<Boolean> update(@RequestBody MovieBasicUpdateReq req) {
        return super.update(req, MovieBasicConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影")
    public void export(MovieBasicPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicExp.class, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        taskManager.syncPlexMovie();
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlexById(@RequestBody MovieBasicSyncPlexReq req) {
        movieManager.syncPlexMovieById(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("readNFO")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> readNFO(@RequestBody MovieBasicReadNFOReq req) throws JAXBException {
        movieManager.readNFO(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("searchDouban")
    @ApiOperation(value = "查询豆瓣")
    public CommonResult<List<MovieBasicSearchDoubanResp>> searchDouban(@RequestBody MovieBasicSearchDoubanReq req) {
        List<Movie> movieList = doubanApiService.searchMovie(req.getKeywords());
        List<MovieBasicSearchDoubanResp> respList = Lists.newArrayList();
        for (Movie movie : movieList) {
            respList.add(MovieBasicConvert.INSTANCE.convertToSearchDoubanResp(movie));
        }
        return CommonResult.success(respList);
    }

    @PostMapping("matchDouban")
    @ApiOperation(value = "匹配豆瓣")
    public CommonResult<Boolean> matchDouban(@RequestBody MovieBasicMatchDoubanReq req) {
        return CommonResult.success(movieBasicService.updateDoubanId(req.getId(), req.getDoubanId()));
    }
}