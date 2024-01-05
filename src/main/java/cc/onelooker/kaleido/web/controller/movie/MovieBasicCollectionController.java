package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieBasicCollectionConvert;
import cc.onelooker.kaleido.dto.movie.MovieBasicCollectionDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieBasicCollectionUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieBasicCollectionViewResp;
import cc.onelooker.kaleido.service.movie.MovieBasicCollectionService;
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
 * 电影集合关联表前端控制器
 *
 * @author xiadawei
 * @date 2023-12-29 16:15:43
 */

@Api(tags = "电影集合关联表")
@RestController
@RequestMapping("/movieBasicCollection")
public class MovieBasicCollectionController extends AbstractCrudController<MovieBasicCollectionDTO> {

    @Autowired
    private MovieBasicCollectionService movieBasicCollectionService;

    @Override
    protected IBaseService getService() {
        return movieBasicCollectionService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影集合关联表")
    public CommonResult<PageResult<MovieBasicCollectionPageResp>> page(MovieBasicCollectionPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieBasicCollectionConvert.INSTANCE::convertToDTO, MovieBasicCollectionConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影集合关联表详情")
    public CommonResult<MovieBasicCollectionViewResp> view(Long id) {
        return super.view(id, MovieBasicCollectionConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影集合关联表")
    public CommonResult<MovieBasicCollectionCreateResp> create(@RequestBody MovieBasicCollectionCreateReq req) {
        return super.create(req, MovieBasicCollectionConvert.INSTANCE::convertToDTO, MovieBasicCollectionConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影集合关联表")
    public CommonResult<Boolean> update(@RequestBody MovieBasicCollectionUpdateReq req) {
        return super.update(req, MovieBasicCollectionConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影集合关联表")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}