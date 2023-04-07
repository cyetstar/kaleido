package cc.onelooker.kaleido.web.controller.business;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zjjcnt.common.core.domain.*;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import cc.onelooker.kaleido.service.business.VideoService;
import cc.onelooker.kaleido.dto.business.VideoDTO;
import cc.onelooker.kaleido.convert.business.VideoConvert;
import cc.onelooker.kaleido.dto.business.req.*;
import cc.onelooker.kaleido.dto.business.resp.*;
import cc.onelooker.kaleido.dto.business.exp.VideoExp;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.lang.Long;
import java.lang.String;


/**
* 前端控制器
*
* @author cyetstar
* @date 2023-04-06 13:32:12
*/

@Api(tags = "")
@RestController
@RequestMapping("/video")
public class VideoController extends AbstractCrudController<VideoDTO>{

    @Autowired
    private VideoService videoService;

    @Override
    protected IBaseService getService() {
        return videoService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询")
    public CommonResult<PageResult<VideoPageResp>> page(VideoPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, VideoConvert.INSTANCE::convertToDTO, VideoConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看详情")
    public CommonResult<VideoViewResp> view(Long id) {
        return super.view(id, VideoConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增")
    public CommonResult<VideoCreateResp> create(@RequestBody VideoCreateReq req) {
        return super.create(req, VideoConvert.INSTANCE::convertToDTO, VideoConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑")
    public CommonResult<Boolean> update(@RequestBody VideoUpdateReq req) {
        return super.update(req, VideoConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除")
    public CommonResult<Boolean> delete(@RequestParam(name = "id") Long... ids) {
        return super.delete(ids);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(VideoExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出")
    public void export(VideoPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, VideoExp.class,
                    VideoConvert.INSTANCE::convertToDTO, VideoConvert.INSTANCE::convertToExp, response);
    }

}