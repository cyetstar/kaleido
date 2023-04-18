package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieSetService;
import cc.onelooker.kaleido.dto.business.MovieSetDTO;
import cc.onelooker.kaleido.convert.business.MovieSetConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieSetExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
* 电影集合前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "电影集合")
@RestController
@RequestMapping("/movieSet")
public class MovieSetController extends AbstractCrudController<MovieSetDTO>{

    @Autowired
    private MovieSetService movieSetService;

    @Override
    protected IBaseService getService() {
        return movieSetService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影集合")
    public CommonResult<PageResult<MovieSetPageResp>> page(MovieSetPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieSetConvert.INSTANCE::convertToDTO, MovieSetConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影集合详情")
    public CommonResult<MovieSetViewResp> view(Long id) {
        return super.view(id, MovieSetConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影集合")
    public CommonResult<MovieSetCreateResp> create(@RequestBody MovieSetCreateReq req) {
        return super.create(req, MovieSetConvert.INSTANCE::convertToDTO, MovieSetConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影集合")
    public CommonResult<Boolean> update(@RequestBody MovieSetUpdateReq req) {
        return super.update(req, MovieSetConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影集合")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieSetExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影集合")
    public void export(MovieSetPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影集合" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieSetExp.class,
                    MovieSetConvert.INSTANCE::convertToDTO, MovieSetConvert.INSTANCE::convertToExp, response);
    }

}