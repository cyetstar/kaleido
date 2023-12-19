package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowShowActorConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowActorDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowActorCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowActorPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowActorUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowActorCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowActorPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowActorViewResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowShowActorExp;
import cc.onelooker.kaleido.service.tvshow.TvshowShowActorService;
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
* 剧集演职员关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/

@Api(tags = "剧集演职员关联表")
@RestController
@RequestMapping("/tvshowShowActor")
public class TvshowShowActorController extends AbstractCrudController<TvshowShowActorDTO>{

    @Autowired
    private TvshowShowActorService tvshowShowActorService;

    @Override
    protected IBaseService getService() {
        return tvshowShowActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集演职员关联表")
    public CommonResult<PageResult<TvshowShowActorPageResp>> page(TvshowShowActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowShowActorConvert.INSTANCE::convertToDTO, TvshowShowActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集演职员关联表详情")
    public CommonResult<TvshowShowActorViewResp> view(Long id) {
        return super.view(id, TvshowShowActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集演职员关联表")
    public CommonResult<TvshowShowActorCreateResp> create(@RequestBody TvshowShowActorCreateReq req) {
        return super.create(req, TvshowShowActorConvert.INSTANCE::convertToDTO, TvshowShowActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集演职员关联表")
    public CommonResult<Boolean> update(@RequestBody TvshowShowActorUpdateReq req) {
        return super.update(req, TvshowShowActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集演职员关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowShowActorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出剧集演职员关联表")
    public void export(TvshowShowActorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "剧集演职员关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowShowActorExp.class,
                    TvshowShowActorConvert.INSTANCE::convertToDTO, TvshowShowActorConvert.INSTANCE::convertToExp, response);
    }

}