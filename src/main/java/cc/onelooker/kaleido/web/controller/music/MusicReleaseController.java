package cc.onelooker.kaleido.web.controller.music;

import cc.onelooker.kaleido.convert.music.MusicReleaseConvert;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.dto.music.req.MusicReleaseCreateReq;
import cc.onelooker.kaleido.dto.music.req.MusicReleasePageReq;
import cc.onelooker.kaleido.dto.music.req.MusicReleaseUpdateAudioTagReq;
import cc.onelooker.kaleido.dto.music.req.MusicReleaseUpdateReq;
import cc.onelooker.kaleido.dto.music.resp.MusicReleaseCreateResp;
import cc.onelooker.kaleido.dto.music.resp.MusicReleasePageResp;
import cc.onelooker.kaleido.dto.music.resp.MusicReleaseViewResp;
import cc.onelooker.kaleido.exp.music.MusicReleaseExp;
import cc.onelooker.kaleido.service.music.MusicManager;
import cc.onelooker.kaleido.service.music.MusicReleaseService;
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
 * 发行品前端控制器
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */

@Api(tags = "发行品")
@RestController
@RequestMapping("/musicRelease")
public class MusicReleaseController extends AbstractCrudController<MusicReleaseDTO> {

    @Autowired
    private MusicReleaseService musicReleaseService;

    @Autowired
    private MusicManager musicManager;

    @Override
    protected IBaseService getService() {
        return musicReleaseService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询发行品")
    public CommonResult<PageResult<MusicReleasePageResp>> page(MusicReleasePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicReleaseConvert.INSTANCE::convertToDTO, MusicReleaseConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看发行品详情")
    public CommonResult<MusicReleaseViewResp> view(Long id) {
        return super.view(id, MusicReleaseConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增发行品")
    public CommonResult<MusicReleaseCreateResp> create(@RequestBody MusicReleaseCreateReq req) {
        return super.create(req, MusicReleaseConvert.INSTANCE::convertToDTO, MusicReleaseConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑发行品")
    public CommonResult<Boolean> update(@RequestBody MusicReleaseUpdateReq req) {
        return super.update(req, MusicReleaseConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除发行品")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicReleaseExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出发行品")
    public void export(MusicReleasePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "发行品" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicReleaseExp.class, MusicReleaseConvert.INSTANCE::convertToDTO, MusicReleaseConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步音乐库")
    public CommonResult<Boolean> syncPlex() {
        musicManager.syncPlex();
        return CommonResult.success(true);
    }

    @PostMapping("updateAudioTag")
    public CommonResult<Boolean> updateAudioTag(@RequestBody MusicReleaseUpdateAudioTagReq req) {
        musicManager.updateReleaseAudioTag(req.getId());
        return CommonResult.success(true);
    }

}