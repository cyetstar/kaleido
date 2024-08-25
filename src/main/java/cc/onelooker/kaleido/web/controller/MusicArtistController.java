package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MusicArtistConvert;
import cc.onelooker.kaleido.dto.MusicArtistDTO;
import cc.onelooker.kaleido.dto.resp.MusicArtistCreateResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistPageResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistSearchNeteaseResp;
import cc.onelooker.kaleido.dto.resp.MusicArtistViewResp;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.service.MusicArtistService;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.third.netease.Artist;
import cc.onelooker.kaleido.third.netease.NeteaseApiService;
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

import java.util.List;

/**
 * 艺术家前端控制器
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */

@Api(tags = "艺术家")
@RestController
@RequestMapping("/musicArtist")
public class MusicArtistController extends AbstractCrudController<MusicArtistDTO> {

    @Autowired
    private MusicArtistService musicArtistService;

    @Autowired
    private MusicManager musicManager;

    @Autowired
    private NeteaseApiService neteaseApiService;

    @Override
    protected IBaseService getService() {
        return musicArtistService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询艺术家")
    public CommonResult<PageResult<MusicArtistPageResp>> page(MusicArtistPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicArtistConvert.INSTANCE::convertToDTO, MusicArtistConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看艺术家详情")
    public CommonResult<MusicArtistViewResp> view(Long id) {
        return super.view(id, MusicArtistConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增艺术家")
    public CommonResult<MusicArtistCreateResp> create(@RequestBody MusicArtistCreateReq req) {
        return super.create(req, MusicArtistConvert.INSTANCE::convertToDTO, MusicArtistConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑艺术家")
    public CommonResult<Boolean> update(@RequestBody MusicArtistUpdateReq req) {
        return super.update(req, MusicArtistConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除艺术家")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping("searchNetease")
    public CommonResult<List<MusicArtistSearchNeteaseResp>> searchNetease(MusicArtistSearchNeteaseReq req) {
        List<Artist> artistList = neteaseApiService.searchArtist(req.getKeywords(), req.getLimit());
        List<MusicArtistSearchNeteaseResp> respList = Lists.newArrayList();
        for (Artist artist : artistList) {
            respList.add(MusicArtistConvert.INSTANCE.convertToSearchNeteaseResp(artist));
        }
        return CommonResult.success(respList);
    }

    @PostMapping("matchNetease")
    public CommonResult<Boolean> matchNetease(@RequestBody MusicArtistMatchNeteaseReq req) {
        return CommonResult.success(musicArtistService.updateNeteaseId(req.getId(), req.getNeteaseId()));
    }

}