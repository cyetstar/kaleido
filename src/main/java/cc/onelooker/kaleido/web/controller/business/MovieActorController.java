package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieActorService;
import cc.onelooker.kaleido.dto.business.MovieActorDTO;
import cc.onelooker.kaleido.convert.business.MovieActorConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.MovieActorExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import com.zjjcnt.common.core.annotation.Crypto;
import java.lang.String;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;


/**
* 演职员前端控制器
*
* @author cyetstar
* @date 2023-04-18 23:04:56
*/

@Api(tags = "演职员")
@RestController
@RequestMapping("/movieActor")
public class MovieActorController extends AbstractCrudController<MovieActorDTO>{

    @Autowired
    private MovieActorService movieActorService;

    @Override
    protected IBaseService getService() {
        return movieActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询演职员")
    public CommonResult<PageResult<MovieActorPageResp>> page(MovieActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieActorConvert.INSTANCE::convertToDTO, MovieActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看演职员详情")
    public CommonResult<MovieActorViewResp> view(Long id) {
        return super.view(id, MovieActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增演职员")
    public CommonResult<MovieActorCreateResp> create(@RequestBody MovieActorCreateReq req) {
        return super.create(req, MovieActorConvert.INSTANCE::convertToDTO, MovieActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑演职员")
    public CommonResult<Boolean> update(@RequestBody MovieActorUpdateReq req) {
        return super.update(req, MovieActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除演职员")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieActorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出演职员")
    public void export(MovieActorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "演职员" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieActorExp.class,
                    MovieActorConvert.INSTANCE::convertToDTO, MovieActorConvert.INSTANCE::convertToExp, response);
    }

}