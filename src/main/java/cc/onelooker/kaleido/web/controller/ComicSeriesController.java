package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.ComicSeriesCreateResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesPageResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesSearchInfoResp;
import cc.onelooker.kaleido.dto.resp.ComicSeriesViewResp;
import cc.onelooker.kaleido.service.AuthorService;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.ComicSeriesService;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.KaleidoUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 漫画系列前端控制器
 *
 * @author cyetstar
 * @date 2024-03-12 17:47:50
 */

@Api(tags = "漫画系列")
@RestController
@RequestMapping("/comicSeries")
public class ComicSeriesController extends AbstractCrudController<ComicSeriesDTO> {

    @Autowired
    private ComicSeriesService comicSeriesService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TmmApiService tmmApiService;

    @Autowired
    private ComicManager comicManager;

    @Override
    protected IBaseService getService() {
        return comicSeriesService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询漫画系列")
    public CommonResult<PageResult<ComicSeriesPageResp>> page(ComicSeriesPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:added_at");
        return super.page(req, pageParam, ComicSeriesConvert.INSTANCE::convertToDTO, ComicSeriesConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画系列详情")
    public CommonResult<ComicSeriesViewResp> view(String id) {
        ComicSeriesDTO comicSeriesDTO = comicManager.findSeriesById(id);
        ComicSeriesViewResp resp = ComicSeriesConvert.INSTANCE.convertToViewResp(comicSeriesDTO);
        return CommonResult.success(resp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增漫画系列")
    public CommonResult<ComicSeriesCreateResp> create(@RequestBody ComicSeriesCreateReq req) {
        return super.create(req, ComicSeriesConvert.INSTANCE::convertToDTO, ComicSeriesConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑漫画系列")
    public CommonResult<Boolean> update(@RequestBody ComicSeriesUpdateReq req) {
        ComicSeriesDTO comicSeriesDTO = ComicSeriesConvert.INSTANCE.convertToDTO(req);
        if (req.getWriterList() != null) {
            comicSeriesDTO.setWriterList(req.getWriterList().stream().map(s -> authorService.findById(s)).collect(Collectors.toList()));
        }
        if (req.getPencillerList() != null) {
            comicSeriesDTO.setPencillerList(req.getPencillerList().stream().map(s -> authorService.findById(s)).collect(Collectors.toList()));
        }
        comicManager.saveSeries(comicSeriesDTO);
        return CommonResult.success(true);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除漫画系列")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @PostMapping("searchInfo")
    @ApiOperation(value = "查询信息")
    public CommonResult<List<ComicSeriesSearchInfoResp>> searchInfo(@RequestBody ComicSeriesSearchInfoReq req) {
        List<Comic> comicList = tmmApiService.searchComic(req.getKeyword(), req.getVer());
        List<ComicSeriesSearchInfoResp> respList = comicList.stream().map(s -> {
            ComicSeriesSearchInfoResp resp = ComicSeriesConvert.INSTANCE.convertToSearchInfoResp(s);
            ComicSeriesDTO comicSeriesDTO = comicSeriesService.findByBgmId(s.getBgmId());
            if (comicSeriesDTO != null) {
                resp.setId(comicSeriesDTO.getId());
            }
            return resp;
        }).collect(Collectors.toList());
        return CommonResult.success(respList);
    }

    @PostMapping("matchPath")
    @ApiOperation(value = "匹配文件信息")
    public CommonResult<Boolean> matchPath(@RequestBody ComicSeriesMatchPathReq req) {
        Comic comic = new Comic();
        comic.setBgmId(req.getBgmId());
        comic.setSeries(req.getSeries());
        comicManager.matchPath(Paths.get(req.getPath()), comic);
        return CommonResult.success(true);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(String id) {
        ComicSeriesDTO comicSeriesDTO = comicSeriesService.findById(id);
        Path path = KaleidoUtils.getComicPath(comicSeriesDTO.getPath());
        return CommonResult.success(path.toString());
    }

}