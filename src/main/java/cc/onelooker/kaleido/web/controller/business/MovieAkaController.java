package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieAkaService;
import cc.onelooker.kaleido.dto.business.MovieAkaDTO;
import cc.onelooker.kaleido.convert.business.MovieAkaConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieAkaExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 别名前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "别名")
@RestController
@RequestMapping("/movieAka")
public class MovieAkaController extends AbstractCrudController<MovieAkaDTO>{

    @Autowired
    private MovieAkaService movieAkaService;

    @Override
    protected IBaseService getService() {
        return movieAkaService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询别名")
    public CommonResult<PageResult<MovieAkaPageResp>> page(MovieAkaPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieAkaConvert.INSTANCE::convertToDTO, MovieAkaConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看别名详情")
    public CommonResult<MovieAkaViewResp> view(Long id) {
        return super.view(id, MovieAkaConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增别名")
    public CommonResult<MovieAkaCreateResp> create(@RequestBody MovieAkaCreateReq req) {
        return super.create(req, MovieAkaConvert.INSTANCE::convertToDTO, MovieAkaConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑别名")
    public CommonResult<Boolean> update(@RequestBody MovieAkaUpdateReq req) {
        return super.update(req, MovieAkaConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除别名")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieAkaExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出别名")
    public void export(MovieAkaPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "别名" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieAkaExp.class,
                    MovieAkaConvert.INSTANCE::convertToDTO, MovieAkaConvert.INSTANCE::convertToExp, response);
    }

}