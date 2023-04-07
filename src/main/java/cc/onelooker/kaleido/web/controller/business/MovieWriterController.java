package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieWriterService;
import cc.onelooker.kaleido.dto.business.MovieWriterDTO;
import cc.onelooker.kaleido.convert.business.MovieWriterConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieWriterExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;


/**
* 前端控制器
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/

@Api(tags = "")
@RestController
@RequestMapping("/movieWriter")
public class MovieWriterController extends AbstractCrudController<MovieWriterDTO>{

    @Autowired
    private MovieWriterService movieWriterService;

    @Override
    protected IBaseService getService() {
        return movieWriterService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<MovieWriterPageResp>> page(MovieWriterPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieWriterConvert.INSTANCE::convertToDTO, MovieWriterConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<MovieWriterViewResp> view(Long id) {
        return super.view(id, MovieWriterConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<MovieWriterCreateResp> create(@RequestBody MovieWriterCreateReq req) {
        return super.create(req, MovieWriterConvert.INSTANCE::convertToDTO, MovieWriterConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody MovieWriterUpdateReq req) {
        return super.update(req, MovieWriterConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieWriterExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(MovieWriterPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieWriterExp.class,
                    MovieWriterConvert.INSTANCE::convertToDTO, MovieWriterConvert.INSTANCE::convertToExp, response);
    }

}