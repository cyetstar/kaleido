package cc.onelooker.kaleido.web.controller;

import cc.onelooker.kaleido.convert.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.req.*;
import cc.onelooker.kaleido.dto.resp.*;
import cc.onelooker.kaleido.service.ArtistService;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicManager;
import cc.onelooker.kaleido.third.plex.Metadata;
import cc.onelooker.kaleido.third.plex.PlexApiService;
import cc.onelooker.kaleido.third.tmm.Album;
import cc.onelooker.kaleido.third.tmm.Song;
import cc.onelooker.kaleido.third.tmm.TmmApiService;
import cc.onelooker.kaleido.utils.AudioTagUtil;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.domain.CommonResult;
import com.zjjcnt.common.core.domain.PageParam;
import com.zjjcnt.common.core.domain.PageResult;
import com.zjjcnt.common.core.service.IBaseService;
import com.zjjcnt.common.core.web.controller.AbstractCrudController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private ArtistService artistService;

    @Autowired
    private MusicManager musicManager;

    @Autowired
    private TmmApiService tmmApiService;

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
    public CommonResult<MusicAlbumViewResp> view(String id) {
        List<ArtistDTO> artistDTOList = artistService.listByAlbumId(id);
        MusicAlbumViewResp musicAlbumViewResp = doView(id, MusicAlbumConvert.INSTANCE::convertToViewResp);
        musicAlbumViewResp.setArtistDTOList(artistDTOList);
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
    public CommonResult<Boolean> delete(@RequestBody String[] id) {
        return super.delete(id);
    }

    @GetMapping("searchInfo")
    public CommonResult<List<MusicAlbumSearchInfoResp>> searchInfo(MusicAlbumSearchInfoReq req) {
        List<Album> albumList = tmmApiService.searchAlbum(req.getKeyword(), req.getSource());
        List<MusicAlbumSearchInfoResp> respList = Lists.newArrayList();
        for (Album album : albumList) {
            MusicAlbumSearchInfoResp resp = MusicAlbumConvert.INSTANCE.convertToSearchInfoResp(album);
            resp.setPublishTime(album.getPublishTime());
            respList.add(resp);
        }
        return CommonResult.success(respList);
    }

    @PostMapping("viewMatchInfo")
    public CommonResult<List<MusicAlbumViewMatchInfoResp>> viewMatchInfo(@RequestBody MusicAlbumViewMatchInfoReq req) {
        Album album = tmmApiService.findAlbum(req.getNeteaseId(), req.getMusicbrainzId());
        List<MusicAlbumViewMatchInfoResp> result = null;
        try {
            Path path = null;
            if (StringUtils.isEmpty(req.getPath())) {
                MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(req.getId());
                path = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath());
            } else {
                path = Paths.get(req.getPath());
            }
            result = Files.list(path).filter(s -> KaleidoUtil.isAudioFile(s.getFileName().toString())).map(s -> {
                MusicAlbumViewMatchInfoResp resp = new MusicAlbumViewMatchInfoResp();
                try {
                    AudioFile audioFile = AudioFileIO.read(s.toFile());
                    Tag tag = audioFile.getTag();
                    AudioTagUtil.AudioTag audioTag = AudioTagUtil.toAudioTag(tag);
                    Integer trackIndex = null;
                    resp.setTitle(audioTag.getTitle());
                    resp.setArtistName(audioTag.getArtist());
                    resp.setDuration(audioFile.getAudioHeader().getTrackLength());
                    resp.setPublishTime(audioTag.getReleaseYear());
                    if (StringUtils.isNumeric(audioTag.getTrackIndex())) {
                        trackIndex = Integer.parseInt(audioTag.getTrackIndex());
                    } else {
                        trackIndex = KaleidoUtil.parseTrackIndex(s.getFileName().toString());
                    }
                    resp.setTrackIndex(trackIndex);
                    Song song = album.getSong(trackIndex);
                    if (song != null) {
                        resp.setSongTitle(song.getTitle());
                        resp.setSongArtistName(song.getArtistName());
                        resp.setSongDuration(song.getDuration());
                        resp.setSongTrackIndex(song.getTrackIndex());
                        resp.setSongPublishTime(album.getPublishTime());
                    }
                } catch (Exception e) {
                    ExceptionUtil.wrapAndThrow(e);
                }
                return resp;
            }).sorted(Comparator.comparing(MusicAlbumViewMatchInfoResp::getTrackIndex)).collect(Collectors.toList());
        } catch (IOException e) {
            ExceptionUtil.wrapAndThrow(e);
        }
        return CommonResult.success(result);
    }

    @PostMapping("matchInfo")
    public CommonResult<Boolean> matchInfo(@RequestBody MusicAlbumMatchInfoReq req) {
        if (StringUtils.equals(req.getMatchType(), "path")) {
            Album album = new Album();
            album.setNeteaseId(req.getNeteaseId());
            album.setMusicbrainzId(req.getMusicbrainzId());
            album.setTitle(req.getTitle());
            musicManager.matchPath(Paths.get(req.getPath()), album);
        } else {
            Album album = tmmApiService.findAlbum(req.getNeteaseId(), req.getMusicbrainzId());
            musicManager.matchInfo(req.getId(), album);
        }
        return CommonResult.success(true);
    }

    @GetMapping("listByArtistId")
    public CommonResult<List<MusicAlbumListByArtistIdResp>> listByArtistId(String artistId) {
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
    public CommonResult<String> viewPath(String id) {
        MusicAlbumDTO musicAlbumDTO = musicAlbumService.findById(id);
        Path filePath = KaleidoUtil.getMusicPath(musicAlbumDTO.getPath());
        return CommonResult.success(filePath.toString());
    }

    @PostMapping("downloadCover")
    public CommonResult<Boolean> downloadCover(@RequestBody MusicAlbumDownloadCoverReq req) {
        List<Metadata> metadataList = plexApiService.listTrackByAlbumId(req.getId());
        Metadata metadata = metadataList.get(0);
        Path filePath = KaleidoUtil.getMusicPath(metadata.getMedia().getPart().getFile());
        File file = filePath.resolveSibling("cover.jpg").toFile();
        HttpUtil.downloadFile(req.getUrl(), file);
        return CommonResult.success(true);
    }

}