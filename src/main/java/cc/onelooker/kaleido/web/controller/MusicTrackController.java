package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MusicTrackConvert;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.dto.req.MusicTrackCreateReq;
import cc.onelooker.kaleido.dto.req.MusicTrackDownloadLyricReq;
import cc.onelooker.kaleido.dto.req.MusicTrackPageReq;
import cc.onelooker.kaleido.dto.req.MusicTrackUpdateReq;
import cc.onelooker.kaleido.dto.resp.MusicTrackCreateResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackListByAlbumIdResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackPageResp;
import cc.onelooker.kaleido.dto.resp.MusicTrackViewResp;
import cc.onelooker.kaleido.enums.ConfigKey;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.service.MusicTrackService;
import cc.onelooker.kaleido.utils.ConfigUtils;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import com.zjjcnt.common.util.constant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 曲目前端控制器
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */

@Api(tags = "曲目")
@RestController
@RequestMapping("/musicTrack")
public class MusicTrackController extends AbstractCrudController<MusicTrackDTO> {

    @Autowired
    private MusicTrackService musicTrackService;

    @Autowired
    private MusicManager musicManager;

    @Override
    protected IBaseService getService() {
        return musicTrackService;
    }

    @GetMapping("page")
    @ApiOperation(value = "查询曲目")
    public CommonResult<PageResult<MusicTrackPageResp>> page(MusicTrackPageReq req, PageParam pageParam) {
        return super.page(req, pageParam, MusicTrackConvert.INSTANCE::convertToDTO, MusicTrackConvert.INSTANCE::convertToPageResp);
    }

    @GetMapping("view")
    @ApiOperation(value = "查看曲目详情")
    public CommonResult<MusicTrackViewResp> view(String id) {
        return super.view(id, MusicTrackConvert.INSTANCE::convertToViewResp);
    }

    @PostMapping("create")
    @ApiOperation(value = "新增曲目")
    public CommonResult<MusicTrackCreateResp> create(@RequestBody MusicTrackCreateReq req) {
        return super.create(req, MusicTrackConvert.INSTANCE::convertToDTO, MusicTrackConvert.INSTANCE::convertToCreateResp);
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑曲目")
    public CommonResult<Boolean> update(@RequestBody MusicTrackUpdateReq req) {
        return super.update(req, MusicTrackConvert.INSTANCE::convertToDTO);
    }

    @DeleteMapping(value = "delete")
    @ApiOperation(value = "删除曲目")
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @GetMapping("listByAlbumId")
    public CommonResult<Collection<List<MusicTrackListByAlbumIdResp>>> listByAlbumId(String albumId) {
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByAlbumId(albumId);
        String musicLibraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        List<MusicTrackListByAlbumIdResp> respList = Lists.newArrayList();
        for (MusicTrackDTO musicTrackDTO : musicTrackDTOList) {
            File file = Paths.get(musicLibraryPath, FilenameUtils.removeExtension(musicTrackDTO.getPath()) + ".lrc").toFile();
            MusicTrackListByAlbumIdResp resp = MusicTrackConvert.INSTANCE.convertToListByAlbumIdResp(musicTrackDTO);
            resp.setHasLyric(file.exists() && file.length() > 0 ? Constants.YES : Constants.NO);
            respList.add(resp);
        }
        Map<Optional<Integer>, List<MusicTrackListByAlbumIdResp>> result = respList.stream().collect(Collectors.groupingBy(s -> Optional.ofNullable(s.getDiscIndex())));
        return CommonResult.success(result.values());
    }

    @GetMapping("viewLyric")
    public CommonResult<List<String>> viewLyric(String id) throws IOException {
        MusicTrackDTO musicTrackDTO = musicTrackService.findById(id);
        String musicLibraryPath = ConfigUtils.getSysConfig(ConfigKey.musicLibraryPath);
        File file = Paths.get(musicLibraryPath, FilenameUtils.removeExtension(musicTrackDTO.getPath()) + ".lrc").toFile();
        String content = FileUtils.readFileToString(file);
        List<String> result = Arrays.asList(StringUtils.split(content, "\n"));
        return CommonResult.success(result);
    }

    @PostMapping("downloadLyric")
    public CommonResult<Boolean> downloadLyric(@RequestBody MusicTrackDownloadLyricReq req) {
        musicManager.downloadTrackLyric(req.getId(), req.getNeteaseId());
        return CommonResult.success(true);
    }

}