package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.plex.resp.GetMusicAlbums;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistReleaseService;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicReleaseService;
import cc.onelooker.kaleido.entity.music.MusicReleaseDO;
import cc.onelooker.kaleido.dto.music.MusicReleaseDTO;
import cc.onelooker.kaleido.convert.music.MusicReleaseConvert;
import cc.onelooker.kaleido.mapper.music.MusicReleaseMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

import org.springframework.transaction.annotation.Transactional;

/**
 * 发行品ServiceImpl
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Service
public class MusicReleaseServiceImpl extends KaleidoBaseServiceImpl<MusicReleaseMapper, MusicReleaseDO, MusicReleaseDTO> implements MusicReleaseService {

    MusicReleaseConvert convert = MusicReleaseConvert.INSTANCE;

    @Autowired
    private MusicArtistReleaseService musicArtistReleaseService;

    @Override
    protected Wrapper<MusicReleaseDO> genQueryWrapper(MusicReleaseDTO dto) {
        LambdaQueryWrapper<MusicReleaseDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicReleaseDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicReleaseDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getBt()), MusicReleaseDO::getBt, dto.getBt());
        query.eq(StringUtils.isNotEmpty(dto.getYsj()), MusicReleaseDO::getYsj, dto.getYsj());
        query.eq(StringUtils.isNotEmpty(dto.getZjlx()), MusicReleaseDO::getZjlx, dto.getZjlx());
        query.eq(StringUtils.isNotEmpty(dto.getYylp()), MusicReleaseDO::getYylp, dto.getYylp());
        query.eq(StringUtils.isNotEmpty(dto.getFxgj()), MusicReleaseDO::getFxgj, dto.getFxgj());
        query.eq(StringUtils.isNotEmpty(dto.getRq()), MusicReleaseDO::getRq, dto.getRq());
        query.eq(StringUtils.isNotEmpty(dto.getCpgs()), MusicReleaseDO::getCpgs, dto.getCpgs());
        query.eq(StringUtils.isNotEmpty(dto.getSfrq()), MusicReleaseDO::getSfrq, dto.getSfrq());
        query.eq(Objects.nonNull(dto.getZds()), MusicReleaseDO::getZds, dto.getZds());
        query.eq(Objects.nonNull(dto.getYgs()), MusicReleaseDO::getYgs, dto.getYgs());
        query.eq(StringUtils.isNotEmpty(dto.getMt()), MusicReleaseDO::getMt, dto.getMt());
        query.eq(StringUtils.isNotEmpty(dto.getSfqs()), MusicReleaseDO::getSfqs, dto.getSfqs());
        query.eq(StringUtils.isNotEmpty(dto.getWjlj()), MusicReleaseDO::getWjlj, dto.getWjlj());
        return query;
    }

    @Override
    public MusicReleaseDTO convertToDTO(MusicReleaseDO musicReleaseDO) {
        return convert.convert(musicReleaseDO);
    }

    @Override
    public MusicReleaseDO convertToDO(MusicReleaseDTO musicReleaseDTO) {
        return convert.convertToDO(musicReleaseDTO);
    }

    @Override
    @Transactional
    public MusicReleaseDTO insert(MusicReleaseDTO dto) {
        List<Long> artistIdList = dto.getArtistIdList();
        dto = super.insert(dto);
        if (artistIdList != null) {
            for (Long artistId : artistIdList) {
                MusicArtistReleaseDTO musicArtistReleaseDTO = new MusicArtistReleaseDTO();
                musicArtistReleaseDTO.setReleaseId(dto.getId());
                musicArtistReleaseDTO.setArtistId(artistId);
                musicArtistReleaseService.insert(musicArtistReleaseDTO);
            }
        }
        return dto;
    }

    @Override
    public MusicReleaseDTO findByPlexId(String plexId) {
        MusicReleaseDTO param = new MusicReleaseDTO();
        param.setPlexId(plexId);
        return find(param);
    }

    @Override
    public MusicReleaseDTO createIfNotExist(GetMusicAlbums.Metadata metadata, MusicArtistDTO musicArtistDTO) {
        MusicReleaseDTO musicReleaseDTO = findByPlexId(metadata.getRatingKey());
        if (musicReleaseDTO == null) {
            musicReleaseDTO = new MusicReleaseDTO();
            musicReleaseDTO.setPlexId(metadata.getRatingKey());
            musicReleaseDTO.setBt(metadata.getTitle());
            musicReleaseDTO.setJj(metadata.getSummary());
            musicReleaseDTO.setArtistIdList(Arrays.asList(musicArtistDTO.getId()));
            musicReleaseDTO.setCjsj(DateFormatUtils.format(metadata.getAddedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            musicReleaseDTO.setXgsj(DateFormatUtils.format(metadata.getUpdatedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            musicReleaseDTO = insert(musicReleaseDTO);
        }
        return musicReleaseDTO;
    }
}