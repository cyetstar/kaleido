package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieActorConvert;
import cc.onelooker.kaleido.dto.movie.MovieActorDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieActorCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieActorPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieActorUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieActorViewResp;
import cc.onelooker.kaleido.service.movie.MovieActorService;
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
 * 演职员前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 01:19:02
 */

@Api(tags = "演职员")
@RestController
@RequestMapping("/movieActor")
public class MovieActorController extends AbstractCrudController<MovieActorDTO> {

    @Autowired
    private MovieActorService movieActorService;

    @Override
    protected IBaseService getService() {
        return movieActorService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询演职员")
    public CommonResult<PageResult<MovieActorPageResp>> page(MovieActorPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieActorConvert.INSTANCE::convertToDTO, MovieActorConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看演职员详情")
    public CommonResult<MovieActorViewResp> view(Long id) {
        return super.view(id, MovieActorConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增演职员")
    public CommonResult<MovieActorCreateResp> create(@RequestBody MovieActorCreateReq req) {
        return super.create(req, MovieActorConvert.INSTANCE::convertToDTO, MovieActorConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑演职员")
    public CommonResult<Boolean> update(@RequestBody MovieActorUpdateReq req) {
        return super.update(req, MovieActorConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除演职员")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}