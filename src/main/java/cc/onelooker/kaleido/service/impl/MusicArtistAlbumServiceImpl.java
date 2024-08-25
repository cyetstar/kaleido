package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MusicArtistAlbumConvert;
import cc.onelooker.kaleido.dto.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.entity.MusicArtistAlbumDO;
import cc.onelooker.kaleido.mapper.MusicArtistAlbumMapper;
import cc.onelooker.kaleido.service.MusicArtistAlbumService;
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
    public List<MusicArtistAlbumDTO> listByAlbumId(String albumId) {
        Validate.notEmpty(albumId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setAlbumId(albumId);
        return list(param);
    }

    @Override
    public List<MusicArtistAlbumDTO> listByArtistId(String artistId) {
        Validate.notEmpty(artistId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setArtistId(artistId);
        return list(param);
    }

    @Override
    public MusicArtistAlbumDTO findByArtistIdAndAlbumId(String artistId, String albumId) {
        Validate.notEmpty(artistId);
        Validate.notEmpty(albumId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setArtistId(artistId);
        param.setAlbumId(albumId);
        return find(param);
    }

    @Override
    public MusicArtistAlbumDTO insertByArtistIdAndAlbumId(String artistId, String albumId) {
        MusicArtistAlbumDTO musicArtistAlbumDTO = new MusicArtistAlbumDTO();
        musicArtistAlbumDTO.setArtistId(artistId);
        musicArtistAlbumDTO.setAlbumId(albumId);
        return insert(musicArtistAlbumDTO);
    }

    @Override
    public boolean deleteByAlbumId(String albumId) {
        Validate.notEmpty(albumId);
        MusicArtistAlbumDTO param = new MusicArtistAlbumDTO();
        param.setAlbumId(albumId);
        return delete(param);
    }
}