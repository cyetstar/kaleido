package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieFileService;
import cc.onelooker.kaleido.dto.business.MovieFileDTO;
import cc.onelooker.kaleido.convert.business.MovieFileConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.exp.business.MovieFileExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;


/**
* 电影文件前端控制器
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/

@Api(tags = "电影文件")
@RestController
@RequestMapping("/movieFile")
public class MovieFileController extends AbstractCrudController<MovieFileDTO>{

    @Autowired
    private MovieFileService movieFileService;

    @Override
    protected IBaseService getService() {
        return movieFileService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影文件")
    public CommonResult<PageResult<MovieFilePageResp>> page(MovieFilePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieFileConvert.INSTANCE::convertToDTO, MovieFileConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影文件详情")
    public CommonResult<MovieFileViewResp> view(Long id) {
        return super.view(id, MovieFileConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影文件")
    public CommonResult<MovieFileCreateResp> create(@RequestBody MovieFileCreateReq req) {
        return super.create(req, MovieFileConvert.INSTANCE::convertToDTO, MovieFileConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影文件")
    public CommonResult<Boolean> update(@RequestBody MovieFileUpdateReq req) {
        return super.update(req, MovieFileConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影文件")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieFileExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影文件")
    public void export(MovieFilePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影文件" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieFileExp.class,
                    MovieFileConvert.INSTANCE::convertToDTO, MovieFileConvert.INSTANCE::convertToExp, response);
    }

}