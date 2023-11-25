package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieBasicLanguageService;
import cc.onelooker.kaleido.dto.movie.MovieBasicLanguageDTO;
import cc.onelooker.kaleido.convert.movie.MovieBasicLanguageConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieBasicLanguageExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 电影语言关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影语言关联表")
@RestController
@RequestMapping("/movieBasicLanguage")
public class MovieBasicLanguageController extends AbstractCrudController<MovieBasicLanguageDTO>{

    @Autowired
    private MovieBasicLanguageService movieBasicLanguageService;

    @Override
    protected IBaseService getService() {
        return movieBasicLanguageService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影语言关联表")
    public CommonResult<PageResult<MovieBasicLanguagePageResp>> page(MovieBasicLanguagePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieBasicLanguageConvert.INSTANCE::convertToDTO, MovieBasicLanguageConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影语言关联表详情")
    public CommonResult<MovieBasicLanguageViewResp> view(Long id) {
        return super.view(id, MovieBasicLanguageConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影语言关联表")
    public CommonResult<MovieBasicLanguageCreateResp> create(@RequestBody MovieBasicLanguageCreateReq req) {
        return super.create(req, MovieBasicLanguageConvert.INSTANCE::convertToDTO, MovieBasicLanguageConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影语言关联表")
    public CommonResult<Boolean> update(@RequestBody MovieBasicLanguageUpdateReq req) {
        return super.update(req, MovieBasicLanguageConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影语言关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicLanguageExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影语言关联表")
    public void export(MovieBasicLanguagePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影语言关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicLanguageExp.class,
                    MovieBasicLanguageConvert.INSTANCE::convertToDTO, MovieBasicLanguageConvert.INSTANCE::convertToExp, response);
    }

}