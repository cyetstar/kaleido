package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.AkaService;
import cc.onelooker.kaleido.entity.business.AkaDO;
import cc.onelooker.kaleido.dto.business.AkaDTO;
import cc.onelooker.kaleido.convert.business.AkaConvert;
import cc.onelooker.kaleido.mapper.business.AkaMapper;

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
public class AkaServiceImpl extends AbstractBaseServiceImpl<AkaMapper, AkaDO, AkaDTO> implements AkaService {

    AkaConvert convert = AkaConvert.INSTANCE;

    @Override
    protected Wrapper<AkaDO> genQueryWrapper(AkaDTO dto) {
        LambdaQueryWrapper<AkaDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getTitle()), AkaDO::getTitle, dto.getTitle());
        query.eq(Objects.nonNull(dto.getMovieId()), AkaDO::getMovieId, dto.getMovieId());
        return query;
    }

    @Override
    public AkaDTO convertToDTO(AkaDO akaDO) {
        return convert.convert(akaDO);
    }

    @Override
    public AkaDO convertToDO(AkaDTO akaDTO) {
        return convert.convertToDO(akaDTO);
    }
}