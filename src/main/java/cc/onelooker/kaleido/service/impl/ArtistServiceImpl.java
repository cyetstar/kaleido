package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ArtistConvert;
import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.entity.ArtistDO;
import cc.onelooker.kaleido.mapper.ArtistMapper;
import cc.onelooker.kaleido.service.ArtistService;
import cc.onelooker.kaleido.service.MusicAlbumArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private MusicAlbumArtistService musicMusicAlbumArtistService;

    @Override
    protected Wrapper<ArtistDO> genQueryWrapper(ArtistDTO dto) {
        LambdaQueryWrapper<ArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), ArtistDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), ArtistDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), ArtistDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getTitleSort()), ArtistDO::getTitleSort, dto.getTitleSort());
        query.eq(StringUtils.isNotEmpty(dto.getArea()), ArtistDO::getArea, dto.getArea());
        query.like(StringUtils.isNotEmpty(dto.getKeyword()), ArtistDO::getTitle, dto.getKeyword());
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
        List<MusicAlbumArtistDTO> musicMusicAlbumArtistDTOList = musicMusicAlbumArtistService.listByAlbumId(albumId);
        List<ArtistDTO> artistDTOList = Lists.newArrayList();
        for (MusicAlbumArtistDTO musicMusicAlbumArtistDTO : musicMusicAlbumArtistDTOList) {
            ArtistDTO artistDTO = findById(musicMusicAlbumArtistDTO.getArtistId());
            if (artistDTO != null) {
                artistDTOList.add(artistDTO);
            }
        }
        return artistDTOList;
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
    public void updateArtists(List<ArtistDTO> artistDTOList, String albumId) {
        if (artistDTOList == null) {
            return;
        }
        musicMusicAlbumArtistService.deleteByAlbumId(albumId);
        artistDTOList.forEach(s -> {
            MusicAlbumArtistDTO musicAlbumArtistDTO = new MusicAlbumArtistDTO();
            musicAlbumArtistDTO.setAlbumId(albumId);
            if (StringUtils.isEmpty(s.getId())) {
                ArtistDTO artistDTO = findOrSave(s);
                musicAlbumArtistDTO.setArtistId(artistDTO.getId());
            } else {
                musicAlbumArtistDTO.setArtistId(s.getId());
            }
            musicMusicAlbumArtistService.insert(musicAlbumArtistDTO);
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
        } else {
            artistDTO.setTitle(dto.getTitle());
            artistDTO.setThumb(StringUtils.defaultString(dto.getThumb(), artistDTO.getThumb()));
            artistDTO.setNeteaseId(StringUtils.defaultString(dto.getNeteaseId(), artistDTO.getNeteaseId()));
            artistDTO.setMusicbrainzId(StringUtils.defaultString(dto.getMusicbrainzId(), artistDTO.getMusicbrainzId()));
            update(artistDTO);
        }
        return artistDTO;
    }
}