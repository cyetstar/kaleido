package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieAkaConvert;
import cc.onelooker.kaleido.dto.movie.MovieAkaDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAkaUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAkaViewResp;
import cc.onelooker.kaleido.service.movie.MovieAkaService;
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
 * 别名前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Api(tags = "别名")
@RestController
@RequestMapping("/movieAka")
public class MovieAkaController extends AbstractCrudController<MovieAkaDTO> {

    @Autowired
    private MovieAkaService movieAkaService;

    @Override
    protected IBaseService getService() {
        return movieAkaService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询别名")
    public CommonResult<PageResult<MovieAkaPageResp>> page(MovieAkaPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieAkaConvert.INSTANCE::convertToDTO, MovieAkaConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看别名详情")
    public CommonResult<MovieAkaViewResp> view(Long id) {
        return super.view(id, MovieAkaConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增别名")
    public CommonResult<MovieAkaCreateResp> create(@RequestBody MovieAkaCreateReq req) {
        return super.create(req, MovieAkaConvert.INSTANCE::convertToDTO, MovieAkaConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑别名")
    public CommonResult<Boolean> update(@RequestBody MovieAkaUpdateReq req) {
        return super.update(req, MovieAkaConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除别名")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}