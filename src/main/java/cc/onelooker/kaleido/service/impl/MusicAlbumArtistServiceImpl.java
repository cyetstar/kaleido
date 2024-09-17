package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MusicAlbumArtistConvert;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.entity.MusicAlbumArtistDO;
import cc.onelooker.kaleido.mapper.MusicAlbumArtistMapper;
import cc.onelooker.kaleido.service.MusicAlbumArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 艺术家专辑关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicAlbumArtistServiceImpl extends AbstractBaseServiceImpl<MusicAlbumArtistMapper, MusicAlbumArtistDO, MusicAlbumArtistDTO> implements MusicAlbumArtistService {

    MusicAlbumArtistConvert convert = MusicAlbumArtistConvert.INSTANCE;

    @Override
    protected Wrapper<MusicAlbumArtistDO> genQueryWrapper(MusicAlbumArtistDTO dto) {
        LambdaQueryWrapper<MusicAlbumArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getArtistId()), MusicAlbumArtistDO::getArtistId, dto.getArtistId());
        query.eq(Objects.nonNull(dto.getAlbumId()), MusicAlbumArtistDO::getAlbumId, dto.getAlbumId());
        return query;
    }

    @Override
    public MusicAlbumArtistDTO convertToDTO(MusicAlbumArtistDO musicMusicAlbumArtistDO) {
        return convert.convert(musicMusicAlbumArtistDO);
    }

    @Override
    public MusicAlbumArtistDO convertToDO(MusicAlbumArtistDTO musicMusicAlbumArtistDTO) {
        return convert.convertToDO(musicMusicAlbumArtistDTO);
    }

    @Override
    public List<MusicAlbumArtistDTO> listByAlbumId(String albumId) {
        Validate.notEmpty(albumId);
        MusicAlbumArtistDTO param = new MusicAlbumArtistDTO();
        param.setAlbumId(albumId);
        return list(param);
    }

    @Override
    public List<MusicAlbumArtistDTO> listByArtistId(String artistId) {
        Validate.notEmpty(artistId);
        MusicAlbumArtistDTO param = new MusicAlbumArtistDTO();
        param.setArtistId(artistId);
        return list(param);
    }

    @Override
    public MusicAlbumArtistDTO findByArtistIdAndAlbumId(String artistId, String albumId) {
        Validate.notEmpty(artistId);
        Validate.notEmpty(albumId);
        MusicAlbumArtistDTO param = new MusicAlbumArtistDTO();
        param.setArtistId(artistId);
        param.setAlbumId(albumId);
        return find(param);
    }

    @Override
    public MusicAlbumArtistDTO insertByArtistIdAndAlbumId(String artistId, String albumId) {
        MusicAlbumArtistDTO musicMusicAlbumArtistDTO = new MusicAlbumArtistDTO();
        musicMusicAlbumArtistDTO.setArtistId(artistId);
        musicMusicAlbumArtistDTO.setAlbumId(albumId);
        return insert(musicMusicAlbumArtistDTO);
    }

    @Override
    @Transactional
    public boolean deleteByAlbumId(String albumId) {
        Validate.notEmpty(albumId);
        MusicAlbumArtistDTO param = new MusicAlbumArtistDTO();
        param.setAlbumId(albumId);
        return delete(param);
    }
}