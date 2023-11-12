package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieUniqueidService;
import cc.onelooker.kaleido.dto.movie.MovieUniqueidDTO;
import cc.onelooker.kaleido.convert.movie.MovieUniqueidConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.dto.movie.exp.MovieUniqueidExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;

/**
* 电影唯一标识前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "电影唯一标识")
@RestController
@RequestMapping("/movieUniqueid")
public class MovieUniqueidController extends AbstractCrudController<MovieUniqueidDTO>{

    @Autowired
    private MovieUniqueidService movieUniqueidService;

    @Override
    protected IBaseService getService() {
        return movieUniqueidService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影唯一标识")
    public CommonResult<PageResult<MovieUniqueidPageResp>> page(MovieUniqueidPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieUniqueidConvert.INSTANCE::convertToDTO, MovieUniqueidConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影唯一标识详情")
    public CommonResult<MovieUniqueidViewResp> view(Long id) {
        return super.view(id, MovieUniqueidConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影唯一标识")
    public CommonResult<MovieUniqueidCreateResp> create(@RequestBody MovieUniqueidCreateReq req) {
        return super.create(req, MovieUniqueidConvert.INSTANCE::convertToDTO, MovieUniqueidConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影唯一标识")
    public CommonResult<Boolean> update(@RequestBody MovieUniqueidUpdateReq req) {
        return super.update(req, MovieUniqueidConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影唯一标识")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieUniqueidExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影唯一标识")
    public void export(MovieUniqueidPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影唯一标识" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieUniqueidExp.class,
                    MovieUniqueidConvert.INSTANCE::convertToDTO, MovieUniqueidConvert.INSTANCE::convertToExp, response);
    }

}