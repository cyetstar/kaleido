package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowEpisodeActorConvert;
import cc.onelooker.kaleido.dto.TvshowEpisodeActorDTO;
import cc.onelooker.kaleido.entity.TvshowEpisodeActorDO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.mapper.TvshowEpisodeActorMapper;
import cc.onelooker.kaleido.service.TvshowEpisodeActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TvshowEpisodeActorDTO findByEpisodeIdAndActorId(String episodeId, String actorId) {
        Validate.notNull(episodeId);
        Validate.notNull(actorId);
        TvshowEpisodeActorDTO param = new TvshowEpisodeActorDTO();
        param.setEpisodeId(episodeId);
        param.setActorId(actorId);
        return find(param);
    }

    @Override
    public TvshowEpisodeActorDTO insert(String episodeId, String actorId, ActorRole actorRole) {
        TvshowEpisodeActorDTO tvshowEpisodeActorDTO = new TvshowEpisodeActorDTO();
        tvshowEpisodeActorDTO.setEpisodeId(episodeId);
        tvshowEpisodeActorDTO.setActorId(actorId);
        tvshowEpisodeActorDTO.setRole(actorRole.name());
        return insert(tvshowEpisodeActorDTO);
    }

    @Override
    @Transactional
    public void deleteByEpisodeIdAndRole(String episodeId, ActorRole actorRole) {
        Validate.notNull(episodeId);
        Validate.notNull(actorRole);
        TvshowEpisodeActorDTO param = new TvshowEpisodeActorDTO();
        param.setEpisodeId(episodeId);
        param.setRole(actorRole.name());
        delete(param);
    }
}