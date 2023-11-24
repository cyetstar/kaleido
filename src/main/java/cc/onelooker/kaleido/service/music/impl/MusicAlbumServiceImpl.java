package cc.onelooker.kaleido.service.music.impl;

import cc.onelooker.kaleido.convert.music.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.music.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.music.MusicArtistAlbumDTO;
import cc.onelooker.kaleido.entity.music.MusicAlbumDO;
import cc.onelooker.kaleido.mapper.music.MusicAlbumMapper;
import cc.onelooker.kaleido.service.music.MusicAlbumService;
import cc.onelooker.kaleido.service.music.MusicArtistAlbumService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 专辑ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicAlbumServiceImpl extends AbstractBaseServiceImpl<MusicAlbumMapper, MusicAlbumDO, MusicAlbumDTO> implements MusicAlbumService {

    MusicAlbumConvert convert = MusicAlbumConvert.INSTANCE;

    @Autowired
    private MusicArtistAlbumService musicArtistAlbumService;

    @Override
    protected Wrapper<MusicAlbumDO> genQueryWrapper(MusicAlbumDTO dto) {
        LambdaQueryWrapper<MusicAlbumDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicAlbumDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexId()), MusicAlbumDO::getPlexId, dto.getPlexId());
        query.eq(StringUtils.isNotEmpty(dto.getPlexThumb()), MusicAlbumDO::getPlexThumb, dto.getPlexThumb());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), MusicAlbumDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MusicAlbumDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getArtists()), MusicAlbumDO::getArtists, dto.getArtists());
        query.eq(Objects.nonNull(dto.getSummary()), MusicAlbumDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getType()), MusicAlbumDO::getType, dto.getType());
        query.eq(StringUtils.isNotEmpty(dto.getGenre()), MusicAlbumDO::getGenre, dto.getGenre());
        query.eq(StringUtils.isNotEmpty(dto.getReleaseCountry()), MusicAlbumDO::getReleaseCountry, dto.getReleaseCountry());
        query.eq(StringUtils.isNotEmpty(dto.getDate()), MusicAlbumDO::getDate, dto.getDate());
        query.eq(StringUtils.isNotEmpty(dto.getLabel()), MusicAlbumDO::getLabel, dto.getLabel());
        query.eq(StringUtils.isNotEmpty(dto.getReleaseDate()), MusicAlbumDO::getReleaseDate, dto.getReleaseDate());
        query.eq(Objects.nonNull(dto.getTotalDiscs()), MusicAlbumDO::getTotalDiscs, dto.getTotalDiscs());
        query.eq(Objects.nonNull(dto.getTotalTracks()), MusicAlbumDO::getTotalTracks, dto.getTotalTracks());
        query.eq(StringUtils.isNotEmpty(dto.getMedia()), MusicAlbumDO::getMedia, dto.getMedia());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), MusicAlbumDO::getPath, dto.getPath());
        return query;
    }

    @Override
    public MusicAlbumDTO convertToDTO(MusicAlbumDO musicAlbumDO) {
        return convert.convert(musicAlbumDO);
    }

    @Override
    public MusicAlbumDO convertToDO(MusicAlbumDTO musicAlbumDTO) {
        return convert.convertToDO(musicAlbumDTO);
    }

    @Override
    public MusicAlbumDTO findByPlexId(String plexId) {
        MusicAlbumDTO param = new MusicAlbumDTO();
        param.setPlexId(plexId);
        return find(param);
    }

    @Override
    public List<MusicAlbumDTO> listByArtistId(Long artistId) {
        Validate.notNull(artistId);
        List<MusicArtistAlbumDTO> musicArtistAlbumDTOList = musicArtistAlbumService.listByArtistId(artistId);
        List<MusicAlbumDTO> musicAlbumDTOList = Lists.newArrayList();
        for (MusicArtistAlbumDTO musicArtistAlbumDTO : musicArtistAlbumDTOList) {
            MusicAlbumDTO musicAlbumDTO = findById(musicArtistAlbumDTO.getAlbumId());
            if (musicAlbumDTO != null) {
                musicAlbumDTOList.add(musicAlbumDTO);
            }
        }
        return musicAlbumDTOList;
    }

    @Override
    public String findMaxXgsj() {
        return baseMapper.findMaxXgsj();
    }
}