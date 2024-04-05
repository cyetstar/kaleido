package cc.onelooker.kaleido.web.controller.comic;

import cc.onelooker.kaleido.convert.comic.ComicSeriesConvert;
import cc.onelooker.kaleido.dto.AlternateTitleDTO;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.dto.comic.ComicAuthorDTO;
import cc.onelooker.kaleido.dto.comic.ComicSeriesDTO;
import cc.onelooker.kaleido.dto.comic.req.*;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesCreateResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesPageResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesSearchInfoResp;
import cc.onelooker.kaleido.dto.comic.resp.ComicSeriesViewResp;
import cc.onelooker.kaleido.service.AlternateTitleService;
import cc.onelooker.kaleido.service.AttributeService;
import cc.onelooker.kaleido.service.ComicManager;
import cc.onelooker.kaleido.service.comic.ComicAuthorService;
import cc.onelooker.kaleido.service.comic.ComicSeriesService;
import cc.onelooker.kaleido.third.tmm.Comic;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
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
    private AlternateTitleService alternateTitleService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private ComicAuthorService comicAuthorService;

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
        return super.page(req, pageParam, ComicSeriesConvert.INSTANCE::convertToDTO, ComicSeriesConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看漫画系列详情")
    public CommonResult<ComicSeriesViewResp> view(String id) {
        ComicSeriesViewResp resp = super.doView(id, ComicSeriesConvert.INSTANCE::convertToViewResp);
        List<AlternateTitleDTO> alternateTitleDTOList = alternateTitleService.listBySubjectId(id);
        List<String> alternateTitleList = alternateTitleDTOList.stream().map(AlternateTitleDTO::getTitle).collect(Collectors.toList());
        resp.setAlternateTitleList(alternateTitleList);
        List<AttributeDTO> attributeDTOList = attributeService.listBySubjectId(id);
        Map<String, List<AttributeDTO>> attributeMap = attributeDTOList.stream().collect(Collectors.groupingBy(AttributeDTO::getType));
        resp.setTagList(attributeMap.getOrDefault("ComicTag", Lists.newArrayList()).stream().map(ComicSeriesConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
        List<ComicAuthorDTO> comicAuthorDTOList = comicAuthorService.listBySeriesId(id);
        resp.setAuthorList(comicAuthorDTOList.stream().map(ComicSeriesConvert.INSTANCE::convertToViewResp).collect(Collectors.toList()));
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
        return super.update(req, ComicSeriesConvert.INSTANCE::convertToDTO);
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
        List<ComicSeriesSearchInfoResp> respList = comicList.stream().map(ComicSeriesConvert.INSTANCE::convertToSearchInfoResp).collect(Collectors.toList());
        return CommonResult.success(respList);
    }

    @PostMapping("matchPath")
    @ApiOperation(value = "匹配文件信息")
    public CommonResult<Boolean> matchPath(@RequestBody ComicSeriesMatchPathReq req) {
        comicManager.matchPath(Paths.get(req.getPath()), req.getBgmId());
        return CommonResult.success(true);
    }

}