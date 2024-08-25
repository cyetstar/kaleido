package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MusicTrackConvert;
import cc.onelooker.kaleido.dto.MusicTrackDTO;
import cc.onelooker.kaleido.entity.MusicTrackDO;
import cc.onelooker.kaleido.mapper.MusicTrackMapper;
import cc.onelooker.kaleido.service.MusicTrackService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 曲目ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-20 22:35:26
 */
@Service
public class MusicTrackServiceImpl extends AbstractBaseServiceImpl<MusicTrackMapper, MusicTrackDO, MusicTrackDTO> implements MusicTrackService {

    MusicTrackConvert convert = MusicTrackConvert.INSTANCE;

    @Override
    protected Wrapper<MusicTrackDO> genQueryWrapper(MusicTrackDTO dto) {
        LambdaQueryWrapper<MusicTrackDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getMusicbrainzId()), MusicTrackDO::getMusicbrainzId, dto.getMusicbrainzId());
        query.eq(StringUtils.isNotEmpty(dto.getNeteaseId()), MusicTrackDO::getNeteaseId, dto.getNeteaseId());
        query.eq(StringUtils.isNotEmpty(dto.getTitle()), MusicTrackDO::getTitle, dto.getTitle());
        query.eq(StringUtils.isNotEmpty(dto.getArtists()), MusicTrackDO::getArtists, dto.getArtists());
        query.eq(StringUtils.isNotEmpty(dto.getFormat()), MusicTrackDO::getFormat, dto.getFormat());
        query.eq(StringUtils.isNotEmpty(dto.getPath()), MusicTrackDO::getPath, dto.getPath());
        query.eq(Objects.nonNull(dto.getAlbumId()), MusicTrackDO::getAlbumId, dto.getAlbumId());
        query.eq(Objects.nonNull(dto.getDuration()), MusicTrackDO::getDuration, dto.getDuration());
        query.eq(Objects.nonNull(dto.getTrackIndex()), MusicTrackDO::getTrackIndex, dto.getTrackIndex());
        query.eq(Objects.nonNull(dto.getDiscIndex()), MusicTrackDO::getDiscIndex, dto.getDiscIndex());
        query.eq(Objects.nonNull(dto.getAddedAt()), MusicTrackDO::getAddedAt, dto.getAddedAt());
        query.eq(Objects.nonNull(dto.getUpdatedAt()), MusicTrackDO::getUpdatedAt, dto.getUpdatedAt());
        return query;
    }

    @Override
    public MusicTrackDTO convertToDTO(MusicTrackDO musicTrackDO) {
        return convert.convert(musicTrackDO);
    }

    @Override
    public MusicTrackDO convertToDO(MusicTrackDTO musicTrackDTO) {
        return convert.convertToDO(musicTrackDTO);
    }

    @Override
    public List<MusicTrackDTO> listByAlbumId(String albumId) {
        Validate.notNull(albumId);
        MusicTrackDTO param = new MusicTrackDTO();
        param.setAlbumId(albumId);
        LambdaQueryWrapper<MusicTrackDO> wrapper = (LambdaQueryWrapper<MusicTrackDO>) genQueryWrapper(param);
        wrapper.orderByAsc(MusicTrackDO::getDiscIndex, MusicTrackDO::getTrackIndex);
        return convertToDTO(list(wrapper));
    }

    @Override
    public boolean deleteByAlbumId(String albumId) {
        Validate.notNull(albumId);
        MusicTrackDTO param = new MusicTrackDTO();
        param.setAlbumId(albumId);
        return delete(param);
    }

}