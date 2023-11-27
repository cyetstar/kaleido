package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.convert.tvshow.TvshowEpisodeActorConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowEpisodeActorDO;
import cc.onelooker.kaleido.mapper.tvshow.TvshowEpisodeActorMapper;
import cc.onelooker.kaleido.service.tvshow.TvshowEpisodeActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 单集演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowEpisodeActorServiceImpl extends AbstractBaseServiceImpl<TvshowEpisodeActorMapper, TvshowEpisodeActorDO, TvshowEpisodeActorDTO> implements TvshowEpisodeActorService {

    TvshowEpisodeActorConvert convert = TvshowEpisodeActorConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowEpisodeActorDO> genQueryWrapper(TvshowEpisodeActorDTO dto) {
        LambdaQueryWrapper<TvshowEpisodeActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getEpisodeId()), TvshowEpisodeActorDO::getEpisodeId, dto.getEpisodeId());
        query.eq(Objects.nonNull(dto.getActorId()), TvshowEpisodeActorDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), TvshowEpisodeActorDO::getRole, dto.getRole());
        return query;
    }

    @Override
    public TvshowEpisodeActorDTO convertToDTO(TvshowEpisodeActorDO tvshowEpisodeActorDO) {
        return convert.convert(tvshowEpisodeActorDO);
    }

    @Override
    public TvshowEpisodeActorDO convertToDO(TvshowEpisodeActorDTO tvshowEpisodeActorDTO) {
        return convert.convertToDO(tvshowEpisodeActorDTO);
    }

    @Override
    public TvshowEpisodeActorDTO findByEpisodeIdAndActorId(Long episodeId, Long actorId) {
        Validate.notNull(episodeId);
        Validate.notNull(actorId);
        TvshowEpisodeActorDTO param = new TvshowEpisodeActorDTO();
        param.setEpisodeId(episodeId);
        param.setActorId(actorId);
        return find(param);
    }

    @Override
    public TvshowEpisodeActorDTO insert(Long episodeId, Long actorId, String role) {
        TvshowEpisodeActorDTO tvshowEpisodeActorDTO = new TvshowEpisodeActorDTO();
        tvshowEpisodeActorDTO.setEpisodeId(episodeId);
        tvshowEpisodeActorDTO.setActorId(actorId);
        tvshowEpisodeActorDTO.setRole(role);
        return insert(tvshowEpisodeActorDTO);
    }
}