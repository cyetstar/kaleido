package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MovieBasicConvert;
import cc.onelooker.kaleido.dto.MovieActorDTO;
import cc.onelooker.kaleido.dto.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.*;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MovieActorService;
import cc.onelooker.kaleido.service.MovieBasicCollectionService;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieManager;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.KaleidoConstants;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private MovieActorService movieActorService;

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Autowired
    private MovieManager movieManager;

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
        pageParam.setOrderBy("DESC:added_at");
        return super.page(req, pageParam, MovieBasicConvert.INSTANCE::convertToDTO, MovieBasicConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影详情")
    public CommonResult<MovieBasicViewResp> view(String id) {
        MovieBasicDTO movieBasicDTO = movieManager.findMovieBasic(id);
        MovieBasicViewResp resp = MovieBasicConvert.INSTANCE.convertToViewResp(movieBasicDTO);
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
        MovieBasicDTO dto = MovieBasicConvert.INSTANCE.convertToDTO(req);
        if (req.getDirectorIdList() != null) {
            dto.setDirectorList(req.getDirectorIdList().stream().map(s -> movieActorService.findById(s)).collect(Collectors.toList()));
        }
        if (req.getWriterIdList() != null) {
            dto.setWriterList(req.getWriterIdList().stream().map(s -> movieActorService.findById(s)).collect(Collectors.toList()));
        }
        if (req.getActorList() != null) {
            dto.setActorList(req.getActorList().stream().map(s -> {
                MovieActorDTO movieActorDTO = movieActorService.findById(s.getId());
                movieActorDTO.setPlayRole(s.getPlayRole());
                return movieActorDTO;
            }).collect(Collectors.toList()));
        }
        movieManager.saveMovie(dto);
        return CommonResult.success(true);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        if (id != null) {
            Arrays.stream(id).forEach(s -> movieManager.deleteMovieBasic(s));
        }
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
        Movie movie = tmmApiService.findMovie(req.getDoubanId(), req.getImdbId(), req.getTmdbId());
        movieManager.matchMovie(req.getId(), movie);
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(id);
        Path filePath = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
        return CommonResult.success(filePath.toString());
    }

    @PostMapping("uploadPoster")
    @ApiOperation(value = "上传海报")
    public CommonResult<Boolean> uploadPoster(MovieBasicUploadPosterReq req) throws IOException {
        Files.write(Paths.get(req.getPath(), KaleidoConstants.MOVIE_POSTER), req.getFile().getBytes());
        plexApiService.refreshMovieById(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("downloadPoster")
    public CommonResult<Boolean> downloadPoster(@RequestBody MovieBasicDownloadPosterReq req) {
        MovieBasicDTO movieBasicDTO = movieBasicService.findById(req.getId());
        Path filePath = KaleidoUtils.getMoviePath(movieBasicDTO.getPath());
        File file = filePath.resolve(KaleidoConstants.MOVIE_POSTER).toFile();
        HttpUtil.downloadFile(req.getUrl(), file);
        return CommonResult.success(true);
    }

    @GetMapping("listByCollectionId")
    public CommonResult<List<MovieBasicListByCollectionIdResp>> listByCollectionId(String collectionId) {
        List<MovieBasicCollectionDTO> movieBasicCollectionDTOList = movieBasicCollectionService.listByCollectionId(collectionId);
        List<String> movieIdList = movieBasicCollectionDTOList.stream().map(MovieBasicCollectionDTO::getMovieId).collect(Collectors.toList());
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

    @PostMapping("matchPath")
    @ApiOperation(value = "匹配文件信息")
    public CommonResult<Boolean> matchPath(@RequestBody MovieBasicMatchPathReq req) {
        movieManager.matchPath(Paths.get(req.getPath()), req.getDoubanId(), req.getTmdbId(), req.getTvdbId());
        return CommonResult.success(true);
    }

}