package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieGenreService;
import cc.onelooker.kaleido.dto.business.MovieGenreDTO;
import cc.onelooker.kaleido.convert.business.MovieGenreConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieGenreExp;

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
@RequestMapping("/movieGenre")
public class MovieGenreController extends AbstractCrudController<MovieGenreDTO>{

    @Autowired
    private MovieGenreService movieGenreService;

    @Override
    protected IBaseService getService() {
        return movieGenreService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<MovieGenrePageResp>> page(MovieGenrePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<MovieGenreViewResp> view(Long id) {
        return super.view(id, MovieGenreConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<MovieGenreCreateResp> create(@RequestBody MovieGenreCreateReq req) {
        return super.create(req, MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody MovieGenreUpdateReq req) {
        return super.update(req, MovieGenreConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieGenreExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(MovieGenrePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieGenreExp.class,
                    MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToExp, response);
    }

}