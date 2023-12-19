package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowActorConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowActorDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowActorUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowActorViewResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowActorExp;
import cc.onelooker.kaleido.service.tvshow.TvshowActorService;
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
* 剧集演职员前端控制器
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/

@Api(tags = "剧集演职员")
@RestController
@RequestMapping("/tvshowActor")
public class TvshowActorController extends AbstractCrudController<TvshowActorDTO>{

    @Autowired
    private TvshowActorService tvshowActorService;

    @Override
    protected IBaseService getService() {
        return tvshowActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集演职员")
    public CommonResult<PageResult<TvshowActorPageResp>> page(TvshowActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowActorConvert.INSTANCE::convertToDTO, TvshowActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集演职员详情")
    public CommonResult<TvshowActorViewResp> view(Long id) {
        return super.view(id, TvshowActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集演职员")
    public CommonResult<TvshowActorCreateResp> create(@RequestBody TvshowActorCreateReq req) {
        return super.create(req, TvshowActorConvert.INSTANCE::convertToDTO, TvshowActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集演职员")
    public CommonResult<Boolean> update(@RequestBody TvshowActorUpdateReq req) {
        return super.update(req, TvshowActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集演职员")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowActorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出剧集演职员")
    public void export(TvshowActorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "剧集演职员" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowActorExp.class,
                    TvshowActorConvert.INSTANCE::convertToDTO, TvshowActorConvert.INSTANCE::convertToExp, response);
    }

}