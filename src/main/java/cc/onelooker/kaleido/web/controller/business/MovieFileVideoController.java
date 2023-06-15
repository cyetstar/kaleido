package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieFileVideoService;
import cc.onelooker.kaleido.dto.business.MovieFileVideoDTO;
import cc.onelooker.kaleido.convert.business.MovieFileVideoConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.exp.business.MovieFileVideoExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;
import java.lang.Integer;


/**
* 电影文件视频信息前端控制器
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/

@Api(tags = "电影文件视频信息")
@RestController
@RequestMapping("/movieFileVideo")
public class MovieFileVideoController extends AbstractCrudController<MovieFileVideoDTO>{

    @Autowired
    private MovieFileVideoService movieFileVideoService;

    @Override
    protected IBaseService getService() {
        return movieFileVideoService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影文件视频信息")
    public CommonResult<PageResult<MovieFileVideoPageResp>> page(MovieFileVideoPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieFileVideoConvert.INSTANCE::convertToDTO, MovieFileVideoConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影文件视频信息详情")
    public CommonResult<MovieFileVideoViewResp> view(Long id) {
        return super.view(id, MovieFileVideoConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影文件视频信息")
    public CommonResult<MovieFileVideoCreateResp> create(@RequestBody MovieFileVideoCreateReq req) {
        return super.create(req, MovieFileVideoConvert.INSTANCE::convertToDTO, MovieFileVideoConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影文件视频信息")
    public CommonResult<Boolean> update(@RequestBody MovieFileVideoUpdateReq req) {
        return super.update(req, MovieFileVideoConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影文件视频信息")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieFileVideoExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影文件视频信息")
    public void export(MovieFileVideoPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影文件视频信息" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieFileVideoExp.class,
                    MovieFileVideoConvert.INSTANCE::convertToDTO, MovieFileVideoConvert.INSTANCE::convertToExp, response);
    }

}