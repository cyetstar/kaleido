package cc.onelooker.kaleido.web.controller.music;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.music.MusicArtistAlbumService;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistAlbumConvert;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicArtistAlbumExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 艺术家专辑关联表前端控制器
*
* @author cyetstar
* @date 2023-11-25 22:16:58
*/

@Api(tags = "艺术家专辑关联表")
@RestController
@RequestMapping("/musicArtistAlbum")
public class MusicArtistAlbumController extends AbstractCrudController<MusicArtistAlbumDTO>{

    @Autowired
    private MusicArtistAlbumService musicArtistAlbumService;

    @Override
    protected IBaseService getService() {
        return musicArtistAlbumService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家专辑关联表")
    public CommonResult<PageResult<MusicArtistAlbumPageResp>> page(MusicArtistAlbumPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicArtistAlbumConvert.INSTANCE::convertToDTO, MusicArtistAlbumConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家专辑关联表详情")
    public CommonResult<MusicArtistAlbumViewResp> view(Long id) {
        return super.view(id, MusicArtistAlbumConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家专辑关联表")
    public CommonResult<MusicArtistAlbumCreateResp> create(@RequestBody MusicArtistAlbumCreateReq req) {
        return super.create(req, MusicArtistAlbumConvert.INSTANCE::convertToDTO, MusicArtistAlbumConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家专辑关联表")
    public CommonResult<Boolean> update(@RequestBody MusicArtistAlbumUpdateReq req) {
        return super.update(req, MusicArtistAlbumConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家专辑关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicArtistAlbumExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出艺术家专辑关联表")
    public void export(MusicArtistAlbumPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "艺术家专辑关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicArtistAlbumExp.class,
                    MusicArtistAlbumConvert.INSTANCE::convertToDTO, MusicArtistAlbumConvert.INSTANCE::convertToExp, response);
    }

}