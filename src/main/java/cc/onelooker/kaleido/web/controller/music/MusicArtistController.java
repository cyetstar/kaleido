package cc.onelooker.kaleido.web.controller.music;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistConvert;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicArtistExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
* 艺术家前端控制器
*
* @author cyetstar
* @date 2023-09-29 22:32:53
*/

@Api(tags = "艺术家")
@RestController
@RequestMapping("/musicArtist")
public class MusicArtistController extends AbstractCrudController<MusicArtistDTO>{

    @Autowired
    private MusicArtistService musicArtistService;

    @Override
    protected IBaseService getService() {
        return musicArtistService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家")
    public CommonResult<PageResult<MusicArtistPageResp>> page(MusicArtistPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicArtistConvert.INSTANCE::convertToDTO, MusicArtistConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家详情")
    public CommonResult<MusicArtistViewResp> view(Long id) {
        return super.view(id, MusicArtistConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家")
    public CommonResult<MusicArtistCreateResp> create(@RequestBody MusicArtistCreateReq req) {
        return super.create(req, MusicArtistConvert.INSTANCE::convertToDTO, MusicArtistConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家")
    public CommonResult<Boolean> update(@RequestBody MusicArtistUpdateReq req) {
        return super.update(req, MusicArtistConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicArtistExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出艺术家")
    public void export(MusicArtistPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "艺术家" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicArtistExp.class,
                    MusicArtistConvert.INSTANCE::convertToDTO, MusicArtistConvert.INSTANCE::convertToExp, response);
    }

}