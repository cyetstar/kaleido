package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieCollectionService;
import cc.onelooker.kaleido.dto.movie.MovieCollectionDTO;
import cc.onelooker.kaleido.convert.movie.MovieCollectionConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieCollectionExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 电影集合前端控制器
*
* @author cyetstar
* @date 2023-11-26 01:19:02
*/

@Api(tags = "电影集合")
@RestController
@RequestMapping("/movieCollection")
public class MovieCollectionController extends AbstractCrudController<MovieCollectionDTO>{

    @Autowired
    private MovieCollectionService movieCollectionService;

    @Override
    protected IBaseService getService() {
        return movieCollectionService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影集合")
    public CommonResult<PageResult<MovieCollectionPageResp>> page(MovieCollectionPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieCollectionConvert.INSTANCE::convertToDTO, MovieCollectionConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影集合详情")
    public CommonResult<MovieCollectionViewResp> view(Long id) {
        return super.view(id, MovieCollectionConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影集合")
    public CommonResult<MovieCollectionCreateResp> create(@RequestBody MovieCollectionCreateReq req) {
        return super.create(req, MovieCollectionConvert.INSTANCE::convertToDTO, MovieCollectionConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影集合")
    public CommonResult<Boolean> update(@RequestBody MovieCollectionUpdateReq req) {
        return super.update(req, MovieCollectionConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影集合")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieCollectionExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影集合")
    public void export(MovieCollectionPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影集合" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieCollectionExp.class,
                    MovieCollectionConvert.INSTANCE::convertToDTO, MovieCollectionConvert.INSTANCE::convertToExp, response);
    }

}