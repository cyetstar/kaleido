package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicArtistConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistDO;
import cc.onelooker.kaleido.mapper.music.MusicArtistMapper;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistReleaseService;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 艺术家ServiceImpl
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Service
public class MusicArtistServiceImpl extends KaleidoBaseServiceImpl<MusicArtistMapper, MusicArtistDO, MusicArtistDTO> implements MusicArtistService {

    MusicArtistConvert convert = MusicArtistConvert.INSTANCE;

    @Autowired
    private MusicArtistReleaseService musicArtistReleaseService;

    @Override
    protected Wrapper<MusicArtistDO> genQueryWrapper(MusicArtistDTO dto) {
        LambdaQueryWrapper<MusicArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicArtistDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicArtistDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getMc()), MusicArtistDO::getMc, dto.getMc());
        query.eq(StringUtils.isNotEmpty(dto.getYsjlx()), MusicArtistDO::getYsjlx, dto.getYsjlx());
        query.eq(StringUtils.isNotEmpty(dto.getGjdq()), MusicArtistDO::getGjdq, dto.getGjdq());
        query.eq(StringUtils.isNotEmpty(dto.getJj()), MusicArtistDO::getJj, dto.getJj());
        return query;
    }

    @Override
    public MusicArtistDTO convertToDTO(MusicArtistDO musicArtistDO) {
        return convert.convert(musicArtistDO);
    }

    @Override
    public MusicArtistDO convertToDO(MusicArtistDTO musicArtistDTO) {
        return convert.convertToDO(musicArtistDTO);
    }

    @Override
    public MusicArtistDTO findByPlexId(String plexId) {
        Validate.notEmpty(plexId);
        MusicArtistDTO param = new MusicArtistDTO();
        param.setPlexId(plexId);
        return find(param);
    }

    @Override
    public MusicArtistDTO createIfNotExist(GetMusicArtists.Metadata metadata) {
        MusicArtistDTO musicArtistDTO = findByPlexId(metadata.getRatingKey());
        if (musicArtistDTO == null) {
            musicArtistDTO = new MusicArtistDTO();
            musicArtistDTO.setPlexId(metadata.getRatingKey());
            musicArtistDTO.setPlexThumb(metadata.getThumb());
            musicArtistDTO.setMc(metadata.getTitle());
            musicArtistDTO.setJj(metadata.getSummary());
            musicArtistDTO.setCjsj(DateTimeUtils.parseTimestamp(metadata.getAddedAt() * 1000L));
            insert(musicArtistDTO);
        }
        return musicArtistDTO;
    }

    @Override
    public List<MusicArtistDTO> listByReleaseId(Long releaseId) {
        List<MusicArtistReleaseDTO> musicArtistReleaseDTOList = musicArtistReleaseService.listByReleaseId(releaseId);
        List<MusicArtistDTO> musicArtistDTOList = Lists.newArrayList();
        for (MusicArtistReleaseDTO musicArtistReleaseDTO : musicArtistReleaseDTOList) {
            MusicArtistDTO musicArtistDTO = findById(musicArtistReleaseDTO.getArtistId());
            if (musicArtistDTO != null) {
                musicArtistDTOList.add(musicArtistDTO);
            }
        }
        return musicArtistDTOList;
    }
}