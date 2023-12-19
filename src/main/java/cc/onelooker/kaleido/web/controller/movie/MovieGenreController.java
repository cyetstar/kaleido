package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieGenreConvert;
import cc.onelooker.kaleido.dto.movie.MovieGenreDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieGenreCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieGenrePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieGenreUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenreCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenrePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieGenreViewResp;
import cc.onelooker.kaleido.exp.movie.MovieGenreExp;
import cc.onelooker.kaleido.service.movie.MovieGenreService;
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
* 电影类型前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影类型")
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
    @ApiOperation(value = "查询电影类型")
    public CommonResult<PageResult<MovieGenrePageResp>> page(MovieGenrePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影类型详情")
    public CommonResult<MovieGenreViewResp> view(Long id) {
        return super.view(id, MovieGenreConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影类型")
    public CommonResult<MovieGenreCreateResp> create(@RequestBody MovieGenreCreateReq req) {
        return super.create(req, MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影类型")
    public CommonResult<Boolean> update(@RequestBody MovieGenreUpdateReq req) {
        return super.update(req, MovieGenreConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影类型")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieGenreExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影类型")
    public void export(MovieGenrePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影类型" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieGenreExp.class,
                    MovieGenreConvert.INSTANCE::convertToDTO, MovieGenreConvert.INSTANCE::convertToExp, response);
    }

}