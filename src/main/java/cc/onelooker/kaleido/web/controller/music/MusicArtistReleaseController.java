package cc.onelooker.kaleido.web.controller.music;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.music.MusicArtistReleaseService;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistReleaseConvert;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicArtistReleaseExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;


/**
* 艺术家发行品关联表前端控制器
*
* @author cyetstar
* @date 2023-09-29 22:32:53
*/

@Api(tags = "艺术家发行品关联表")
@RestController
@RequestMapping("/musicArtistRelease")
public class MusicArtistReleaseController extends AbstractCrudController<MusicArtistReleaseDTO>{

    @Autowired
    private MusicArtistReleaseService musicArtistReleaseService;

    @Override
    protected IBaseService getService() {
        return musicArtistReleaseService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家发行品关联表")
    public CommonResult<PageResult<MusicArtistReleasePageResp>> page(MusicArtistReleasePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicArtistReleaseConvert.INSTANCE::convertToDTO, MusicArtistReleaseConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家发行品关联表详情")
    public CommonResult<MusicArtistReleaseViewResp> view(Long id) {
        return super.view(id, MusicArtistReleaseConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家发行品关联表")
    public CommonResult<MusicArtistReleaseCreateResp> create(@RequestBody MusicArtistReleaseCreateReq req) {
        return super.create(req, MusicArtistReleaseConvert.INSTANCE::convertToDTO, MusicArtistReleaseConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家发行品关联表")
    public CommonResult<Boolean> update(@RequestBody MusicArtistReleaseUpdateReq req) {
        return super.update(req, MusicArtistReleaseConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家发行品关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicArtistReleaseExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出艺术家发行品关联表")
    public void export(MusicArtistReleasePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "艺术家发行品关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicArtistReleaseExp.class,
                    MusicArtistReleaseConvert.INSTANCE::convertToDTO, MusicArtistReleaseConvert.INSTANCE::convertToExp, response);
    }

}