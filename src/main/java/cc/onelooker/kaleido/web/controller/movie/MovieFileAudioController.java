package cc.onelooker.kaleido.web.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.movie.MovieFileAudioService;
import cc.onelooker.kaleido.dto.movie.MovieFileAudioDTO;
import cc.onelooker.kaleido.convert.movie.MovieFileAudioConvert;
import cc.onelooker.kaleido.dto.movie.req.*;
import cc.onelooker.kaleido.dto.movie.resp.*;
import cc.onelooker.kaleido.exp.movie.MovieFileAudioExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 电影文件音频信息前端控制器
*
* @author xiadawei
* @date 2023-06-13 20:35:11
*/

@Api(tags = "电影文件音频信息")
@RestController
@RequestMapping("/movieFileAudio")
public class MovieFileAudioController extends AbstractCrudController<MovieFileAudioDTO>{

    @Autowired
    private MovieFileAudioService movieFileAudioService;

    @Override
    protected IBaseService getService() {
        return movieFileAudioService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影文件音频信息")
    public CommonResult<PageResult<MovieFileAudioPageResp>> page(MovieFileAudioPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieFileAudioConvert.INSTANCE::convertToDTO, MovieFileAudioConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影文件音频信息详情")
    public CommonResult<MovieFileAudioViewResp> view(Long id) {
        return super.view(id, MovieFileAudioConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影文件音频信息")
    public CommonResult<MovieFileAudioCreateResp> create(@RequestBody MovieFileAudioCreateReq req) {
        return super.create(req, MovieFileAudioConvert.INSTANCE::convertToDTO, MovieFileAudioConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影文件音频信息")
    public CommonResult<Boolean> update(@RequestBody MovieFileAudioUpdateReq req) {
        return super.update(req, MovieFileAudioConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影文件音频信息")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MovieFileAudioExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出电影文件音频信息")
    public void export(MovieFileAudioPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "电影文件音频信息" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MovieFileAudioExp.class,
                    MovieFileAudioConvert.INSTANCE::convertToDTO, MovieFileAudioConvert.INSTANCE::convertToExp, response);
    }

}