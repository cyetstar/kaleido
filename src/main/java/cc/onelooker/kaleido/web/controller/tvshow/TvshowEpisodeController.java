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
import cc.onelooker.kaleido.exp.tvshow.TvshowEpisodeExp;
import cc.onelooker.kaleido.plex.PlexApiService;
import cc.onelooker.kaleido.plex.resp.GetEpisodes;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeService;
import cc.onelooker.kaleido.service.tvshow.TvshowManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

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

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowEpisodeExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出单集")
    public void export(TvshowEpisodePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "单集" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowEpisodeExp.class, TvshowEpisodeConvert.INSTANCE::convertToDTO, TvshowEpisodeConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        String libraryId = ConfigUtils.getSysConfig("plexTvshowLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需同步资料库信息");
        }
        //获取最后修改时间
        Long maxUpdatedAt = tvshowEpisodeService.findMaxUpdatedAt();
        List<GetEpisodes.Metadata> metadataList = maxUpdatedAt == null ? plexApiService.listEpsiode(libraryId) : plexApiService.listEpsiodeByUpdatedAt(libraryId, maxUpdatedAt);
        metadataList.sort(Comparator.comparing(GetEpisodes.Metadata::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        for (GetEpisodes.Metadata metadata : metadataList) {
            try {
                tvshowManager.syncPlexEpisodeById(metadata.getRatingKey());
            } catch (Exception e) {
                log.error("同步资料库失败，错误信息：", e);
            }
        }
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlexById(@RequestBody TvshowEpisodeSyncPlexByIdReq req) {
        tvshowManager.syncPlexEpisodeById(req.getId());
        return CommonResult.success(true);
    }

}