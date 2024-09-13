package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.ArtistConvert;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.dto.ArtistDTO;
import cc.onelooker.kaleido.entity.ArtistDO;
import cc.onelooker.kaleido.mapper.ArtistMapper;
import cc.onelooker.kaleido.service.MusicAlbumArtistService;
import cc.onelooker.kaleido.service.ArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean updateNeteaseId(String id, String neteaseId) {
        ArtistDO artistDO = new ArtistDO();
        artistDO.setId(id);
        artistDO.setNeteaseId(neteaseId);
        return SqlHelper.retBool(baseMapper.updateById(artistDO));
    }
}