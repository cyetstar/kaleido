package cc.onelooker.kaleido.web.controller.music;

import cc.onelooker.kaleido.convert.music.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.req.*;
import cc.onelooker.kaleido.dto.music.resp.*;
import cc.onelooker.kaleido.exp.music.MusicAlbumExp;
import cc.onelooker.kaleido.third.netease.NeteaseApiService;
import cc.onelooker.kaleido.third.netease.Album;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import cc.onelooker.kaleido.service.music.MusicManager;
import cc.onelooker.kaleido.utils.ConfigUtils;
import cc.onelooker.kaleido.utils.PlexUtils;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.ExportColumn;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.exception.ServiceException;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.DateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 专辑前端控制器
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */

@Slf4j
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
    private NeteaseApiService neteaseApiService;

    @Autowired
    private PlexApiService plexApiService;

    @Override
    protected IBaseService getService() {
        return musicAlbumService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询专辑")
    public CommonResult<PageResult<MusicAlbumPageResp>> page(MusicAlbumPageReq req, PageParam pageParam) {
        pageParam.setOrderBy("DESC:added_at");
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
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlex() {
        String libraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步资料库信息");
        }
        //获取最后修改时间
        Long maxUpdatedAt = musicAlbumService.findMaxUpdatedAt();
        String libraryPath = plexApiService.getLibraryPath(libraryId);
        List<Metadata> metadataList = maxUpdatedAt == null ? plexApiService.listAlbum(libraryId) : plexApiService.listAlbumByUpdatedAt(libraryId, maxUpdatedAt);
        metadataList.sort(Comparator.comparing(Metadata::getUpdatedAt, Comparator.nullsLast(Comparator.naturalOrder())));
        for (Metadata metadata : metadataList) {
            try {
                musicManager.syncPlexAlbum(libraryPath, metadata);
            } catch (Exception e) {
                log.error("同步资料库失败，错误信息：", e);
            }
        }
        return CommonResult.success(true);
    }

    @PostMapping("syncPlexById")
    @ApiOperation(value = "同步资料库")
    public CommonResult<Boolean> syncPlexById(@RequestBody MusicAlbumSyncPlexReq req) {
        String libraryId = ConfigUtils.getSysConfig("plexMusicLibraryId");
        if (StringUtils.isBlank(libraryId)) {
            throw new ServiceException(2005, "请设置需要同步资料库信息");
        }
        String libraryPath = plexApiService.getLibraryPath(libraryId);
        musicManager.syncPlexAlbumById(libraryPath, req.getId());
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
        List<Album> albumList = neteaseApiService.searchAlbum(req.getKeywords(), req.getLimit());
        List<MusicAlbumSearchNeteaseResp> respList = Lists.newArrayList();
        for (Album album : albumList) {
            MusicAlbumSearchNeteaseResp resp = MusicAlbumConvert.INSTANCE.convertToSearchNeteaseResp(album);
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
    public CommonResult<List<MusicAlbumListByArtistIdResp>> listByArtistId(Long artistId) {
        List<MusicAlbumDTO> musicAlbumDTOList = musicAlbumService.listByArtistId(artistId);
        List<MusicAlbumListByArtistIdResp> respList = Lists.newArrayList();
        for (MusicAlbumDTO musicAlbumDTO : musicAlbumDTOList) {
            MusicAlbumListByArtistIdResp resp = MusicAlbumConvert.INSTANCE.convertToListByArtistIdResp(musicAlbumDTO);
            respList.add(resp);
        }
        return CommonResult.success(respList);
    }

    @GetMapping("viewPath")
    @ApiOperation(value = "获取目录")
    public CommonResult<String> viewPath(Long id) {
        List<Metadata> metadataList = plexApiService.listTrackByAlbumId(id);
        Metadata metadata = metadataList.get(0);
        String folder = PlexUtils.getMusicFolder(metadata.getMedia().getPart().getFile());
        return CommonResult.success(folder);
    }

    @PostMapping("uploadCover")
    @ApiOperation(value = "上传封面")
    public CommonResult<Boolean> uploadCover(MusicAlbumUploadCoverReq req) throws IOException {
        Files.write(Paths.get(req.getPath(), "cover.jpg"), req.getFile().getBytes());
        plexApiService.refresAlbumById(req.getId());
        return CommonResult.success(true);
    }

}