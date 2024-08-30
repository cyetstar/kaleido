package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TvshowShowConvert;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowSearchInfoResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.service.TvshowShowService;
import cc.onelooker.kaleido.third.tmm.Movie;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cc.onelooker.kaleido.utils.KaleidoConstants;
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
    private TvshowManager tvshowManager;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService getService() {
        return tvshowShowService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集")
    public CommonResult<PageResult<TvshowShowPageResp>> page(TvshowShowPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:added_at");
        return super.page(req, pageParam, TvshowShowConvert.INSTANCE::convertToDTO, TvshowShowConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集详情")
    public CommonResult<TvshowShowViewResp> view(String id) {
        TvshowShowDTO tvshowShowDTO = tvshowManager.findTvshowShow(id);
        TvshowShowViewResp resp = TvshowShowConvert.INSTANCE.convertToViewResp(tvshowShowDTO);
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
    public CommonResult<Boolean> downloadPoster(@RequestBody TvshowShowDownloadPosterReq req) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(req.getId());
        Path filePath = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        File file = filePath.resolve(KaleidoConstants.TVSHOW_POSTER).toFile();
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
        Tvshow tvshow = tmmApiService.findTvshow(req.getDoubanId(), req.getImdbId(), req.getTmdbId());
        tvshowManager.matchInfo(req.getId(), tvshow);
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(id);
        Path path = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        return CommonResult.success(path.toString());
    }

}