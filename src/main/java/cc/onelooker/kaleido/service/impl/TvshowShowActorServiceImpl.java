package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowShowActorConvert;
import cc.onelooker.kaleido.dto.TvshowShowActorDTO;
import cc.onelooker.kaleido.entity.TvshowShowActorDO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.mapper.TvshowShowActorMapper;
import cc.onelooker.kaleido.service.TvshowShowActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 剧集演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowShowActorServiceImpl extends AbstractBaseServiceImpl<TvshowShowActorMapper, TvshowShowActorDO, TvshowShowActorDTO> implements TvshowShowActorService {

    TvshowShowActorConvert convert = TvshowShowActorConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowShowActorDO> genQueryWrapper(TvshowShowActorDTO dto) {
        LambdaQueryWrapper<TvshowShowActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getShowId()), TvshowShowActorDO::getShowId, dto.getShowId());
        query.eq(Objects.nonNull(dto.getActorId()), TvshowShowActorDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), TvshowShowActorDO::getRole, dto.getRole());
        return query;
    }

    @Override
    public TvshowShowActorDTO convertToDTO(TvshowShowActorDO tvshowShowActorDO) {
        return convert.convert(tvshowShowActorDO);
    }

    @Override
    public TvshowShowActorDO convertToDO(TvshowShowActorDTO tvshowShowActorDTO) {
        return convert.convertToDO(tvshowShowActorDTO);
    }

    @Override
    public TvshowShowActorDTO findByShowIdAndActorId(String showId, String actorId) {
        Validate.notNull(showId);
        Validate.notNull(actorId);
        TvshowShowActorDTO param = new TvshowShowActorDTO();
        param.setShowId(showId);
        param.setActorId(actorId);
        return find(param);
    }

    @Override
    public TvshowShowActorDTO insert(String showId, String actorId, ActorRole actorRole) {
        TvshowShowActorDTO dto = new TvshowShowActorDTO();
        dto.setShowId(showId);
        dto.setActorId(actorId);
        dto.setRole(actorRole.name());
        return insert(dto);
    }

    @Override
    public List<TvshowShowActorDTO> listByShowId(String showId) {
        Validate.notNull(showId);
        TvshowShowActorDTO param = new TvshowShowActorDTO();
        param.setShowId(showId);
        return list(param);
    }

    @Override
    @Transactional
    public void deleteByShowIdAndRole(String showId, ActorRole actorRole) {
        Validate.notNull(showId);
        Validate.notNull(actorRole);
        TvshowShowActorDTO param = new TvshowShowActorDTO();
        param.setShowId(showId);
        param.setRole(actorRole.name());
        delete(param);
    }
}