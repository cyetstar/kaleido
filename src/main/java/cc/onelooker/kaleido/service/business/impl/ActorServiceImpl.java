package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.ActorService;
import cc.onelooker.kaleido.entity.business.ActorDO;
import cc.onelooker.kaleido.dto.business.ActorDTO;
import cc.onelooker.kaleido.convert.business.ActorConvert;
import cc.onelooker.kaleido.mapper.business.ActorMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.String;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class ActorServiceImpl extends AbstractBaseServiceImpl<ActorMapper, ActorDO, ActorDTO> implements ActorService {

    ActorConvert convert = ActorConvert.INSTANCE;

    @Override
    protected Wrapper<ActorDO> genQueryWrapper(ActorDTO dto) {
        LambdaQueryWrapper<ActorDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCategory()), ActorDO::getCategory, dto.getCategory());
        query.eq(Objects.nonNull(dto.getDoubanId()), ActorDO::getDoubanId, dto.getDoubanId());
        query.eq(Objects.nonNull(dto.getName()), ActorDO::getName, dto.getName());
        query.eq(Objects.nonNull(dto.getOrders()), ActorDO::getOrders, dto.getOrders());
        query.eq(Objects.nonNull(dto.getOriginalname()), ActorDO::getOriginalname, dto.getOriginalname());
        query.eq(Objects.nonNull(dto.getProfile()), ActorDO::getProfile, dto.getProfile());
        query.eq(Objects.nonNull(dto.getRole()), ActorDO::getRole, dto.getRole());
        query.eq(Objects.nonNull(dto.getThumb()), ActorDO::getThumb, dto.getThumb());
        return query;
    }

    @Override
    public ActorDTO convertToDTO(ActorDO actorDO) {
        return convert.convert(actorDO);
    }

    @Override
    public ActorDO convertToDO(ActorDTO actorDTO) {
        return convert.convertToDO(actorDTO);
    }
}