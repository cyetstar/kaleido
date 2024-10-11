package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ThreadConvert;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.MovieBasicDTO;
import cc.onelooker.kaleido.dto.ThreadDTO;
import cc.onelooker.kaleido.dto.req.ThreadCreateReq;
import cc.onelooker.kaleido.dto.req.ThreadPageReq;
import cc.onelooker.kaleido.dto.req.ThreadUpdateReq;
import cc.onelooker.kaleido.dto.req.ThreadUpdateStatusReq;
import cc.onelooker.kaleido.dto.resp.ThreadCreateResp;
import cc.onelooker.kaleido.dto.resp.ThreadPageResp;
import cc.onelooker.kaleido.dto.resp.ThreadViewResp;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.service.MovieBasicService;
import cc.onelooker.kaleido.service.ThreadService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 发布记录前端控制器
 *
 * @author cyetstar
 * @date 2023-12-18 14:53:14
 */

@Api(tags = "发布记录")
@RestController
@RequestMapping("/thread")
public class ThreadController extends AbstractCrudController<ThreadDTO> {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private MovieBasicService movieBasicService;

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Override
    protected IBaseService getService() {
        return threadService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询发布记录")
    public CommonResult<PageResult<ThreadPageResp>> page(ThreadPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, ThreadConvert.INSTANCE::convertToDTO, ThreadConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看发布记录详情")
    public CommonResult<ThreadViewResp> view(String id) {
        ThreadViewResp resp = super.doView(id, ThreadConvert.INSTANCE::convertToViewResp);
        MovieBasicDTO movieBasicDTO = null;
        ComicSeriesDTO comicSeriesDTO = null;
        Set<ThreadViewResp> threadViewRespSet = Sets.newHashSet();
        if (StringUtils.isNotEmpty(resp.getDoubanId())) {
            movieBasicDTO = movieBasicService.findByDoubanId(resp.getDoubanId());
            List<ThreadDTO> threadList = threadService.listByDoubanId(resp.getDoubanId());
            threadList.stream().map(ThreadConvert.INSTANCE::convertToViewResp).forEach(threadViewRespSet::add);
        }
        if (StringUtils.isNotEmpty(resp.getImdbId())) {
            movieBasicDTO = movieBasicService.findByImdbId(resp.getImdbId());
            List<ThreadDTO> threadList = threadService.listByImdbId(resp.getImdbId());
            threadList.stream().map(ThreadConvert.INSTANCE::convertToViewResp).forEach(threadViewRespSet::add);
        }
        if (StringUtils.isNotEmpty(resp.getBgmId())) {
            comicSeriesDTO = comicSeriesService.findByBgmId(resp.getBgmId());
            List<ThreadDTO> threadList = threadService.listByBgmId(resp.getBgmId());
            threadList.stream().map(ThreadConvert.INSTANCE::convertToViewResp).forEach(threadViewRespSet::add);
        }
        if (movieBasicDTO != null) {
            resp.setMovieBasicId(movieBasicDTO.getId());
            resp.setMovieBasicTitle(movieBasicDTO.getTitle());
            resp.setMovieBasicOriginalTitle(movieBasicDTO.getOriginalTitle());
            resp.setMovieBasicPath(movieBasicDTO.getPath());
        }

        if (comicSeriesDTO != null) {
            resp.setComicSeriesId(comicSeriesDTO.getId());
            resp.setComicSeriesTitle(comicSeriesDTO.getTitle());
            resp.setComicSeriesOriginalTitle(comicSeriesDTO.getOriginalTitle());
            resp.setComicSeriesPath(comicSeriesDTO.getPath());
        }
        List<ThreadViewResp> threadList = threadViewRespSet.stream().filter(s -> !StringUtils.equals(s.getId(), resp.getId())).collect(Collectors.toList());
        resp.setThreadList(threadList);
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增发布记录")
    public CommonResult<ThreadCreateResp> create(@RequestBody ThreadCreateReq req) {
        return super.create(req, ThreadConvert.INSTANCE::convertToDTO, ThreadConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑发布记录")
    public CommonResult<Boolean> update(@RequestBody ThreadUpdateReq req) {
        return super.update(req, ThreadConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除发布记录")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @PostMapping("updateStatus")
    @ApiOperation(value = "更新发布记录状态")
    public CommonResult<Boolean> updateStatus(@RequestBody ThreadUpdateStatusReq req) {
        threadService.updateStatus(req.getId(), req.getStatus());
        return CommonResult.success(true);
    }
}