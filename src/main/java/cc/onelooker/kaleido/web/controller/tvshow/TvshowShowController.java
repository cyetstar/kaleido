package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowShowConvert;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicDownloadPosterReq;
import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowDTO;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.req.*;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowSearchInfoResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.service.tvshow.TvshowActorService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.service.tvshow.TvshowShowGenreService;
import cc.onelooker.kaleido.service.tvshow.TvshowShowService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 剧集前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Slf4j
@Api(tags = "剧集")
@RestController
@RequestMapping("/tvshowShow")
public class TvshowShowController extends AbstractCrudController<TvshowShowDTO> {

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowActorService tvshowActorService;

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private PlexApiService plexApiService;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService getService() {
        return tvshowShowService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集")
    public CommonResult<PageResult<TvshowShowPageResp>> page(TvshowShowPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集详情")
    public CommonResult<TvshowShowViewResp> view(Long id) {
        TvshowShowViewResp resp = doView(id, TvshowShowConvert.INSTANCE::convertToViewResp);
        List<TvshowActorDTO> tvshowActorDTOList = tvshowActorService.listByShowId(id);
        List<TvshowShowGenreDTO> tvshowShowGenreDTOList = tvshowShowGenreService.listByShowId(id);
        resp.setActorList(tvshowActorDTOList.stream().filter(s -> StringUtils.equals(s.getRole(), ActorRole.Actor.name())).map(TvshowShowConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        resp.setGenreList(tvshowShowGenreDTOList.stream().map(s -> new TvshowShowViewResp.Genre(String.valueOf(s.getGenreId()))).collect(Collectors.toList()));
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集")
    public CommonResult<TvshowShowCreateResp> create(@RequestBody TvshowShowCreateReq req) {
        return super.create(req, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集")
    public CommonResult<Boolean> update(@RequestBody TvshowShowUpdateReq req) {
        return super.update(req, TvshowShowConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("searchInfo")
    @ApiOperation(value = "查询信息")
    public CommonResult<List<TvshowShowSearchInfoResp>> searchInfo(@RequestBody TvshowShowSearchInfoReq req) {
        List<Movie> movieList = tmmApiService.searchMovie(req.getKeyword(), req.getType());
        List<TvshowShowSearchInfoResp> respList = Lists.newArrayList();
        for (Movie movie : movieList) {
            respList.add(TvshowShowConvert.INSTANCE.convertToSearchInfoResp(movie));
        }
        return CommonResult.success(respList);
    }

    @PostMapping("downloadPoster")
    public CommonResult<Boolean> downloadPoster(@RequestBody MovieBasicDownloadPosterReq req) {
        Metadata metadata = plexApiService.findMovieById(req.getId());
        String folder = KaleidoUtils.getMovieFolder(metadata.getMedia().getPart().getFile());
        File file = Paths.get(folder, "poster.jpg").toFile();
        HttpUtil.downloadFile(req.getUrl(), file);
        return CommonResult.success(true);
    }

    @PostMapping("matchPath")
    @ApiOperation(value = "匹配文件信息")
    public CommonResult<Boolean> matchPath(@RequestBody TvshowShowMatchPathReq req) {
        List<Path> pathList = req.getPaths().stream().map(Paths::get).collect(Collectors.toList());
        tvshowManager.matchPath(pathList, req.getDoubanId());
        return CommonResult.success(true);
    }

    @PostMapping("matchInfo")
    @ApiOperation(value = "匹配信息")
    public CommonResult<Boolean> matchInfo(@RequestBody TvshowShowMatchInfoReq req) {
        tvshowManager.matchInfo(req.getId(), req.getDoubanId(), req.getImdbId(), req.getTmdbId());
        return CommonResult.success(true);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料")
    public CommonResult<Boolean> syncPlex(@RequestBody TvshowShowSyncPlexReq req) {
        tvshowManager.syncPlex(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("readNFO")
    @ApiOperation(value = "读取NFO")
    public CommonResult<Boolean> readNFO(@RequestBody TvshowShowReadNFOReq req) throws Exception {
        tvshowManager.readNFO(req.getId());
        return CommonResult.success(true);
    }

}