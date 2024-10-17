package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ArtistConvert;
import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.dto.MusicTrackArtistDTO;
import cc.onelooker.kaleido.entity.ArtistDO;
import cc.onelooker.kaleido.mapper.ArtistMapper;
import cc.onelooker.kaleido.service.ArtistService;
import cc.onelooker.kaleido.service.MusicAlbumArtistService;
import cc.onelooker.kaleido.service.MusicTrackArtistService;
import cc.onelooker.kaleido.utils.KaleidoUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 艺术家ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class ArtistServiceImpl extends AbstractBaseServiceImpl<ArtistMapper, ArtistDO, ArtistDTO> implements ArtistService {

    ArtistConvert convert = ArtistConvert.INSTANCE;

    @Autowired
    private MusicAlbumArtistService musicAlbumArtistService;

    @Autowired
    private MusicTrackArtistService musicTrackArtistService;

    @Override
    protected Wrapper<ArtistDO> genQueryWrapper(ArtistDTO dto) {
        LambdaQueryWrapper<ArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), ArtistDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), ArtistDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ArtistDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getSortTitle()), ArtistDO::getSortTitle, dto.getSortTitle());
        query.eq(StringUtils.isNotEmpty(dto.getArea()), ArtistDO::getArea, dto.getArea());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), ArtistDO::getTitle, dto.getKeyword());
        query.in(CollectionUtils.isNotEmpty(dto.getIdList()), ArtistDO::getId, dto.getIdList());
        return query;
    }

    @Override
    public ArtistDTO convertToDTO(ArtistDO artistDO) {
        return convert.convert(artistDO);
    }

    @Override
    public ArtistDO convertToDO(ArtistDTO artistDTO) {
        return convert.convertToDO(artistDTO);
    }

    @Override
    public List<ArtistDTO> listByAlbumId(String albumId) {
        List<MusicAlbumArtistDTO> musicAlbumArtistDTOList = musicAlbumArtistService.listByAlbumId(albumId);
        List<ArtistDTO> artistDTOList = Lists.newArrayList();
        for (MusicAlbumArtistDTO musicAlbumArtistDTO : musicAlbumArtistDTOList) {
            ArtistDTO artistDTO = findById(musicAlbumArtistDTO.getArtistId());
            if (artistDTO != null) {
                artistDTOList.add(artistDTO);
            }
        }
        return artistDTOList;
    }

    @Override
    public List<ArtistDTO> listByTrackId(String trackId) {
        List<MusicTrackArtistDTO> musicTrackArtistDTOList = musicTrackArtistService.listByTrackId(trackId);
        List<ArtistDTO> artistDTOList = Lists.newArrayList();
        for (MusicTrackArtistDTO musicTrackArtistDTO : musicTrackArtistDTOList) {
            ArtistDTO artistDTO = findById(musicTrackArtistDTO.getArtistId());
            if (artistDTO != null) {
                artistDTOList.add(artistDTO);
            }
        }
        return artistDTOList;
    }

    public Map<String, List<ArtistDTO>> mapByTrackIdList(List<String> trackIdList) {
        Map<String, ArtistDTO> cacheContext = Maps.newHashMap();
        List<MusicTrackArtistDTO> musicTrackArtistDTOList = musicTrackArtistService.listByTrackIdList(trackIdList);
        return musicTrackArtistDTOList.stream().map(s -> {
            ArtistDTO artistDTO = cacheContext.get(s.getArtistId());
            if (artistDTO == null) {
                artistDTO = findById(s.getArtistId());
                cacheContext.put(s.getArtistId(), artistDTO);
            }
            artistDTO.setTrackId(s.getTrackId());
            return artistDTO;
        }).collect(Collectors.groupingBy(ArtistDTO::getTrackId));
    }

    @Override
    public ArtistDTO findByNeteaseId(String neteaseId) {
        Validate.notEmpty(neteaseId);
        ArtistDTO param = new ArtistDTO();
        param.setNeteaseId(neteaseId);
        return find(param);
    }

    @Override
    public ArtistDTO findByMusicbrainzId(String musicbrainzId) {
        Validate.notEmpty(musicbrainzId);
        ArtistDTO param = new ArtistDTO();
        param.setMusicbrainzId(musicbrainzId);
        return find(param);
    }

    @Override
    public ArtistDTO findByTitle(String title) {
        Validate.notEmpty(title);
        ArtistDTO param = new ArtistDTO();
        param.setTitle(title);
        return find(param);
    }

    @Override
    @Transactional
    public Boolean updateNeteaseId(String id, String neteaseId) {
        ArtistDO artistDO = new ArtistDO();
        artistDO.setId(id);
        artistDO.setNeteaseId(neteaseId);
        return SqlHelper.retBool(baseMapper.updateById(artistDO));
    }

    @Override
    @Transactional
    public void updateAlbumArtists(List<ArtistDTO> artistDTOList, String albumId) {
        if (artistDTOList == null) {
            return;
        }
        musicAlbumArtistService.deleteByAlbumId(albumId);
        artistDTOList.forEach(s -> {
            MusicAlbumArtistDTO musicAlbumArtistDTO = new MusicAlbumArtistDTO();
            musicAlbumArtistDTO.setAlbumId(albumId);
            if (StringUtils.isEmpty(s.getId())) {
                ArtistDTO artistDTO = findOrSave(s);
                musicAlbumArtistDTO.setArtistId(artistDTO.getId());
            } else {
                musicAlbumArtistDTO.setArtistId(s.getId());
            }
            musicAlbumArtistService.insert(musicAlbumArtistDTO);
        });

    }

    @Override
    @Transactional
    public void updateTrackArtists(List<ArtistDTO> artistDTOList, String trackId) {
        if (artistDTOList == null) {
            return;
        }
        musicTrackArtistService.deleteByTrackId(trackId);
        artistDTOList.forEach(s -> {
            MusicTrackArtistDTO musicTrackArtistDTO = new MusicTrackArtistDTO();
            musicTrackArtistDTO.setTrackId(trackId);
            if (StringUtils.isEmpty(s.getId())) {
                ArtistDTO artistDTO = findOrSave(s);
                musicTrackArtistDTO.setArtistId(artistDTO.getId());
            } else {
                musicTrackArtistDTO.setArtistId(s.getId());
            }
            musicTrackArtistService.insert(musicTrackArtistDTO);
        });
    }

    private ArtistDTO findOrSave(ArtistDTO dto) {
        ArtistDTO artistDTO = null;
        if (StringUtils.isNotEmpty(dto.getNeteaseId())) {
            artistDTO = findByNeteaseId(dto.getNeteaseId());
        }
        if (artistDTO == null && StringUtils.isNotEmpty(dto.getMusicbrainzId())) {
            artistDTO = findByMusicbrainzId(dto.getMusicbrainzId());
        }
        if (artistDTO == null && StringUtils.isNotEmpty(dto.getTitle())) {
            artistDTO = findByTitle(dto.getTitle());
        }
        if (artistDTO == null) {
            artistDTO = new ArtistDTO();
            artistDTO.setTitle(dto.getTitle());
            artistDTO.setSummary(dto.getSummary());
            artistDTO.setThumb(dto.getThumb());
            artistDTO.setNeteaseId(dto.getNeteaseId());
            artistDTO.setMusicbrainzId(dto.getMusicbrainzId());
            artistDTO = insert(artistDTO);
        } else if (isNeedUpdate(artistDTO, dto)) {
            artistDTO.setTitle(dto.getTitle());
            artistDTO.setThumb(StringUtils.defaultString(dto.getThumb(), artistDTO.getThumb()));
            artistDTO.setNeteaseId(StringUtils.defaultString(dto.getNeteaseId(), artistDTO.getNeteaseId()));
            artistDTO.setMusicbrainzId(StringUtils.defaultString(dto.getMusicbrainzId(), artistDTO.getMusicbrainzId()));
            update(artistDTO);
        }
        return artistDTO;
    }

    private boolean isNeedUpdate(ArtistDTO dto1, ArtistDTO dto2) {
        if (KaleidoUtil.isOverride(dto1.getTitle(), dto2.getTitle()) || KaleidoUtil.isOverride(dto1.getNeteaseId(), dto2.getNeteaseId()) || KaleidoUtil.isOverride(dto1.getMusicbrainzId(), dto2.getMusicbrainzId()) || KaleidoUtil.isOverride(dto1.getThumb(), dto2.getThumb())) {
            return true;
        }
        return false;
    }
}