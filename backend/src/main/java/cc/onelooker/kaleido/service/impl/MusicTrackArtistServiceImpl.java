package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.MusicTrackArtistConvert;
import cc.onelooker.kaleido.dto.MusicTrackArtistDTO;
import cc.onelooker.kaleido.entity.MusicTrackArtistDO;
import cc.onelooker.kaleido.mapper.MusicTrackArtistMapper;
import cc.onelooker.kaleido.service.MusicTrackArtistService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
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
public class MusicTrackArtistServiceImpl extends AbstractBaseServiceImpl<MusicTrackArtistMapper, MusicTrackArtistDO, MusicTrackArtistDTO> implements MusicTrackArtistService {

    MusicTrackArtistConvert convert = MusicTrackArtistConvert.INSTANCE;

    @Override
    protected Wrapper<MusicTrackArtistDO> genQueryWrapper(MusicTrackArtistDTO dto) {
        LambdaQueryWrapper<MusicTrackArtistDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getArtistId()), MusicTrackArtistDO::getArtistId, dto.getArtistId());
        query.eq(Objects.nonNull(dto.getTrackId()), MusicTrackArtistDO::getTrackId, dto.getTrackId());
        query.in(CollectionUtils.isNotEmpty(dto.getTrackIdList()), MusicTrackArtistDO::getTrackId, dto.getTrackIdList());
        return query;
    }

    @Override
    public MusicTrackArtistDTO convertToDTO(MusicTrackArtistDO musicMusicTrackArtistDO) {
        return convert.convert(musicMusicTrackArtistDO);
    }

    @Override
    public MusicTrackArtistDO convertToDO(MusicTrackArtistDTO musicMusicTrackArtistDTO) {
        return convert.convertToDO(musicMusicTrackArtistDTO);
    }

    @Override
    public List<MusicTrackArtistDTO> listByTrackId(String trackId) {
        Validate.notEmpty(trackId);
        MusicTrackArtistDTO param = new MusicTrackArtistDTO();
        param.setTrackId(trackId);
        return list(param);
    }

    @Override
    public List<MusicTrackArtistDTO> listByArtistId(String artistId) {
        Validate.notEmpty(artistId);
        MusicTrackArtistDTO param = new MusicTrackArtistDTO();
        param.setArtistId(artistId);
        return list(param);
    }

    @Override
    @Transactional
    public boolean deleteByTrackId(String trackId) {
        Validate.notEmpty(trackId);
        MusicTrackArtistDTO param = new MusicTrackArtistDTO();
        param.setTrackId(trackId);
        return delete(param);
    }

    @Override
    public List<MusicTrackArtistDTO> listByTrackIdList(List<String> trackIdList) {
        Validate.notEmpty(trackIdList);
        MusicTrackArtistDTO param = new MusicTrackArtistDTO();
        param.setTrackIdList(trackIdList);
        return list(param);
    }
}