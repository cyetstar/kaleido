package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowSeasonUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowSeasonExp;
import cc.onelooker.kaleido.service.tvshow.TvshowSeasonService;
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
* 单季前端控制器
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/

@Api(tags = "单季")
@RestController
@RequestMapping("/tvshowSeason")
public class TvshowSeasonController extends AbstractCrudController<TvshowSeasonDTO>{

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

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

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowSeasonExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出单季")
    public void export(TvshowSeasonPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "单季" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowSeasonExp.class,
                    TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToExp, response);
    }

}