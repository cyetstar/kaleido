package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieTagService;
import cc.onelooker.kaleido.dto.movie.MovieTagDTO;
import cc.onelooker.kaleido.convert.movie.MovieTagConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieTagExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 电影标签前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影标签")
@RestController
@RequestMapping("/movieTag")
public class MovieTagController extends AbstractCrudController<MovieTagDTO>{

    @Autowired
    private MovieTagService movieTagService;

    @Override
    protected IBaseService getService() {
        return movieTagService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影标签")
    public CommonResult<PageResult<MovieTagPageResp>> page(MovieTagPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieTagConvert.INSTANCE::convertToDTO, MovieTagConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影标签详情")
    public CommonResult<MovieTagViewResp> view(Long id) {
        return super.view(id, MovieTagConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影标签")
    public CommonResult<MovieTagCreateResp> create(@RequestBody MovieTagCreateReq req) {
        return super.create(req, MovieTagConvert.INSTANCE::convertToDTO, MovieTagConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影标签")
    public CommonResult<Boolean> update(@RequestBody MovieTagUpdateReq req) {
        return super.update(req, MovieTagConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影标签")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieTagExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影标签")
    public void export(MovieTagPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影标签" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieTagExp.class,
                    MovieTagConvert.INSTANCE::convertToDTO, MovieTagConvert.INSTANCE::convertToExp, response);
    }

}