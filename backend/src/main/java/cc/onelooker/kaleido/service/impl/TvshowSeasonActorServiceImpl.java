package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.TvshowSeasonActorConvert;
import cc.onelooker.kaleido.dto.TvshowSeasonActorDTO;
import cc.onelooker.kaleido.entity.TvshowSeasonActorDO;
import cc.onelooker.kaleido.enums.ActorRole;
import cc.onelooker.kaleido.mapper.TvshowSeasonActorMapper;
import cc.onelooker.kaleido.service.TvshowSeasonActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 剧集演职员关联表ServiceImpl
 *
 * @author cyetstar
 * @date 2023-11-26 23:27:03
 */
@Service
public class TvshowSeasonActorServiceImpl extends AbstractBaseServiceImpl<TvshowSeasonActorMapper, TvshowSeasonActorDO, TvshowSeasonActorDTO> implements TvshowSeasonActorService {

    TvshowSeasonActorConvert convert = TvshowSeasonActorConvert.INSTANCE;

    @Override
    protected Wrapper<TvshowSeasonActorDO> genQueryWrapper(TvshowSeasonActorDTO dto) {
        LambdaQueryWrapper<TvshowSeasonActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getSeasonId()), TvshowSeasonActorDO::getSeasonId, dto.getSeasonId());
        query.eq(Objects.nonNull(dto.getActorId()), TvshowSeasonActorDO::getActorId, dto.getActorId());
        query.eq(StringUtils.isNotEmpty(dto.getRole()), TvshowSeasonActorDO::getRole, dto.getRole());
        return query;
    }

    @Override
    public TvshowSeasonActorDTO convertToDTO(TvshowSeasonActorDO tvshowShowActorDO) {
        return convert.convert(tvshowShowActorDO);
    }

    @Override
    public TvshowSeasonActorDO convertToDO(TvshowSeasonActorDTO tvshowShowActorDTO) {
        return convert.convertToDO(tvshowShowActorDTO);
    }

    @Override
    public TvshowSeasonActorDTO insert(String seasonId, String actorId, ActorRole actorRole) {
        TvshowSeasonActorDTO dto = new TvshowSeasonActorDTO();
        dto.setSeasonId(seasonId);
        dto.setActorId(actorId);
        dto.setRole(actorRole.name());
        return insert(dto);
    }

    @Override
    public List<TvshowSeasonActorDTO> listBySeasonId(String seasonId) {
        Validate.notNull(seasonId);
        TvshowSeasonActorDTO param = new TvshowSeasonActorDTO();
        param.setSeasonId(seasonId);
        return list(param);
    }

}