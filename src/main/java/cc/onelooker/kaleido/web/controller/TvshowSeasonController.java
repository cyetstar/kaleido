package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.TvshowSeasonConvert;
import cc.onelooker.kaleido.dto.TvshowSeasonDTO;
import cc.onelooker.kaleido.dto.TvshowShowDTO;
import cc.onelooker.kaleido.dto.req.TvshowSeasonCreateReq;
import cc.onelooker.kaleido.dto.req.TvshowSeasonPageReq;
import cc.onelooker.kaleido.dto.req.TvshowSeasonReadNFOReq;
import cc.onelooker.kaleido.dto.req.TvshowSeasonUpdateReq;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonCreateResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonPageResp;
import cc.onelooker.kaleido.dto.resp.TvshowSeasonViewResp;
import cc.onelooker.kaleido.service.TvshowManager;
import cc.onelooker.kaleido.service.TvshowSeasonService;
import cc.onelooker.kaleido.service.TvshowShowService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

/**
 * 单季前端控制器
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */

@Api(tags = "单季")
@RestController
@RequestMapping("/tvshowSeason")
public class TvshowSeasonController extends AbstractCrudController<TvshowSeasonDTO> {

    @Autowired
    private TvshowSeasonService tvshowSeasonService;

    @Autowired
    private TvshowShowService tvshowShowService;

    @Autowired
    private TvshowManager tvshowManager;

    @Autowired

    @Override
    protected IBaseService getService() {
        return tvshowSeasonService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询单季")
    public CommonResult<PageResult<TvshowSeasonPageResp>> page(TvshowSeasonPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看单季详情")
    public CommonResult<TvshowSeasonViewResp> view(String id) {
        return super.view(id, TvshowSeasonConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增单季")
    public CommonResult<TvshowSeasonCreateResp> create(@RequestBody TvshowSeasonCreateReq req) {
        return super.create(req, TvshowSeasonConvert.INSTANCE::convertToDTO, TvshowSeasonConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑单季")
    public CommonResult<Boolean> update(@RequestBody TvshowSeasonUpdateReq req) {
        return super.update(req, TvshowSeasonConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除单季")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @PostMapping("readNFO")
    @ApiOperation(value = "读取NFO")
    public CommonResult<Boolean> readNFO(@RequestBody TvshowSeasonReadNFOReq req) throws Exception {
        tvshowManager.readSeasonNFO(req.getId());
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        TvshowSeasonDTO tvshowSeasonDTO = tvshowSeasonService.findById(id);
        TvshowShowDTO tvshowShowDTO = tvshowShowService.findById(tvshowSeasonDTO.getShowId());
        Path folderPath = KaleidoUtils.getTvshowPath(tvshowShowDTO.getPath());
        Path seasonPath = folderPath.resolve("Season " + StringUtils.leftPad(String.valueOf(tvshowSeasonDTO.getSeasonIndex()), 2, '0'));
        return CommonResult.success(seasonPath.toString());
    }

}