package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TvshowEpisodeConvert;
import cc.onelooker.kaleido.dto.TvshowEpisodeDTO;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodePageReq;
import cc.onelooker.kaleido.dto.req.TvshowEpisodeUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodePageResp;
import cc.onelooker.kaleido.dto.resp.TvshowEpisodeViewResp;
import cc.onelooker.kaleido.service.TvshowEpisodeService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 单集前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Slf4j
@Api(tags = "单集")
@RestController
@RequestMapping("/tvshowEpisode")
public class TvshowEpisodeController extends AbstractCrudController<TvshowEpisodeDTO> {

    @Autowired
    private TvshowEpisodeService tvshowEpisodeService;

    @Override
    protected IBaseService getService() {
        return tvshowEpisodeService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询单集")
    public CommonResult<PageResult<TvshowEpisodePageResp>> page(TvshowEpisodePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowEpisodeConvert.INSTANCE::convertToDTO, TvshowEpisodeConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看单集详情")
    public CommonResult<TvshowEpisodeViewResp> view(String id) {
        return super.view(id, TvshowEpisodeConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单集")
    public CommonResult<TvshowEpisodeCreateResp> create(@RequestBody TvshowEpisodeCreateReq req) {
        return super.create(req, TvshowEpisodeConvert.INSTANCE::convertToDTO, TvshowEpisodeConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单集")
    public CommonResult<Boolean> update(@RequestBody TvshowEpisodeUpdateReq req) {
        return super.update(req, TvshowEpisodeConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单集")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}