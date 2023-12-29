package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieTagConvert;
import cc.onelooker.kaleido.dto.movie.MovieTagDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieTagCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieTagPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieTagUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieTagViewResp;
import cc.onelooker.kaleido.service.movie.MovieTagService;
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
 * 电影标签前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Api(tags = "电影标签")
@RestController
@RequestMapping("/movieTag")
public class MovieTagController extends AbstractCrudController<MovieTagDTO> {

    @Autowired
    private MovieTagService movieTagService;

    @Override
    protected IBaseService getService() {
        return movieTagService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影标签")
    public CommonResult<PageResult<MovieTagPageResp>> page(MovieTagPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieTagConvert.INSTANCE::convertToDTO, MovieTagConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影标签详情")
    public CommonResult<MovieTagViewResp> view(Long id) {
        return super.view(id, MovieTagConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影标签")
    public CommonResult<MovieTagCreateResp> create(@RequestBody MovieTagCreateReq req) {
        return super.create(req, MovieTagConvert.INSTANCE::convertToDTO, MovieTagConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影标签")
    public CommonResult<Boolean> update(@RequestBody MovieTagUpdateReq req) {
        return super.update(req, MovieTagConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影标签")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}