package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieBasicCountryService;
import cc.onelooker.kaleido.dto.movie.MovieBasicCountryDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicCountryConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieBasicCountryExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 电影国家地区关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影国家地区关联表")
@RestController
@RequestMapping("/movieBasicCountry")
public class MovieBasicCountryController extends AbstractCrudController<MovieBasicCountryDTO>{

    @Autowired
    private MovieBasicCountryService movieBasicCountryService;

    @Override
    protected IBaseService getService() {
        return movieBasicCountryService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影国家地区关联表")
    public CommonResult<PageResult<MovieBasicCountryPageResp>> page(MovieBasicCountryPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieBasicCountryConvert.INSTANCE::convertToDTO, MovieBasicCountryConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影国家地区关联表详情")
    public CommonResult<MovieBasicCountryViewResp> view(Long id) {
        return super.view(id, MovieBasicCountryConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影国家地区关联表")
    public CommonResult<MovieBasicCountryCreateResp> create(@RequestBody MovieBasicCountryCreateReq req) {
        return super.create(req, MovieBasicCountryConvert.INSTANCE::convertToDTO, MovieBasicCountryConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影国家地区关联表")
    public CommonResult<Boolean> update(@RequestBody MovieBasicCountryUpdateReq req) {
        return super.update(req, MovieBasicCountryConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影国家地区关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicCountryExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影国家地区关联表")
    public void export(MovieBasicCountryPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影国家地区关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicCountryExp.class,
                    MovieBasicCountryConvert.INSTANCE::convertToDTO, MovieBasicCountryConvert.INSTANCE::convertToExp, response);
    }

}