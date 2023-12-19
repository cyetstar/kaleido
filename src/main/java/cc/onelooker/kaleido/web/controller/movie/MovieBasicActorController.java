package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicActorConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicActorDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicActorUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicActorViewResp;
import cc.onelooker.kaleido.exp.movie.MovieBasicActorExp;
import cc.onelooker.kaleido.service.movie.MovieBasicActorService;
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
* 电影演职员关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影演职员关联表")
@RestController
@RequestMapping("/movieBasicActor")
public class MovieBasicActorController extends AbstractCrudController<MovieBasicActorDTO>{

    @Autowired
    private MovieBasicActorService movieBasicActorService;

    @Override
    protected IBaseService getService() {
        return movieBasicActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影演职员关联表")
    public CommonResult<PageResult<MovieBasicActorPageResp>> page(MovieBasicActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieBasicActorConvert.INSTANCE::convertToDTO, MovieBasicActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影演职员关联表详情")
    public CommonResult<MovieBasicActorViewResp> view(Long id) {
        return super.view(id, MovieBasicActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影演职员关联表")
    public CommonResult<MovieBasicActorCreateResp> create(@RequestBody MovieBasicActorCreateReq req) {
        return super.create(req, MovieBasicActorConvert.INSTANCE::convertToDTO, MovieBasicActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影演职员关联表")
    public CommonResult<Boolean> update(@RequestBody MovieBasicActorUpdateReq req) {
        return super.update(req, MovieBasicActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影演职员关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieBasicActorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影演职员关联表")
    public void export(MovieBasicActorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影演职员关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieBasicActorExp.class,
                    MovieBasicActorConvert.INSTANCE::convertToDTO, MovieBasicActorConvert.INSTANCE::convertToExp, response);
    }

}