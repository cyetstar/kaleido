package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistReleaseService;
import cc.onelooker.kaleido.entity.music.MusicArtistReleaseDO;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.convert.music.MusicArtistReleaseConvert;
import cc.onelooker.kaleido.mapper.music.MusicArtistReleaseMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;

/**
 * 艺术家发行品关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-09-29 22:32:53
 */
@Service
public class MusicArtistReleaseServiceImpl extends KaleidoBaseServiceImpl<MusicArtistReleaseMapper, MusicArtistReleaseDO, MusicArtistReleaseDTO> implements MusicArtistReleaseService {

    MusicArtistReleaseConvert convert = MusicArtistReleaseConvert.INSTANCE;

    @Override
    protected Wrapper<MusicArtistReleaseDO> genQueryWrapper(MusicArtistReleaseDTO dto) {
        LambdaQueryWrapper<MusicArtistReleaseDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getArtistId()), MusicArtistReleaseDO::getArtistId, dto.getArtistId());
        query.eq(Objects.nonNull(dto.getReleaseId()), MusicArtistReleaseDO::getReleaseId, dto.getReleaseId());
        return query;
    }

    @Override
    public MusicArtistReleaseDTO convertToDTO(MusicArtistReleaseDO musicArtistReleaseDO) {
        return convert.convert(musicArtistReleaseDO);
    }

    @Override
    public MusicArtistReleaseDO convertToDO(MusicArtistReleaseDTO musicArtistReleaseDTO) {
        return convert.convertToDO(musicArtistReleaseDTO);
    }
}