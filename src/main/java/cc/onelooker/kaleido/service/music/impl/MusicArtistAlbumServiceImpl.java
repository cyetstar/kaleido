package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicArtistAlbumConvert;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.entity.music.MusicArtistAlbumDO;
import cc.onelooker.kaleido.mapper.music.MusicArtistAlbumMapper;
import cc.onelooker.kaleido.service.music.MusicArtistAlbumService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 艺术家专辑关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicArtistAlbumServiceImpl extends AbstractBaseServiceImpl<MusicArtistAlbumMapper, MusicArtistAlbumDO, MusicArtistAlbumDTO> implements MusicArtistAlbumService {

    MusicArtistAlbumConvert convert = MusicArtistAlbumConvert.INSTANCE;

    @Override
    protected Wrapper<MusicArtistAlbumDO> genQueryWrapper(MusicArtistAlbumDTO dto) {
        LambdaQueryWrapper<MusicArtistAlbumDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getArtistId()), MusicArtistAlbumDO::getArtistId, dto.getArtistId());
        query.eq(Objects.nonNull(dto.getAlbumId()), MusicArtistAlbumDO::getAlbumId, dto.getAlbumId());
        return query;
    }

    @Override
    public MusicArtistAlbumDTO convertToDTO(MusicArtistAlbumDO musicArtistAlbumDO) {
        return convert.convert(musicArtistAlbumDO);
    }

    @Override
    public MusicArtistAlbumDO convertToDO(MusicArtistAlbumDTO musicArtistAlbumDTO) {
        return convert.convertToDO(musicArtistAlbumDTO);
    }

    @Override
    public List<MusicArtistAlbumDTO> listByAlbumId(Long albumId) {
        Validate.notNull(albumId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setAlbumId(albumId);
        return list(param);
    }

    @Override
    public List<MusicArtistAlbumDTO> listByArtistId(Long artistId) {
        Validate.notNull(artistId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setArtistId(artistId);
        return list(param);
    }
}