package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieDirectorService;
import cc.onelooker.kaleido.dto.business.MovieDirectorDTO;
import cc.onelooker.kaleido.convert.business.MovieDirectorConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieDirectorExp;

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
@RequestMapping("/movieDirector")
public class MovieDirectorController extends AbstractCrudController<MovieDirectorDTO>{

    @Autowired
    private MovieDirectorService movieDirectorService;

    @Override
    protected IBaseService getService() {
        return movieDirectorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<MovieDirectorPageResp>> page(MovieDirectorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieDirectorConvert.INSTANCE::convertToDTO, MovieDirectorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<MovieDirectorViewResp> view(Long id) {
        return super.view(id, MovieDirectorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<MovieDirectorCreateResp> create(@RequestBody MovieDirectorCreateReq req) {
        return super.create(req, MovieDirectorConvert.INSTANCE::convertToDTO, MovieDirectorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody MovieDirectorUpdateReq req) {
        return super.update(req, MovieDirectorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieDirectorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(MovieDirectorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieDirectorExp.class,
                    MovieDirectorConvert.INSTANCE::convertToDTO, MovieDirectorConvert.INSTANCE::convertToExp, response);
    }

}