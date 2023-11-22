package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicArtistConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistDO;
import cc.onelooker.kaleido.mapper.music.MusicArtistMapper;
import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.service.music.MusicArtistAlbumService;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 艺术家ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicArtistServiceImpl extends AbstractBaseServiceImpl<MusicArtistMapper, MusicArtistDO, MusicArtistDTO> implements MusicArtistService {

    MusicArtistConvert convert = MusicArtistConvert.INSTANCE;

    @Autowired
    private MusicArtistAlbumService musicArtistAlbumService;

    @Override
    protected Wrapper<MusicArtistDO> genQueryWrapper(MusicArtistDTO dto) {
        LambdaQueryWrapper<MusicArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicArtistDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicArtistDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexThumb()), MusicArtistDO::getPlexThumb, dto.getPlexThumb());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), MusicArtistDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getName()), MusicArtistDO::getName, dto.getName());
        query.eq(StringUtils.isNotEmpty(dto.getArea()), MusicArtistDO::getArea, dto.getArea());
        query.eq(Objects.nonNull(dto.getSummary()), MusicArtistDO::getSummary, dto.getSummary());
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
            musicArtistDTO.setName(metadata.getTitle());
            musicArtistDTO.setSummary(metadata.getSummary());
            musicArtistDTO.setCjsj(metadata.getStringAddedAt());
            insert(musicArtistDTO);
        }
        return musicArtistDTO;
    }

    @Override
    public List<MusicArtistDTO> listByAlbumId(Long albumId) {
        List<MusicArtistAlbumDTO> musicArtistAlbumDTOList = musicArtistAlbumService.listByAlbumId(albumId);
        List<MusicArtistDTO> musicArtistDTOList = Lists.newArrayList();
        for (MusicArtistAlbumDTO musicArtistAlbumDTO : musicArtistAlbumDTOList) {
            MusicArtistDTO musicArtistDTO = findById(musicArtistAlbumDTO.getArtistId());
            if (musicArtistDTO != null) {
                musicArtistDTOList.add(musicArtistDTO);
            }
        }
        return musicArtistDTOList;
    }
}