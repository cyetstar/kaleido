package cc.onelooker.kaleido.web.controller.tvshow;

import cc.onelooker.kaleido.convert.tvshow.TvshowGenreConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowGenreDTO;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenreCreateReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenrePageReq;
import cc.onelooker.kaleido.dto.tvshow.req.TvshowGenreUpdateReq;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenreCreateResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenrePageResp;
import cc.onelooker.kaleido.dto.tvshow.resp.TvshowGenreViewResp;
import cc.onelooker.kaleido.service.tvshow.TvshowGenreService;
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
 * 剧集类型前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Api(tags = "剧集类型")
@RestController
@RequestMapping("/tvshowGenre")
public class TvshowGenreController extends AbstractCrudController<TvshowGenreDTO> {

    @Autowired
    private TvshowGenreService tvshowGenreService;

    @Override
    protected IBaseService getService() {
        return tvshowGenreService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询剧集类型")
    public CommonResult<PageResult<TvshowGenrePageResp>> page(TvshowGenrePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowGenreConvert.INSTANCE::convertToDTO, TvshowGenreConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看剧集类型详情")
    public CommonResult<TvshowGenreViewResp> view(Long id) {
        return super.view(id, TvshowGenreConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增剧集类型")
    public CommonResult<TvshowGenreCreateResp> create(@RequestBody TvshowGenreCreateReq req) {
        return super.create(req, TvshowGenreConvert.INSTANCE::convertToDTO, TvshowGenreConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑剧集类型")
    public CommonResult<Boolean> update(@RequestBody TvshowGenreUpdateReq req) {
        return super.update(req, TvshowGenreConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除剧集类型")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}