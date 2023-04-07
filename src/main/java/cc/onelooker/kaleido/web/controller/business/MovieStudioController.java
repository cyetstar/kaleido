package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieStudioService;
import cc.onelooker.kaleido.dto.business.MovieStudioDTO;
import cc.onelooker.kaleido.convert.business.MovieStudioConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieStudioExp;

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
@RequestMapping("/movieStudio")
public class MovieStudioController extends AbstractCrudController<MovieStudioDTO>{

    @Autowired
    private MovieStudioService movieStudioService;

    @Override
    protected IBaseService getService() {
        return movieStudioService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<MovieStudioPageResp>> page(MovieStudioPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieStudioConvert.INSTANCE::convertToDTO, MovieStudioConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<MovieStudioViewResp> view(Long id) {
        return super.view(id, MovieStudioConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<MovieStudioCreateResp> create(@RequestBody MovieStudioCreateReq req) {
        return super.create(req, MovieStudioConvert.INSTANCE::convertToDTO, MovieStudioConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody MovieStudioUpdateReq req) {
        return super.update(req, MovieStudioConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieStudioExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(MovieStudioPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieStudioExp.class,
                    MovieStudioConvert.INSTANCE::convertToDTO, MovieStudioConvert.INSTANCE::convertToExp, response);
    }

}