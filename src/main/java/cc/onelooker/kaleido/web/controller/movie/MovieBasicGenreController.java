package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicGenreConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicGenreDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenreCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenrePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicGenreUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenreCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenrePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicGenreViewResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicGenreExp;
import cc.onelooker.kaleido.service.movie.MovieBasicGenreService;
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
* 电影类型关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影类型关联表")
@RestController
@RequestMapping("/movieBasicGenre")
public class MovieBasicGenreController extends AbstractCrudController<MovieBasicGenreDTO>{

    @Autowired
    private MovieBasicGenreService movieBasicGenreService;

    @Override
    protected IBaseService getService() {
        return movieBasicGenreService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影类型关联表")
    public CommonResult<PageResult<MovieBasicGenrePageResp>> page(MovieBasicGenrePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieBasicGenreConvert.INSTANCE::convertToDTO, MovieBasicGenreConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影类型关联表详情")
    public CommonResult<MovieBasicGenreViewResp> view(Long id) {
        return super.view(id, MovieBasicGenreConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影类型关联表")
    public CommonResult<MovieBasicGenreCreateResp> create(@RequestBody MovieBasicGenreCreateReq req) {
        return super.create(req, MovieBasicGenreConvert.INSTANCE::convertToDTO, MovieBasicGenreConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影类型关联表")
    public CommonResult<Boolean> update(@RequestBody MovieBasicGenreUpdateReq req) {
        return super.update(req, MovieBasicGenreConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影类型关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicGenreExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影类型关联表")
    public void export(MovieBasicGenrePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影类型关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicGenreExp.class,
                    MovieBasicGenreConvert.INSTANCE::convertToDTO, MovieBasicGenreConvert.INSTANCE::convertToExp, response);
    }

}