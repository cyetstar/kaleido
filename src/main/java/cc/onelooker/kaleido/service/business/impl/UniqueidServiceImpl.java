package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.UniqueidService;
import cc.onelooker.kaleido.entity.business.UniqueidDO;
import cc.onelooker.kaleido.dto.business.UniqueidDTO;
import cc.onelooker.kaleido.convert.business.UniqueidConvert;
import cc.onelooker.kaleido.mapper.business.UniqueidMapper;

import org.apache.commons.lang3.StringUtils;
import java.util.*;

import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;

/**
 * ServiceImpl
 *
 * @author cyetstar
 * @date 2023-04-06 13:32:12
 */
@Service
public class UniqueidServiceImpl extends AbstractBaseServiceImpl<UniqueidMapper, UniqueidDO, UniqueidDTO> implements UniqueidService {

    UniqueidConvert convert = UniqueidConvert.INSTANCE;

    @Override
    protected Wrapper<UniqueidDO> genQueryWrapper(UniqueidDTO dto) {
        LambdaQueryWrapper<UniqueidDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getDef()), UniqueidDO::getDef, dto.getDef());
        query.eq(Objects.nonNull(dto.getType()), UniqueidDO::getType, dto.getType());
        query.eq(Objects.nonNull(dto.getValue()), UniqueidDO::getValue, dto.getValue());
        query.eq(Objects.nonNull(dto.getMovieId()), UniqueidDO::getMovieId, dto.getMovieId());
        return query;
    }

    @Override
    public UniqueidDTO convertToDTO(UniqueidDO uniqueidDO) {
        return convert.convert(uniqueidDO);
    }

    @Override
    public UniqueidDO convertToDO(UniqueidDTO uniqueidDTO) {
        return convert.convertToDO(uniqueidDTO);
    }
}