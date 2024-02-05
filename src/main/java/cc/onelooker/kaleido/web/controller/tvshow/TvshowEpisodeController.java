package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowEpisodeConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodePageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeSyncPlexByIdReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodePageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeViewResp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.third.plex.PlexApiService;
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

/**
 * 单集前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Slf4j
@Api(tags = "单集")
@RestController
@RequestMapping("/tvshowEpisode")
public class TvshowEpisodeController extends AbstractCrudController<TvshowEpisodeDTO> {

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Autowired
    private PlexApiService plexApiService;

    @Override
    protected IBaseService getService() {
        return tvshowEpisodeService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询单集")
    public CommonResult<PageResult<TvshowEpisodePageResp>> page(TvshowEpisodePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowEpisodeConvert.INSTANCE::convertToDTO, TvshowEpisodeConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看单集详情")
    public CommonResult<TvshowEpisodeViewResp> view(Long id) {
        return super.view(id, TvshowEpisodeConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单集")
    public CommonResult<TvshowEpisodeCreateResp> create(@RequestBody TvshowEpisodeCreateReq req) {
        return super.create(req, TvshowEpisodeConvert.INSTANCE::convertToDTO, TvshowEpisodeConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单集")
    public CommonResult<Boolean> update(@RequestBody TvshowEpisodeUpdateReq req) {
        return super.update(req, TvshowEpisodeConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单集")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        asyncTaskManager.syncPlexTvshow();
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlexById(@RequestBody TvshowEpisodeSyncPlexByIdReq req) {
        tvshowManager.syncPlexEpisode(req.getId());
        return CommonResult.success(true);
    }

}