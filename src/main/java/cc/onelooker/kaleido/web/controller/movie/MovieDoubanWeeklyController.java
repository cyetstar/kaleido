package cc.onelooker.kaleido.web.controller.movie;

import cc.onelooker.kaleido.convert.movie.MovieDoubanWeeklyConvert;
import cc.onelooker.kaleido.dto.movie.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyCreateReq;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyPageReq;
import cc.onelooker.kaleido.dto.movie.req.MovieDoubanWeeklyUpdateReq;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyCreateResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyPageResp;
import cc.onelooker.kaleido.dto.movie.resp.MovieDoubanWeeklyViewResp;
import cc.onelooker.kaleido.service.movie.MovieDoubanWeeklyService;
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
 * 豆瓣电影口碑榜前端控制器
 *
 * @author cyetstar
 * @date 2023-12-22 11:18:26
 */

@Api(tags = "豆瓣电影口碑榜")
@RestController
@RequestMapping("/movieDoubanWeekly")
public class MovieDoubanWeeklyController extends AbstractCrudController<MovieDoubanWeeklyDTO> {

    @Autowired
    private MovieDoubanWeeklyService movieDoubanWeeklyService;

    @Override
    protected IBaseService getService() {
        return movieDoubanWeeklyService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询豆瓣电影口碑榜")
    public CommonResult<PageResult<MovieDoubanWeeklyPageResp>> page(MovieDoubanWeeklyPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieDoubanWeeklyConvert.INSTANCE::convertToDTO, MovieDoubanWeeklyConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看豆瓣电影口碑榜详情")
    public CommonResult<MovieDoubanWeeklyViewResp> view(Long id) {
        return super.view(id, MovieDoubanWeeklyConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增豆瓣电影口碑榜")
    public CommonResult<MovieDoubanWeeklyCreateResp> create(@RequestBody MovieDoubanWeeklyCreateReq req) {
        return super.create(req, MovieDoubanWeeklyConvert.INSTANCE::convertToDTO, MovieDoubanWeeklyConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑豆瓣电影口碑榜")
    public CommonResult<Boolean> update(@RequestBody MovieDoubanWeeklyUpdateReq req) {
        return super.update(req, MovieDoubanWeeklyConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除豆瓣电影口碑榜")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}