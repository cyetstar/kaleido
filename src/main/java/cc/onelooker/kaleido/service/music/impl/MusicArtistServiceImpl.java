package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.plex.resp.GetMusicArtists;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import com.zjjcnt.common.util.DateTimeUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistService;
import cc.onelooker.kaleido.entity.music.MusicArtistDO;
import cc.onelooker.kaleido.dto.music.MusicArtistDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistConvert;
import cc.onelooker.kaleido.mapper.music.MusicArtistMapper;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import java.lang.Long;
import java.lang.String;

import com.zjjcnt.common.core.annotation.Dict;
import com.zjjcnt.common.core.annotation.StringDateTimeFormat;

/**
 * 艺术家ServiceImpl
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Service
public class MusicArtistServiceImpl extends KaleidoBaseServiceImpl<MusicArtistMapper, MusicArtistDO, MusicArtistDTO> implements MusicArtistService {

    MusicArtistConvert convert = MusicArtistConvert.INSTANCE;

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
            musicArtistDTO.setMc(metadata.getTitle());
            musicArtistDTO.setJj(metadata.getSummary());
            musicArtistDTO.setCjsj(DateFormatUtils.format(metadata.getAddedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            musicArtistDTO.setXgsj(DateFormatUtils.format(metadata.getUpdatedAt() * 1000L, DateTimeUtils.DATETIME_PATTERN));
            insert(musicArtistDTO);
        }
        return musicArtistDTO;
    }
}