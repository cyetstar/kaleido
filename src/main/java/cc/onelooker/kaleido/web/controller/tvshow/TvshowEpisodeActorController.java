package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowEpisodeActorConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorPageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowEpisodeActorUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorPageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowEpisodeActorViewResp;
import cc.onelooker.kaleido.exp.tvshow.TvshowEpisodeActorExp;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeActorService;
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
* 单集演职员关联表前端控制器
*
* @author cyetstar
* @date 2023-11-26 23:27:03
*/

@Api(tags = "单集演职员关联表")
@RestController
@RequestMapping("/tvshowEpisodeActor")
public class TvshowEpisodeActorController extends AbstractCrudController<TvshowEpisodeActorDTO>{

    @Autowired
    private TvshowEpisodeActorService tvshowEpisodeActorService;

    @Override
    protected IBaseService getService() {
        return tvshowEpisodeActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询单集演职员关联表")
    public CommonResult<PageResult<TvshowEpisodeActorPageResp>> page(TvshowEpisodeActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowEpisodeActorConvert.INSTANCE::convertToDTO, TvshowEpisodeActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看单集演职员关联表详情")
    public CommonResult<TvshowEpisodeActorViewResp> view(Long id) {
        return super.view(id, TvshowEpisodeActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单集演职员关联表")
    public CommonResult<TvshowEpisodeActorCreateResp> create(@RequestBody TvshowEpisodeActorCreateReq req) {
        return super.create(req, TvshowEpisodeActorConvert.INSTANCE::convertToDTO, TvshowEpisodeActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单集演职员关联表")
    public CommonResult<Boolean> update(@RequestBody TvshowEpisodeActorUpdateReq req) {
        return super.update(req, TvshowEpisodeActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单集演职员关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(TvshowEpisodeActorExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出单集演职员关联表")
    public void export(TvshowEpisodeActorPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "单集演职员关联表" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, TvshowEpisodeActorExp.class,
                    TvshowEpisodeActorConvert.INSTANCE::convertToDTO, TvshowEpisodeActorConvert.INSTANCE::convertToExp, response);
    }

}