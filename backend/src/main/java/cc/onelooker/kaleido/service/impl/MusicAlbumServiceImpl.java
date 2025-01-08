package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MusicAlbumConvert;
import cc.onelooker.kaleido.dto.MusicAlbumArtistDTO;
import cc.onelooker.kaleido.dto.MusicAlbumDTO;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.entity.MusicAlbumDO;
import cc.onelooker.kaleido.mapper.MusicAlbumMapper;
import cc.onelooker.kaleido.service.MusicAlbumArtistService;
import cc.onelooker.kaleido.service.MusicAlbumService;
import cc.onelooker.kaleido.service.MusicTrackService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 专辑ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-25 22:16:58
 */
@Service
public class MusicAlbumServiceImpl extends AbstractBaseServiceImpl<MusicAlbumMapper, MusicAlbumDO, MusicAlbumDTO>
        implements MusicAlbumService {

    MusicAlbumConvert convert = MusicAlbumConvert.INSTANCE;

    @Autowired
    private MusicAlbumArtistService musicMusicAlbumArtistService;

    @Autowired
    private MusicTrackService musicTrackService;

    @Override
    protected Wrapper<MusicAlbumDO> genQueryWrapper(MusicAlbumDTO dto) {
        LambdaQueryWrapper<MusicAlbumDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getId()), MusicAlbumDO::getId, dto.getId());
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicAlbumDO::getMusicbrainzId,
                dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), MusicAlbumDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MusicAlbumDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getArtists()), MusicAlbumDO::getArtists, dto.getArtists());
        query.eq(Objects.nonNull(dto.getSummary()), MusicAlbumDO::getSummary, dto.getSummary());
        query.eq(StringUtils.isNotEmpty(dto.getType()), MusicAlbumDO::getType, dto.getType());
        query.eq(StringUtils.isNotEmpty(dto.getGenre()), MusicAlbumDO::getGenre, dto.getGenre());
        query.eq(StringUtils.isNotEmpty(dto.getReleaseCountry()), MusicAlbumDO::getReleaseCountry,
                dto.getReleaseCountry());
        query.eq(StringUtils.isNotEmpty(dto.getLabel()), MusicAlbumDO::getLabel, dto.getLabel());
        query.eq(Objects.nonNull(dto.getTotalDiscs()), MusicAlbumDO::getTotalDiscs, dto.getTotalDiscs());
        query.eq(Objects.nonNull(dto.getTotalTracks()), MusicAlbumDO::getTotalTracks, dto.getTotalTracks());
        query.eq(StringUtils.isNotEmpty(dto.getMedia()), MusicAlbumDO::getMedia, dto.getMedia());
        query.eq(StringUtils.isNotEmpty(dto.getYear()), MusicAlbumDO::getYear, dto.getYear());
        query.eq(StringUtils.isNotEmpty(dto.getOriginallyAvailableAt()), MusicAlbumDO::getOriginallyAvailableAt,
                dto.getOriginallyAvailableAt());
        query.eq(StringUtils.isNotEmpty(dto.getThumb()), MusicAlbumDO::getThumb, dto.getThumb());
        query.eq(Objects.nonNull(dto.getAddedAt()), MusicAlbumDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MusicAlbumDO::getUpdatedAt, dto.getUpdatedAt());
        if (StringUtils.isNotEmpty(dto.getKeyword())) {
            List<String> idList = Lists.newArrayList();
            idList.addAll(listAlbumIdByTrackKeyword(dto.getKeyword()));
            if (CollectionUtils.isNotEmpty(idList)) {
                query.and(q -> q.in(MusicAlbumDO::getId, idList).or().like(MusicAlbumDO::getTitle, dto.getKeyword())
                        .or().like(MusicAlbumDO::getArtists, dto.getKeyword()));
            } else {
                query.and(q -> q.like(MusicAlbumDO::getTitle, dto.getKeyword()).or().like(MusicAlbumDO::getArtists,
                        dto.getKeyword()));
            }
        }
        query.likeRight(StringUtils.length(dto.getDecade()) > 3, MusicAlbumDO::getYear,
                StringUtils.substring(dto.getDecade(), 0, 3));
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

    private List<String> listAlbumIdByTrackKeyword(String title) {
        List<MusicTrackDTO> musicTrackDTOList = musicTrackService.listByKeyword(title);
        return musicTrackDTOList.stream().map(MusicTrackDTO::getAlbumId).collect(Collectors.toList());
    }

    @Override
    public List<MusicAlbumDTO> listByArtistId(String artistId) {
        Validate.notNull(artistId);
        List<MusicAlbumArtistDTO> musicMusicAlbumArtistDTOList = musicMusicAlbumArtistService.listByArtistId(artistId);
        List<MusicAlbumDTO> musicAlbumDTOList = Lists.newArrayList();
        for (MusicAlbumArtistDTO musicMusicAlbumArtistDTO : musicMusicAlbumArtistDTOList) {
            MusicAlbumDTO musicAlbumDTO = findById(musicMusicAlbumArtistDTO.getAlbumId());
            if (musicAlbumDTO != null) {
                musicAlbumDTOList.add(musicAlbumDTO);
            }
        }
        return musicAlbumDTOList;
    }

    @Override
    public Long findMaxUpdatedAt() {
        return baseMapper.findMaxUpdatedAt();
    }

    @Override
    @Transactional
    public boolean deleteById(Serializable id) {
        musicMusicAlbumArtistService.deleteByAlbumId((String) id);
        musicTrackService.deleteByAlbumId((String) id);
        return super.deleteById(id);
    }
}