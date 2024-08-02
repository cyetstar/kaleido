package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.tvshow.req.*;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.service.tvshow.TvshowSeasonService;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
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

import java.nio.file.Path;
import java.nio.file.Paths;

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
    private TvshowManager tvshowManager;

    @Autowired
    private PlexApiService plexApiService;

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
    public CommonResult<TvshowSeasonViewResp> view(Long id) {
        return super.view(id, TvshowSeasonConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单季")
    public CommonResult<TvshowSeasonCreateResp> create(@RequestBody TvshowSeasonCreateReq req) {
        return super.create(req, TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单季")
    public CommonResult<Boolean> update(@RequestBody TvshowSeasonUpdateReq req) {
        return super.update(req, TvshowSeasonConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单季")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料")
    public CommonResult<Boolean> syncPlex(@RequestBody TvshowSeasonSyncPlexReq req) {
        tvshowManager.syncPlexShowSeason(req.getId());
        return CommonResult.success(true);
    }

    @PostMapping("readNFO")
    @ApiOperation(value = "读取NFO")
    public CommonResult<Boolean> readNFO(@RequestBody TvshowSeasonReadNFOReq req) throws Exception {
        tvshowManager.readSeasonNFO(req.getId());
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(Long id) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(id);
        Metadata metadata = plexApiService.findMetadata(tvshowSeasonDTO.getShowId());
        Path folderPath = Paths.get(KaleidoUtils.getTvshowPath(metadata.getLocation().getPath()));
        Path seasonPath = folderPath.resolve("Season " + StringUtils.leftPad(String.valueOf(tvshowSeasonDTO.getSeasonIndex()), 2, '0'));
        return CommonResult.success(seasonPath.toString());
    }

}