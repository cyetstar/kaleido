package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.ActorDTO;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.service.ActorService;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.service.TvshowShowService;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cc.onelooker.kaleido.utils.KaleidoConstants;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import cn.hutool.http.HttpUtil;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;

/**
 * 单季前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Api(tags = "单季")
@RestController
@RequestMapping("/tvshowSeason")
public class TvshowSeasonController extends AbstractCrudController<TvshowSeasonDTO> {

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService getService() {
        return tvshowSeasonService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询单季")
    public CommonResult<PageResult<TvshowSeasonPageResp>> page(TvshowSeasonPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看单季详情")
    public CommonResult<TvshowSeasonViewResp> view(String id) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowManager.findTvshowSeason(id);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        TvshowSeasonViewResp resp = TvshowSeasonConvert.INSTANCE.convertToViewResp(tvshowSeasonDTO);
        resp.setShowTitle(tvshowShowDTO.getTitle());
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单季")
    public CommonResult<TvshowSeasonCreateResp> create(@RequestBody TvshowSeasonCreateReq req) {
        return super.create(req, TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单季")
    public CommonResult<Boolean> update(@RequestBody TvshowSeasonUpdateReq req) {
        TvshowSeasonDTO tvshowSeasonDTO = TvshowSeasonConvert.INSTANCE.convertToDTO(req);
        if (req.getDirectorList() != null) {
            tvshowSeasonDTO.setDirectorList(req.getDirectorList().stream().map(s -> actorService.findById(s)).collect(Collectors.toList()));
        }
        if (req.getWriterList() != null) {
            tvshowSeasonDTO.setWriterList(req.getWriterList().stream().map(s -> actorService.findById(s)).collect(Collectors.toList()));
        }
        if (req.getActorList() != null) {
            tvshowSeasonDTO.setActorList(req.getActorList().stream().map(s -> {
                ActorDTO actorDTO = actorService.findById(s.getId());
                actorDTO.setPlayRole(s.getPlayRole());
                return actorDTO;
            }).collect(Collectors.toList()));
        }
        tvshowManager.saveSeason(tvshowSeasonDTO);
        return CommonResult.success(true);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单季")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(id);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        Path folderPath = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        Path seasonPath = folderPath.resolve("Season " + StringUtils.leftPad(String.valueOf(tvshowSeasonDTO.getSeasonIndex()), 2, '0'));
        return CommonResult.success(seasonPath.toString());
    }

    @PostMapping("matchInfo")
    @ApiOperation(value = "匹配信息")
    public CommonResult<Boolean> matchInfo(@RequestBody TvshowSeasonMatchInfoReq req) {
        Tvshow tvshow = tmmApiService.findShow(req.getDoubanId(), req.getImdbId(), req.getTmdbId());
        tvshowManager.matchInfo(req.getId(), tvshow);
        return CommonResult.success(true);
    }

    @PostMapping("downloadPoster")
    public CommonResult<Boolean> downloadPoster(@RequestBody TvshowSeasonDownloadPosterReq req) throws IOException {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(req.getId());
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        Path path = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        String fileName = StringUtils.joinWith("-", "season", tvshowSeasonDTO.getSeasonIndex(), KaleidoConstants.TVSHOW_POSTER);
        HttpUtil.downloadFile(req.getUrl(), path.resolve(fileName).toFile());
        if (tvshowSeasonDTO.getSeasonIndex() == 1) {
            Files.copy(path.resolve(fileName), path.resolve(KaleidoConstants.TVSHOW_POSTER), StandardCopyOption.REPLACE_EXISTING);
        }
        return CommonResult.success(true);
    }

}