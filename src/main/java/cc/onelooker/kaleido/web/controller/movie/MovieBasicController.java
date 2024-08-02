package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicConvert;
import cc.onelooker.kaleido.dto.movie.*;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.movie.*;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cc.onelooker.kaleido.utils.NioFileUtils;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieManager movieManager;

    @Autowired
    private AsyncTaskManager taskManager;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService getService() {
        return movieBasicService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影")
    public CommonResult<PageResult<MovieBasicPageResp>> page(MovieBasicPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:id");
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
        if (id != null) {
            Arrays.stream(id).forEach(s -> movieManager.deleteMovie(s));
        }
        return CommonResult.success(true);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        taskManager.syncPlexMovie();
        return CommonResult.success(true);
    }

    @PostMapping("readNFO")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> readNFO() {
        taskManager.readNFO();
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料")
    public CommonResult<Boolean> syncPlexById(@RequestBody MovieBasicSyncPlexByIdReq req) {
        movieManager.syncMovie(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("refreshPlexById")
    @ApiOperation(value = "刷新资料")
    public CommonResult<Boolean> refreshPlexById(@RequestBody MovieBasicRefreshPlexByIdReq req) {
        plexApiService.refreshMovieById(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("readNFOById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> readNFOById(@RequestBody MovieBasicReadNFOByIdReq req) {
        movieManager.readNFO(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("exportNFO")
    @ApiOperation(value = "输出NFO")
    public CommonResult<Boolean> exportNFO(@RequestBody MovieBasicWriteNFOReq req) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(req.getId());
        movieManager.exportNFO(movieBasicDTO);
        return CommonResult.success(true);
    }

    @PostMapping("searchInfo")
    @ApiOperation(value = "查询信息")
    public CommonResult<List<MovieBasicSearchInfoResp>> searchInfo(@RequestBody MovieBasicSearchInfoReq req) {
        List<Movie> movieList = tmmApiService.searchMovie(req.getKeyword(), req.getType());
        List<MovieBasicSearchInfoResp> respList = Lists.newArrayList();
        for (Movie movie : movieList) {
            respList.add(MovieBasicConvert.INSTANCE.convertToSearchInfoResp(movie));
        }
        return CommonResult.success(respList);
    }

    @PostMapping("matchInfo")
    @ApiOperation(value = "匹配信息")
    public CommonResult<Boolean> matchInfo(@RequestBody MovieBasicMatchInfoReq req) {
        movieManager.matchInfo(req.getId(), req.getDoubanId(), req.getImdbId(), req.getTmdbId());
        return CommonResult.success(true);
    }

    @GetMapping("viewNFO")
    @ApiOperation(value = "查看NFO")
    public HttpEntity<byte[]> viewNFO(Long id) throws IOException {
        Metadata metadata = plexApiService.findMovieById(id);
        Path filePath = KaleidoUtils.getMoviePath(metadata.getMedia().getPart().getFile());
        File file = filePath.resolveSibling("movie.nfo").toFile();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(FileUtils.readFileToByteArray(file));
    }

    @PostMapping("autoCopy")
    @ApiOperation(value = "自动拷贝")
    public CommonResult<Boolean> autoCopy(@RequestBody MovieBasicAutoCopyReq req) throws IOException {
        String movieLibraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        String movieDownloadPath = ConfigUtils.getSysConfig(ConfigKey.movieDownloadPath);
        for (String path : req.getPathList()) {
            if (StringUtils.isBlank(path)) {
                continue;
            }
            NioFileUtils.moveDir(Paths.get(movieDownloadPath, path), Paths.get(movieLibraryPath, path), StandardCopyOption.REPLACE_EXISTING);
        }
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(Long id) {
        Metadata metadata = plexApiService.findMovieById(id);
        Path filePath = KaleidoUtils.getMoviePath(metadata.getMedia().getPart().getFile());
        return CommonResult.success(filePath.getParent().toString());
    }

    @PostMapping("uploadPoster")
    @ApiOperation(value = "上传海报")
    public CommonResult<Boolean> uploadPoster(MovieBasicUploadPosterReq req) throws IOException {
        Files.write(Paths.get(req.getPath(), "poster.jpg"), req.getFile().getBytes());
        plexApiService.refreshMovieById(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("downloadPoster")
    public CommonResult<Boolean> downloadPoster(@RequestBody MovieBasicDownloadPosterReq req) {
        Metadata metadata = plexApiService.findMovieById(req.getId());
        Path filePath = KaleidoUtils.getMoviePath(metadata.getMedia().getPart().getFile());
        File file = filePath.resolveSibling("poster.jpg").toFile();
        HttpUtil.downloadFile(req.getUrl(), file);
        return CommonResult.success(true);
    }

    @GetMapping("listByCollectionId")
    public CommonResult<List<MovieBasicListByCollectionIdResp>> listByCollectionId(Long collectionId) {
        List<MovieBasicCollectionDTO> movieBasicCollectionDTOList = movieBasicCollectionService.listByCollectionId(collectionId);
        List<Long> movieIdList = movieBasicCollectionDTOList.stream().map(MovieBasicCollectionDTO::getMovieId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(movieIdList)) {
            return CommonResult.success(Lists.newArrayList());
        }
        MovieBasicDTO param = new MovieBasicDTO();
        param.setIdList(movieIdList);
        List<MovieBasicDTO> movieBasicDTOList = movieBasicService.list(param);
        List<MovieBasicListByCollectionIdResp> respList = Lists.newArrayList();
        for (MovieBasicDTO movieBasicDTO : movieBasicDTOList) {
            MovieBasicListByCollectionIdResp resp = MovieBasicConvert.INSTANCE.convertToListByCollectionIdResp(movieBasicDTO);
            respList.add(resp);
        }
        return CommonResult.success(respList);
    }

    @PostMapping("checkThreadStatus")
    @ApiOperation(value = "检查发布收藏状态")
    public CommonResult<Boolean> checkThreadStatus() {
        taskManager.checkThreadStatus();
        return CommonResult.success(true);
    }

    @PostMapping("matchPath")
    @ApiOperation(value = "匹配文件信息")
    public CommonResult<Boolean> matchPath(@RequestBody MovieBasicMatchPathReq req) {
        movieManager.matchPath(Paths.get(req.getPath()), req.getDoubanId(), req.getTmdbId(), req.getTvdbId());
        return CommonResult.success(true);
    }

    @PostMapping("moveMovieFolder")
    @ApiOperation(value = "重命名文件夹")
    public CommonResult<Boolean> moveMovieFolder() {
        String movieLibraryPath = ConfigUtils.getSysConfig(ConfigKey.movieLibraryPath);
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        try (Stream<Path> paths = Files.list(Paths.get(movieLibraryPath))) {
            List<Path> pathList = paths.collect(Collectors.toList());
            int count = 0;
            for (Path p : pathList) {
                count++;
                if (!Files.isDirectory(p)) {
                    continue;
                }
                String fileName = p.getFileName().toString();
                if (StringUtils.equalsAny(fileName, "#recycle", "2020s", "2010s", "2000s", "1990s", "1980s", "1970s", "1960s", "1950s", "1940s", "1930s", "1920s", "1910s", "1900s", "1890s")) {
                    continue;
                }
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.find()) {
                    String year = matcher.group(1);
                    String decade = year.substring(0, 3) + "0s";
                    Path decadePath = Paths.get(movieLibraryPath, decade);
                    if (!Files.exists(decadePath)) {
                        Files.createDirectory(decadePath);
                    }
                    NioFileUtils.moveDir(p, decadePath, StandardCopyOption.REPLACE_EXISTING);
                }
                if (count % 1000 == 0) {
                    log.info("已经处理了 {} 个文件夹", count);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CommonResult.success(true);
    }

    @PostMapping("analyze")
    @ApiOperation(value = "分析")
    public CommonResult<Boolean> analyze(@RequestBody MovieBasicAnalyzeReq req) {
        movieManager.analyze(req.getId());
        return CommonResult.success(true);
    }

}