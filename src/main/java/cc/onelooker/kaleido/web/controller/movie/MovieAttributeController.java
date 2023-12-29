package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieAttributeConvert;
import cc.onelooker.kaleido.dto.movie.MovieAttributeDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributeCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributePageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieAttributeUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributeCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributePageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieAttributeViewResp;
import cc.onelooker.kaleido.service.movie.MovieAttributeService;
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
 * 电影属性值前端控制器
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */

@Api(tags = "电影属性值")
@RestController
@RequestMapping("/movieAttribute")
public class MovieAttributeController extends AbstractCrudController<MovieAttributeDTO> {

    @Autowired
    private MovieAttributeService movieAttributeService;

    @Override
    protected IBaseService getService() {
        return movieAttributeService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影属性值")
    public CommonResult<PageResult<MovieAttributePageResp>> page(MovieAttributePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieAttributeConvert.INSTANCE::convertToDTO, MovieAttributeConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影属性值详情")
    public CommonResult<MovieAttributeViewResp> view(Long id) {
        return super.view(id, MovieAttributeConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影属性值")
    public CommonResult<MovieAttributeCreateResp> create(@RequestBody MovieAttributeCreateReq req) {
        return super.create(req, MovieAttributeConvert.INSTANCE::convertToDTO, MovieAttributeConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影属性值")
    public CommonResult<Boolean> update(@RequestBody MovieAttributeUpdateReq req) {
        return super.update(req, MovieAttributeConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影属性值")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}