package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.ArtistConvert;
import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.ArtistCreateResp;
import cc.onelooker.kaleido.dto.resp.ArtistPageResp;
import cc.onelooker.kaleido.dto.resp.ArtistSearchNeteaseResp;
import cc.onelooker.kaleido.dto.resp.ArtistViewResp;
import cc.onelooker.kaleido.service.ArtistService;
import cc.onelooker.kaleido.third.tmm.Artist;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import com.google.common.collect.Lists;
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
import java.util.List;

/**
 * 艺术家前端控制器
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */

@Api(tags = "艺术家")
@RestController
@RequestMapping("/artist")
public class ArtistController extends AbstractCrudController<ArtistDTO> {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private TmmApiService tmmApiService;

    @Override
    protected IBaseService<ArtistDTO> getService() {
        return artistService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家")
    public CommonResult<PageResult<ArtistPageResp>> page(ArtistPageReq req, PageParam pageParam) {
        ArtistDTO dto = ArtistConvert.INSTANCE.convertToDTO(req);
        if (StringUtils.isNotEmpty(req.getIds())) {
            dto.setIdList(Arrays.asList(StringUtils.split(req.getIds(), Constants.COMMA)));
        }
        PageResult<ArtistDTO> dtoPageResult = artistService.page(dto, pageParam.toPage());
        PageResult<ArtistPageResp> pageResult = PageResult.convert(dtoPageResult,
                ArtistConvert.INSTANCE::convertToPageResp);
        return CommonResult.success(pageResult);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家详情")
    public CommonResult<ArtistViewResp> view(Long id) {
        return super.view(id, ArtistConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家")
    public CommonResult<ArtistCreateResp> create(@RequestBody ArtistCreateReq req) {
        return super.create(req, ArtistConvert.INSTANCE::convertToDTO, ArtistConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家")
    public CommonResult<Boolean> update(@RequestBody ArtistUpdateReq req) {
        return super.update(req, ArtistConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping("searchNetease")
    public CommonResult<List<ArtistSearchNeteaseResp>> searchNetease(ArtistSearchNeteaseReq req) {
        List<Artist> artistList = tmmApiService.searchArtist(req.getKeyword(), req.getSource());
        List<ArtistSearchNeteaseResp> respList = Lists.newArrayList();
        for (Artist artist : artistList) {
            respList.add(ArtistConvert.INSTANCE.convertToSearchNeteaseResp(artist));
        }
        return CommonResult.success(respList);
    }

    @PostMapping("matchNetease")
    public CommonResult<Boolean> matchNetease(@RequestBody ArtistMatchNeteaseReq req) {
        return CommonResult.success(artistService.updateNeteaseId(req.getId(), req.getNeteaseId()));
    }

}