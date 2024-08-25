package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MovieThreadFilenameConvert;
import cc.onelooker.kaleido.dto.MovieThreadFilenameDTO;
import cc.onelooker.kaleido.dto.req.MovieThreadFilenameCreateReq;
import cc.onelooker.kaleido.dto.req.MovieThreadFilenamePageReq;
import cc.onelooker.kaleido.dto.req.MovieThreadFilenameUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieThreadFilenameCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieThreadFilenamePageResp;
import cc.onelooker.kaleido.dto.resp.MovieThreadFilenameViewResp;
import cc.onelooker.kaleido.service.MovieThreadFilenameService;
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
 * 电影发布文件前端控制器
 *
 * @author cyetstar
 * @date 2023-12-18 16:03:32
 */

@Api(tags = "电影发布文件")
@RestController
@RequestMapping("/movieThreadFilename")
public class MovieThreadFilenameController extends AbstractCrudController<MovieThreadFilenameDTO> {

    @Autowired
    private MovieThreadFilenameService movieThreadFilenameService;

    @Override
    protected IBaseService getService() {
        return movieThreadFilenameService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询电影发布文件")
    public CommonResult<PageResult<MovieThreadFilenamePageResp>> page(MovieThreadFilenamePageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MovieThreadFilenameConvert.INSTANCE::convertToDTO, MovieThreadFilenameConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看电影发布文件详情")
    public CommonResult<MovieThreadFilenameViewResp> view(Long id) {
        return super.view(id, MovieThreadFilenameConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增电影发布文件")
    public CommonResult<MovieThreadFilenameCreateResp> create(@RequestBody MovieThreadFilenameCreateReq req) {
        return super.create(req, MovieThreadFilenameConvert.INSTANCE::convertToDTO, MovieThreadFilenameConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑电影发布文件")
    public CommonResult<Boolean> update(@RequestBody MovieThreadFilenameUpdateReq req) {
        return super.update(req, MovieThreadFilenameConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除电影发布文件")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

}