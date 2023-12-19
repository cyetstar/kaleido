package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieThreadConvert;
import cc.onelooker.kaleido.dto.movie.MovieThreadDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieThreadUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieThreadViewResp;
import cc.onelooker.kaleido.exp.movie.MovieThreadExp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.movie.MovieThreadService;
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
 * 电影发布记录前端控制器
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */

@Api(tags = "电影发布记录")
@RestController
@RequestMapping("/movieThread")
public class MovieThreadController extends AbstractCrudController<MovieThreadDTO> {

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    protected IBaseService getService() {
        return movieThreadService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影发布记录")
    public CommonResult<PageResult<MovieThreadPageResp>> page(MovieThreadPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieThreadConvert.INSTANCE::convertToDTO, MovieThreadConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影发布记录详情")
    public CommonResult<MovieThreadViewResp> view(Long id) {
        return super.view(id, MovieThreadConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影发布记录")
    public CommonResult<MovieThreadCreateResp> create(@RequestBody MovieThreadCreateReq req) {
        return super.create(req, MovieThreadConvert.INSTANCE::convertToDTO, MovieThreadConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影发布记录")
    public CommonResult<Boolean> update(@RequestBody MovieThreadUpdateReq req) {
        return super.update(req, MovieThreadConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影发布记录")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieThreadExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影发布记录")
    public void export(MovieThreadPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影发布记录" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieThreadExp.class, MovieThreadConvert.INSTANCE::convertToDTO, MovieThreadConvert.INSTANCE::convertToExp, response);
    }

}