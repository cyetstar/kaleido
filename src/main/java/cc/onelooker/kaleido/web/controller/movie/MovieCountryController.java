package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieCountryService;
import cc.onelooker.kaleido.dto.movie.MovieCountryDTO;
import cc.onelooker.kaleido.convert.movie.MovieCountryConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieCountryExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 国家地区前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "国家地区")
@RestController
@RequestMapping("/movieCountry")
public class MovieCountryController extends AbstractCrudController<MovieCountryDTO>{

    @Autowired
    private MovieCountryService movieCountryService;

    @Override
    protected IBaseService getService() {
        return movieCountryService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询国家地区")
    public CommonResult<PageResult<MovieCountryPageResp>> page(MovieCountryPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieCountryConvert.INSTANCE::convertToDTO, MovieCountryConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看国家地区详情")
    public CommonResult<MovieCountryViewResp> view(Long id) {
        return super.view(id, MovieCountryConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增国家地区")
    public CommonResult<MovieCountryCreateResp> create(@RequestBody MovieCountryCreateReq req) {
        return super.create(req, MovieCountryConvert.INSTANCE::convertToDTO, MovieCountryConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑国家地区")
    public CommonResult<Boolean> update(@RequestBody MovieCountryUpdateReq req) {
        return super.update(req, MovieCountryConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除国家地区")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieCountryExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出国家地区")
    public void export(MovieCountryPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "国家地区" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieCountryExp.class,
                    MovieCountryConvert.INSTANCE::convertToDTO, MovieCountryConvert.INSTANCE::convertToExp, response);
    }

}