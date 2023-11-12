package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicTrackConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.entity.music.MusicTrackDO;
import cc.onelooker.kaleido.mapper.music.MusicTrackMapper;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistTrackService;
import cc.onelooker.kaleido.service.music.MusicTrackService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 曲目ServiceImpl
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Service
public class MusicTrackServiceImpl extends KaleidoBaseServiceImpl<MusicTrackMapper, MusicTrackDO, MusicTrackDTO> implements MusicTrackService {

    MusicTrackConvert convert = MusicTrackConvert.INSTANCE;

    @Autowired
    private MusicArtistTrackService musicArtistTrackService;

    @Override
    protected Wrapper<MusicTrackDO> genQueryWrapper(MusicTrackDTO dto) {
        LambdaQueryWrapper<MusicTrackDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicTrackDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicTrackDO::getPlexId, dto.getPlexId());
        query.eq(Objects.nonNull(dto.getReleaseId()), MusicTrackDO::getReleaseId, dto.getReleaseId());
        query.eq(StringUtils.isNotEmpty(dto.getBt()), MusicTrackDO::getBt, dto.getBt());
        query.eq(StringUtils.isNotEmpty(dto.getWjgs()), MusicTrackDO::getWjgs, dto.getWjgs());
        query.eq(StringUtils.isNotEmpty(dto.getWjlj()), MusicTrackDO::getWjlj, dto.getWjlj());
        query.eq(StringUtils.isNotEmpty(dto.getSfygc()), MusicTrackDO::getSfygc, dto.getSfygc());
        query.eq(StringUtils.isNotEmpty(dto.getSfqs()), MusicTrackDO::getSfqs, dto.getSfqs());
        return query;
    }

    @Override
    public MusicTrackDTO convertToDTO(MusicTrackDO musicTrackDO) {
        return convert.convert(musicTrackDO);
    }

    @Override
    public MusicTrackDO convertToDO(MusicTrackDTO musicTrackDTO) {
        return convert.convertToDO(musicTrackDTO);
    }

    @Override
    @Transactional
    public MusicTrackDTO insert(MusicTrackDTO dto) {
        List<Long> artistIdList = dto.getArtistIdList();
        dto = super.insert(dto);
        if (artistIdList != null) {
            for (Long artistId : artistIdList) {
                MusicArtistTrackDTO musicArtistTrackDTO = new MusicArtistTrackDTO();
                musicArtistTrackDTO.setTrackId(dto.getId());
                musicArtistTrackDTO.setArtisId(artistId);
                musicArtistTrackService.insert(musicArtistTrackDTO);
            }
        }
        return dto;
    }

    @Override
    public MusicTrackDTO findByPlexId(String plexId) {
        MusicTrackDTO param = new MusicTrackDTO();
        param.setPlexId(plexId);
        return find(param);
    }

    @Override
    public MusicTrackDTO createIfNotExist(String plexMusicLibraryPath, GetMusicTracks.Metadata metadata, MusicReleaseDTO musicReleaseDTO, MusicArtistDTO musicArtistDTO) {
        MusicTrackDTO musicTrackDTO = findByPlexId(metadata.getRatingKey());
        if (musicTrackDTO == null) {
            musicTrackDTO = new MusicTrackDTO();
            musicTrackDTO.setPlexId(metadata.getRatingKey());
            musicTrackDTO.setBt(metadata.getTitle());
            musicTrackDTO.setReleaseId(musicReleaseDTO.getId());
            musicTrackDTO.setCjsj(DateFormatUtils.format(metadata.getAddedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            musicTrackDTO.setXgsj(DateFormatUtils.format(metadata.getUpdatedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            musicTrackDTO.setArtistIdList(Arrays.asList(musicArtistDTO.getId()));
            GetMusicTracks.Media media = metadata.getMedia();
            musicTrackDTO.setWjgs(media.getContainer());
            musicTrackDTO.setWjlj(StringUtils.removeStart(media.getPart().getFile(), plexMusicLibraryPath));
            musicTrackDTO = insert(musicTrackDTO);
        }
        return musicTrackDTO;
    }

    @Override
    public List<MusicTrackDTO> listByReleaseId(Long releaseId) {
        Validate.notNull(releaseId);
        MusicTrackDTO param = new MusicTrackDTO();
        param.setReleaseId(releaseId);
        return list(param);
    }

    public void updateAudioTag(Long id) {
        MusicTrackDTO musicTrackDTO = findById(id);
        try {
            Validate.notEmpty(musicTrackDTO.getWjlj());
            AudioFile audioFile = AudioFileIO.read(Paths.get(musicTrackDTO.getWjlj()).toFile());
            Tag tag = audioFile.getTag();
            musicTrackDTO.setYsj(tag.getFirst(FieldKey.ARTIST));
            musicTrackDTO.setDh(Integer.valueOf(tag.getFirst(FieldKey.DISC_NO)));
            musicTrackDTO.setQh(Integer.valueOf(tag.getFirst(FieldKey.TRACK)));
            musicTrackDTO.setMusicbrainzId(tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID));
            update(musicTrackDTO);
        } catch (Exception e) {
            log.error("");
        }
    }

}