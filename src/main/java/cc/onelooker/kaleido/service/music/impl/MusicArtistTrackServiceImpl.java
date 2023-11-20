package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicArtistTrackConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistTrackDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistTrackDO;
import cc.onelooker.kaleido.mapper.music.MusicArtistTrackMapper;
import cc.onelooker.kaleido.service.music.MusicArtistTrackService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 艺术家曲目关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicArtistTrackServiceImpl extends AbstractBaseServiceImpl<MusicArtistTrackMapper, MusicArtistTrackDO, MusicArtistTrackDTO> implements MusicArtistTrackService {

    MusicArtistTrackConvert convert = MusicArtistTrackConvert.INSTANCE;

    @Override
    protected Wrapper<MusicArtistTrackDO> genQueryWrapper(MusicArtistTrackDTO dto) {
        LambdaQueryWrapper<MusicArtistTrackDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getArtisId()), MusicArtistTrackDO::getArtisId, dto.getArtisId());
        query.eq(Objects.nonNull(dto.getTrackId()), MusicArtistTrackDO::getTrackId, dto.getTrackId());
        return query;
    }

    @Override
    public MusicArtistTrackDTO convertToDTO(MusicArtistTrackDO musicArtistTrackDO) {
        return convert.convert(musicArtistTrackDO);
    }

    @Override
    public MusicArtistTrackDO convertToDO(MusicArtistTrackDTO musicArtistTrackDTO) {
        return convert.convertToDO(musicArtistTrackDTO);
    }
}