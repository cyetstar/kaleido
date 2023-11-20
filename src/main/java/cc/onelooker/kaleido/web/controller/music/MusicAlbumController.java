package cc.onelooker.kaleido.web.controller.music;

import cc.onelooker.kaleido.convert.music.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicAlbumExp;
import cc.onelooker.kaleido.netease.NeteaseApiService;
import cc.onelooker.kaleido.netease.domain.Album;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import cc.onelooker.kaleido.service.music.MusicManager;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 专辑前端控制器
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */

@Api(tags = "专辑")
@RestController
@RequestMapping("/musicAlbum")
public class MusicAlbumController extends AbstractCrudController<MusicAlbumDTO> {

    @Autowired
    private MusicAlbumService musicAlbumService;

    @Autowired
    private MusicArtistService musicArtistService;

    @Autowired
    private MusicManager musicManager;

    @Autowired
    private NeteaseApiService neteaseMusicApiService;

    @Override
    protected IBaseService getService() {
        return musicAlbumService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询专辑")
    public CommonResult<PageResult<MusicAlbumPageResp>> page(MusicAlbumPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicAlbumConvert.INSTANCE::convertToDTO, MusicAlbumConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看专辑详情")
    public CommonResult<MusicAlbumViewResp> view(Long id) {
        List<MusicArtistDTO> musicArtistDTOList = musicArtistService.listByAlbumId(id);
        MusicAlbumViewResp musicAlbumViewResp = doView(id, MusicAlbumConvert.INSTANCE::convertToViewResp);
        musicAlbumViewResp.setMusicArtistDTOList(musicArtistDTOList);
        return CommonResult.success(musicAlbumViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增专辑")
    public CommonResult<MusicAlbumCreateResp> create(@RequestBody MusicAlbumCreateReq req) {
        return super.create(req, MusicAlbumConvert.INSTANCE::convertToDTO, MusicAlbumConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑专辑")
    public CommonResult<Boolean> update(@RequestBody MusicAlbumUpdateReq req) {
        return super.update(req, MusicAlbumConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除专辑")
    public CommonResult<Boolean> delete(@RequestBody Long[] id) {
        return super.delete(id);
    }

    @GetMapping(value = "/column")
    @ApiOperation(value = "查询可导出列")
    public CommonResult<List<ExportColumn>> column() {
        List<ExportColumn> exportColumns = getColumns(MusicAlbumExp.class);
        return CommonResult.success(exportColumns);
    }

    @GetMapping("export")
    @ApiOperation(value = "导出专辑")
    public void export(MusicAlbumPageReq req, String[] columns, PageParam pageParam, HttpServletResponse response) {
        String filename = "专辑" + DateTimeUtils.now() + ".xlsx";
        super.export(req, columns, pageParam, filename, MusicAlbumExp.class, MusicAlbumConvert.INSTANCE::convertToDTO, MusicAlbumConvert.INSTANCE::convertToExp, response);
    }

    @PostMapping("syncPlex")
    @ApiOperation(value = "同步音乐库")
    public CommonResult<Boolean> syncPlex() {
        musicManager.syncPlex();
        return CommonResult.success(true);
    }

    //读取封面文件，输出字节流
    @PostMapping("updateAudioTag")
    public CommonResult<Boolean> updateAudioTag(@RequestBody MusicAlbumUpdateAudioTagReq req) {
        int error = musicManager.updateAudioTag(req.getId());
        return CommonResult.success(error == 0);
    }

    @GetMapping("searchNetease")
    public CommonResult<List<MusicAlbumSearchNeteaseResp>> searchNetease(MusicAlbumSearchNeteaseReq req) {
        List<Album> albumList = neteaseMusicApiService.searchAlbum(req.getKeywords(), req.getLimit());
        List<MusicAlbumSearchNeteaseResp> respList = Lists.newArrayList();
        for (Album album : albumList) {
            MusicAlbumSearchNeteaseResp resp = MusicAlbumConvert.INSTANCE.convertToSearchAlbumResp(album);
            resp.setPublishTime(DateTimeUtils.parseTimestamp(album.getPublishTime()));
            respList.add(resp);
        }
        return CommonResult.success(respList);
    }

    @PostMapping("matchNetease")
    public CommonResult<Boolean> matchNetease(@RequestBody MusicAlbumMatchNeteaseReq req) {
        musicManager.matchNetease(req.getId(), req.getNeteaseId());
        return CommonResult.success(true);
    }

    @PostMapping("downloadLyric")
    public CommonResult<Boolean> downloadLyric(@RequestBody MusicAlbumUpdateLyricReq req) {
        int error = musicManager.downloadLyric(req.getId());
        return CommonResult.success(error == 0);
    }

    @GetMapping("listByArtistId")
    @ApiOperation(value = "查询发行品")
    public CommonResult<List<MusicAlbumListByArtistIdResp>> listByArtistId(Long artistId) {
        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.listByArtistId(artistId);
        List<MusicAlbumListByArtistIdResp> respList = Lists.newArrayList();
        for (MusicAlbumDTO musicAlbumDTO : musicAlbumDTOList) {
            MusicAlbumListByArtistIdResp resp = MusicAlbumConvert.INSTANCE.convertToListByArtistIdResp(musicAlbumDTO);
            respList.add(resp);
        }
        return CommonResult.success(respList);
    }

}