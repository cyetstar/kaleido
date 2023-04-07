package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.CountryService;
import cc.onelooker.kaleido.entity.business.CountryDO;
import cc.onelooker.kaleido.dto.business.CountryDTO;
import cc.onelooker.kaleido.convert.business.CountryConvert;
import cc.onelooker.kaleido.mapper.business.CountryMapper;

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
public class CountryServiceImpl extends AbstractBaseServiceImpl<CountryMapper, CountryDO, CountryDTO> implements CountryService {

    CountryConvert convert = CountryConvert.INSTANCE;

    @Override
    protected Wrapper<CountryDO> genQueryWrapper(CountryDTO dto) {
        LambdaQueryWrapper<CountryDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getValue()), CountryDO::getValue, dto.getValue());
        return query;
    }

    @Override
    public CountryDTO convertToDTO(CountryDO countryDO) {
        return convert.convert(countryDO);
    }

    @Override
    public CountryDO convertToDO(CountryDTO countryDTO) {
        return convert.convertToDO(countryDTO);
    }
}