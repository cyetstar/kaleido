package cc.onelooker.kaleido.service.tvshow.impl;

import cc.onelooker.kaleido.convert.tvshow.TvshowShowActorConvert;
import cc.onelooker.kaleido.dto.tvshow.TvshowShowActorDTO;
import cc.onelooker.kaleido.entity.tvshow.TvshowShowActorDO;
import cc.onelooker.kaleido.mapper.tvshow.TvshowShowActorMapper;
import cc.onelooker.kaleido.service.tvshow.TvshowShowActorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

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
    public TvshowShowActorDTO findByShowIdAndActorId(Long showId, Long actorId) {
        Validate.notNull(showId);
        Validate.notNull(actorId);
        TvshowShowActorDTO param = new TvshowShowActorDTO();
        param.setShowId(showId);
        param.setActorId(actorId);
        return find(param);
    }

    @Override
    public TvshowShowActorDTO insert(Long showId, Long actorId, String role) {
        TvshowShowActorDTO dto = new TvshowShowActorDTO();
        dto.setShowId(showId);
        dto.setActorId(actorId);
        dto.setRole(role);
        return insert(dto);
    }
}