package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MovieThreadConvert;
import cc.onelooker.kaleido.dto.MovieThreadDTO;
import cc.onelooker.kaleido.dto.req.MovieThreadCreateReq;
import cc.onelooker.kaleido.dto.req.MovieThreadPageReq;
import cc.onelooker.kaleido.dto.req.MovieThreadUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieThreadCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieThreadPageResp;
import cc.onelooker.kaleido.dto.resp.MovieThreadViewResp;
import cc.onelooker.kaleido.service.AsyncTaskManager;
import cc.onelooker.kaleido.service.MovieThreadService;
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
 * 电影发布记录前端控制器
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */

@Api(tags = "电影发布记录")
@RestController
@RequestMapping("/movieThread")
public class MovieThreadController extends AbstractCrudController<MovieThreadDTO> {

    @Autowired
    private MovieThreadService movieThreadService;

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @Override
    protected IBaseService getService() {
        return movieThreadService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影发布记录")
    public CommonResult<PageResult<MovieThreadPageResp>> page(MovieThreadPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieThreadConvert.INSTANCE::convertToDTO, MovieThreadConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影发布记录详情")
    public CommonResult<MovieThreadViewResp> view(String id) {
        return super.view(id, MovieThreadConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影发布记录")
    public CommonResult<MovieThreadCreateResp> create(@RequestBody MovieThreadCreateReq req) {
        return super.create(req, MovieThreadConvert.INSTANCE::convertToDTO, MovieThreadConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影发布记录")
    public CommonResult<Boolean> update(@RequestBody MovieThreadUpdateReq req) {
        return super.update(req, MovieThreadConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影发布记录")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}