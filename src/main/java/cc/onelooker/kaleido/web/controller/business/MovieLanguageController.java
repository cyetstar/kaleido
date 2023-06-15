package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieLanguageService;
import cc.onelooker.kaleido.dto.business.MovieLanguageDTO;
import cc.onelooker.kaleido.convert.business.MovieLanguageConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieLanguageExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 语言前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "语言")
@RestController
@RequestMapping("/movieLanguage")
public class MovieLanguageController extends AbstractCrudController<MovieLanguageDTO>{

    @Autowired
    private MovieLanguageService movieLanguageService;

    @Override
    protected IBaseService getService() {
        return movieLanguageService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询语言")
    public CommonResult<PageResult<MovieLanguagePageResp>> page(MovieLanguagePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieLanguageConvert.INSTANCE::convertToDTO, MovieLanguageConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看语言详情")
    public CommonResult<MovieLanguageViewResp> view(Long id) {
        return super.view(id, MovieLanguageConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增语言")
    public CommonResult<MovieLanguageCreateResp> create(@RequestBody MovieLanguageCreateReq req) {
        return super.create(req, MovieLanguageConvert.INSTANCE::convertToDTO, MovieLanguageConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑语言")
    public CommonResult<Boolean> update(@RequestBody MovieLanguageUpdateReq req) {
        return super.update(req, MovieLanguageConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除语言")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieLanguageExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出语言")
    public void export(MovieLanguagePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "语言" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieLanguageExp.class,
                    MovieLanguageConvert.INSTANCE::convertToDTO, MovieLanguageConvert.INSTANCE::convertToExp, response);
    }

}