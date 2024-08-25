package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MovieActorConvert;
import cc.onelooker.kaleido.dto.MovieActorDTO;
import cc.onelooker.kaleido.dto.req.MovieActorCreateReq;
import cc.onelooker.kaleido.dto.req.MovieActorPageReq;
import cc.onelooker.kaleido.dto.req.MovieActorUpdateReq;
import cc.onelooker.kaleido.dto.resp.MovieActorCreateResp;
import cc.onelooker.kaleido.dto.resp.MovieActorPageResp;
import cc.onelooker.kaleido.dto.resp.MovieActorViewResp;
import cc.onelooker.kaleido.service.MovieActorService;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
        MovieActorDTO dto = MovieActorConvert.INSTANCE.convertToDTO(req);
        if (StringUtils.isNotEmpty(req.getIds())) {
            dto.setIdList(Arrays.asList(StringUtils.split(req.getIds(), Constants.COMMA)));
        }
        PageResult<MovieActorDTO> dtoPageResult = movieActorService.page(dto, pageParam.toPage());
        PageResult<MovieActorPageResp> pageResult = PageResult.convert(dtoPageResult, MovieActorConvert.INSTANCE::convertToPageResp);
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看演职员详情")
    public CommonResult<MovieActorViewResp> view(String id) {
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
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

}