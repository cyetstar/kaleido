package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MovieDoubanWeeklyConvert;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.MovieDoubanWeeklyDTO;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyCreateReq;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyPageReq;
import cc.onelooker.kaleido.dto.req.MovieDoubanWeeklyUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyPageResp;
import cc.onelooker.kaleido.dto.resp.MovieDoubanWeeklyViewResp;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.MovieDoubanWeeklyService;
import cc.onelooker.kaleido.service.MovieManager;
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
 * @date 2023-12-29 16:15:43
 */

@Api(tags = "豆瓣电影口碑榜")
@RestController
@RequestMapping("/movieDoubanWeekly")
public class MovieDoubanWeeklyController extends AbstractCrudController<MovieDoubanWeeklyDTO> {

    @Autowired
    private MovieDoubanWeeklyService movieDoubanWeeklyService;

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private MovieManager movieManager;

    @Override
    protected IBaseService getService() {
        return movieDoubanWeeklyService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询豆瓣电影口碑榜")
    public CommonResult<PageResult<MovieDoubanWeeklyPageResp>> page(MovieDoubanWeeklyPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:status;ASC:top;DESC:id");
        PageResult<MovieDoubanWeeklyPageResp> pageResult = super.doPage(req, pageParam, MovieDoubanWeeklyConvert.INSTANCE::convertToDTO, MovieDoubanWeeklyConvert.INSTANCE::convertToPageResp);
        pageResult.getRecords().stream().forEach(s -> {
            MovieBasicDTO movieBasicDTO = movieBasicService.findByDoubanId(s.getDoubanId());
            if (movieBasicDTO != null) {
                s.setMovieId(movieBasicDTO.getId());
                s.setImdb(movieBasicDTO.getImdbId());
            }
        });
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看豆瓣电影口碑榜详情")
    public CommonResult<MovieDoubanWeeklyViewResp> view(String id) {
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
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @PostMapping("sync")
    @ApiOperation(value = "同步豆瓣口碑榜")
    public CommonResult<Boolean> sync() {
        movieManager.syncDoubanWeekly();
        return CommonResult.success(true);
    }

}