package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TvshowActorConvert;
import cc.onelooker.kaleido.dto.TvshowActorDTO;
import cc.onelooker.kaleido.dto.req.TvshowActorCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowActorPageReq;
import cc.onelooker.kaleido.dto.req.TvshowActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowActorCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowActorPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowActorViewResp;
import cc.onelooker.kaleido.service.TvshowActorService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 剧集演职员前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Api(tags = "剧集演职员")
@RestController
@RequestMapping("/tvshowActor")
public class TvshowActorController extends AbstractCrudController<TvshowActorDTO> {

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
    public CommonResult<TvshowActorViewResp> view(String id) {
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
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}