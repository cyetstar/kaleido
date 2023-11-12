package cc.onelooker.kaleido.web.controller.music;

import cc.onelooker.kaleido.convert.music.MusicTrackConvert;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.dto.music.req.MusicTrackCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicTrackPageReq;
import cc.onelooker.kaleido.dto.music.req.MusicTrackUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackCreateResp;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackListByReleaseIdResp;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackPageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicTrackViewResp;
import cc.onelooker.kaleido.exp.music.MusicTrackExp;
import cc.onelooker.kaleido.service.music.MusicTrackService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 曲目前端控制器
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */

@Api(tags = "曲目")
@RestController
@RequestMapping("/musicTrack")
public class MusicTrackController extends AbstractCrudController<MusicTrackDTO> {

    @Autowired
    private MusicTrackService musicTrackService;

    @Override
    protected IBaseService getService() {
        return musicTrackService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询曲目")
    public CommonResult<PageResult<MusicTrackPageResp>> page(MusicTrackPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicTrackConvert.INSTANCE::convertToDTO, MusicTrackConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看曲目详情")
    public CommonResult<MusicTrackViewResp> view(Long id) {
        return super.view(id, MusicTrackConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增曲目")
    public CommonResult<MusicTrackCreateResp> create(@RequestBody MusicTrackCreateReq req) {
        return super.create(req, MusicTrackConvert.INSTANCE::convertToDTO, MusicTrackConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑曲目")
    public CommonResult<Boolean> update(@RequestBody MusicTrackUpdateReq req) {
        return super.update(req, MusicTrackConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除曲目")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicTrackExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出曲目")
    public void export(MusicTrackPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "曲目" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicTrackExp.class,
                MusicTrackConvert.INSTANCE::convertToDTO, MusicTrackConvert.INSTANCE::convertToExp, response);
    }

    @GetMapping("listByReleaseId")
    public CommonResult<List<MusicTrackListByReleaseIdResp>> listByReleaseId(Long releaseId) {
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByReleaseId(releaseId);
        List<MusicTrackListByReleaseIdResp> respList = MusicTrackConvert.INSTANCE.convertToListByReleaseIdResp(musicTrackDTOList);
        return CommonResult.success(respList);
    }

}