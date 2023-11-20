package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicTrackConvert;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO;
import cc.onelooker.kaleido.dto.music.MusicTrackDTO;
import cc.onelooker.kaleido.entity.music.MusicTrackDO;
import cc.onelooker.kaleido.mapper.music.MusicTrackMapper;
import cc.onelooker.kaleido.plex.resp.GetMusicTracks;
import cc.onelooker.kaleido.service.music.MusicArtistTrackService;
import cc.onelooker.kaleido.service.music.MusicTrackService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 曲目ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicTrackServiceImpl extends AbstractBaseServiceImpl<MusicTrackMapper, MusicTrackDO, MusicTrackDTO> implements MusicTrackService {

    MusicTrackConvert convert = MusicTrackConvert.INSTANCE;

    @Autowired
    private MusicArtistTrackService musicArtistTrackService;

    @Override
    protected Wrapper<MusicTrackDO> genQueryWrapper(MusicTrackDTO dto) {
        LambdaQueryWrapper<MusicTrackDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getAlbumId()), MusicTrackDO::getAlbumId, dto.getAlbumId());
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicTrackDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicTrackDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), MusicTrackDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MusicTrackDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getArtists()), MusicTrackDO::getArtists, dto.getArtists());
        query.eq(Objects.nonNull(dto.getLength()), MusicTrackDO::getLength, dto.getLength());
        query.eq(Objects.nonNull(dto.getTrackNumber()), MusicTrackDO::getTrackNumber, dto.getTrackNumber());
        query.eq(Objects.nonNull(dto.getDiscNumber()), MusicTrackDO::getDiscNumber, dto.getDiscNumber());
        query.eq(StringUtils.isNotEmpty(dto.getFormat()), MusicTrackDO::getFormat, dto.getFormat());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), MusicTrackDO::getPath, dto.getPath());
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
    public MusicTrackDTO createIfNotExist(String libraryPath, GetMusicTracks.Metadata metadata, MusicAlbumDTO musicAlbumDTO, MusicArtistDTO musicArtistDTO) {
        MusicTrackDTO musicTrackDTO = findByPlexId(metadata.getRatingKey());
        if (musicTrackDTO == null) {
            musicTrackDTO = new MusicTrackDTO();
            musicTrackDTO.setPlexId(metadata.getRatingKey());
            musicTrackDTO.setTitle(metadata.getTitle());
            musicTrackDTO.setAlbumId(musicAlbumDTO.getId());
            musicTrackDTO.setCjsj(DateTimeUtils.parseTimestamp(metadata.getAddedAt() * 1000L));
            musicTrackDTO.setXgsj(DateTimeUtils.parseTimestamp(metadata.getUpdatedAt() * 1000L));
            musicTrackDTO.setArtistIdList(Arrays.asList(musicArtistDTO.getId()));
            GetMusicTracks.Media media = metadata.getMedia();
            musicTrackDTO.setFormat(media.getContainer());
            musicTrackDTO.setPath(StringUtils.removeStart(media.getPart().getFile(), libraryPath));
            musicTrackDTO = insert(musicTrackDTO);
        }
        return musicTrackDTO;
    }

    @Override
    public List<MusicTrackDTO> listByAlbumId(Long releaseId) {
        Validate.notNull(releaseId);
        MusicTrackDTO param = new MusicTrackDTO();
        param.setAlbumId(releaseId);
        return list(param);
    }

}