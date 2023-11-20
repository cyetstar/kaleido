package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicArtistReleaseConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistReleaseDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistReleaseDO;
import cc.onelooker.kaleido.mapper.music.MusicArtistReleaseMapper;
import cc.onelooker.kaleido.service.KaleidoBaseServiceImpl;
import cc.onelooker.kaleido.service.music.MusicArtistReleaseService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public List<MusicArtistReleaseDTO> listByReleaseId(Long releaseId) {
        Validate.notNull(releaseId);
        MusicArtistReleaseDTO param = new MusicArtistReleaseDTO();
        param.setReleaseId(releaseId);
        return list(param);
    }

    @Override
    public List<MusicArtistReleaseDTO> listByArtistId(Long artistId) {
        Validate.notNull(artistId);
        MusicArtistReleaseDTO param = new MusicArtistReleaseDTO();
        param.setArtistId(artistId);
        return list(param);
    }
}