package cc.onelooker.kaleido.web.controller.music;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.music.MusicArtistTrackService;
import cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistTrackConvert;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicArtistTrackExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 艺术家曲目关联表前端控制器
*
* @author cyetstar
* @date 2023-11-20 22:35:26
*/

@Api(tags = "艺术家曲目关联表")
@RestController
@RequestMapping("/musicArtistTrack")
public class MusicArtistTrackController extends AbstractCrudController<MusicArtistTrackDTO>{

    @Autowired
    private MusicArtistTrackService musicArtistTrackService;

    @Override
    protected IBaseService getService() {
        return musicArtistTrackService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家曲目关联表")
    public CommonResult<PageResult<MusicArtistTrackPageResp>> page(MusicArtistTrackPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicArtistTrackConvert.INSTANCE::convertToDTO, MusicArtistTrackConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家曲目关联表详情")
    public CommonResult<MusicArtistTrackViewResp> view(Long id) {
        return super.view(id, MusicArtistTrackConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家曲目关联表")
    public CommonResult<MusicArtistTrackCreateResp> create(@RequestBody MusicArtistTrackCreateReq req) {
        return super.create(req, MusicArtistTrackConvert.INSTANCE::convertToDTO, MusicArtistTrackConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家曲目关联表")
    public CommonResult<Boolean> update(@RequestBody MusicArtistTrackUpdateReq req) {
        return super.update(req, MusicArtistTrackConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家曲目关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicArtistTrackExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出艺术家曲目关联表")
    public void export(MusicArtistTrackPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "艺术家曲目关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicArtistTrackExp.class,
                    MusicArtistTrackConvert.INSTANCE::convertToDTO, MusicArtistTrackConvert.INSTANCE::convertToExp, response);
    }

}