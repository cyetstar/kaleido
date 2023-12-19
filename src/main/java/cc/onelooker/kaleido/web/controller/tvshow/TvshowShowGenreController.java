package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowShowGenreConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenreCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenrePageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowShowGenreUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenreCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenrePageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowShowGenreViewResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowShowGenreExp;
import cc.onelooker.kaleido.service.tvshow.TvshowShowGenreService;
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
* 剧集类型关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/

@Api(tags = "剧集类型关联表")
@RestController
@RequestMapping("/tvshowShowGenre")
public class TvshowShowGenreController extends AbstractCrudController<TvshowShowGenreDTO>{

    @Autowired
    private TvshowShowGenreService tvshowShowGenreService;

    @Override
    protected IBaseService getService() {
        return tvshowShowGenreService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集类型关联表")
    public CommonResult<PageResult<TvshowShowGenrePageResp>> page(TvshowShowGenrePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowShowGenreConvert.INSTANCE::convertToDTO, TvshowShowGenreConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集类型关联表详情")
    public CommonResult<TvshowShowGenreViewResp> view(Long id) {
        return super.view(id, TvshowShowGenreConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集类型关联表")
    public CommonResult<TvshowShowGenreCreateResp> create(@RequestBody TvshowShowGenreCreateReq req) {
        return super.create(req, TvshowShowGenreConvert.INSTANCE::convertToDTO, TvshowShowGenreConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集类型关联表")
    public CommonResult<Boolean> update(@RequestBody TvshowShowGenreUpdateReq req) {
        return super.update(req, TvshowShowGenreConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集类型关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowShowGenreExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出剧集类型关联表")
    public void export(TvshowShowGenrePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "剧集类型关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowShowGenreExp.class,
                    TvshowShowGenreConvert.INSTANCE::convertToDTO, TvshowShowGenreConvert.INSTANCE::convertToExp, response);
    }

}