package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.MovieFileSubtitleService;
import cc.onelooker.kaleido.dto.business.MovieFileSubtitleDTO;
import cc.onelooker.kaleido.convert.business.MovieFileSubtitleConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.exp.business.MovieFileSubtitleExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 电影文件字幕信息前端控制器
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/

@Api(tags = "电影文件字幕信息")
@RestController
@RequestMapping("/movieFileSubtitle")
public class MovieFileSubtitleController extends AbstractCrudController<MovieFileSubtitleDTO>{

    @Autowired
    private MovieFileSubtitleService movieFileSubtitleService;

    @Override
    protected IBaseService getService() {
        return movieFileSubtitleService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影文件字幕信息")
    public CommonResult<PageResult<MovieFileSubtitlePageResp>> page(MovieFileSubtitlePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieFileSubtitleConvert.INSTANCE::convertToDTO, MovieFileSubtitleConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影文件字幕信息详情")
    public CommonResult<MovieFileSubtitleViewResp> view(Long id) {
        return super.view(id, MovieFileSubtitleConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影文件字幕信息")
    public CommonResult<MovieFileSubtitleCreateResp> create(@RequestBody MovieFileSubtitleCreateReq req) {
        return super.create(req, MovieFileSubtitleConvert.INSTANCE::convertToDTO, MovieFileSubtitleConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影文件字幕信息")
    public CommonResult<Boolean> update(@RequestBody MovieFileSubtitleUpdateReq req) {
        return super.update(req, MovieFileSubtitleConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影文件字幕信息")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieFileSubtitleExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影文件字幕信息")
    public void export(MovieFileSubtitlePageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影文件字幕信息" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieFileSubtitleExp.class,
                    MovieFileSubtitleConvert.INSTANCE::convertToDTO, MovieFileSubtitleConvert.INSTANCE::convertToExp, response);
    }

}