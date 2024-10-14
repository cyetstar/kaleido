package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ActorConvert;
import cc.onelooker.kaleido.convert.TvshowShowConvert;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.dto.req.TvshowShowCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowShowPageReq;
import cc.onelooker.kaleido.dto.req.TvshowShowSearchInfoReq;
import cc.onelooker.kaleido.dto.req.TvshowShowUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowShowCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowSearchInfoResp;
import cc.onelooker.kaleido.dto.resp.TvshowShowViewResp;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.service.TvshowShowService;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.third.tmm.Tvshow;
import cc.onelooker.kaleido.utils.KaleidoUtil;
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

import java.nio.file.Path;
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
    private TvshowSeasonService tvshowSeasonService;

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
        TvshowSeasonDTO tvshowSeason = tvshowManager.findTvshowSeason(tvshowShowDTO.getFirstSeason().getId());
        TvshowShowViewResp resp = TvshowShowConvert.INSTANCE.convertToViewResp(tvshowShowDTO);
        if (tvshowSeason.getDirectorList() != null) {
            resp.setDirectorList(tvshowSeason.getDirectorList().stream().map(ActorConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        }
        if (tvshowSeason.getWriterList() != null) {
            resp.setWriterList(tvshowSeason.getWriterList().stream().map(ActorConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        }
        if (tvshowSeason.getActorList() != null) {
            resp.setActorList(tvshowSeason.getActorList().stream().map(ActorConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        }
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
        TvshowShowDTO tvshowShowDTO = TvshowShowConvert.INSTANCE.convertToDTO(req);
        tvshowManager.saveShow(tvshowShowDTO);
        return CommonResult.success(true);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("searchInfo")
    @ApiOperation(value = "查询信息")
    public CommonResult<List<TvshowShowSearchInfoResp>> searchInfo(@RequestBody TvshowShowSearchInfoReq req) {
        List<Tvshow> tvshowList = tmmApiService.searchShow(req.getKeyword(), req.getSource());
        List<TvshowShowSearchInfoResp> respList = Lists.newArrayList();
        if (tvshowList != null) {
            respList = tvshowList.stream().map(s -> {
                TvshowShowSearchInfoResp resp = TvshowShowConvert.INSTANCE.convertToSearchInfoResp(s);
                TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findByDoubanId(s.getDoubanId());
                if (tvshowSeasonDTO != null) {
                    resp.setExistId(tvshowSeasonDTO.getId());
                }
                return resp;
            }).collect(Collectors.toList());
        }

        return CommonResult.success(respList);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(id);
        Path path = KaleidoUtil.getTvshowPath(tvshowShowDTO.getPath());
        return CommonResult.success(path.toString());
    }

}