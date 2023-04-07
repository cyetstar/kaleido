package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.LanguageService;
import cc.onelooker.kaleido.entity.business.LanguageDO;
import cc.onelooker.kaleido.dto.business.LanguageDTO;
import cc.onelooker.kaleido.convert.business.LanguageConvert;
import cc.onelooker.kaleido.mapper.business.LanguageMapper;

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
public class LanguageServiceImpl extends AbstractBaseServiceImpl<LanguageMapper, LanguageDO, LanguageDTO> implements LanguageService {

    LanguageConvert convert = LanguageConvert.INSTANCE;

    @Override
    protected Wrapper<LanguageDO> genQueryWrapper(LanguageDTO dto) {
        LambdaQueryWrapper<LanguageDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getValue()), LanguageDO::getValue, dto.getValue());
        return query;
    }

    @Override
    public LanguageDTO convertToDTO(LanguageDO languageDO) {
        return convert.convert(languageDO);
    }

    @Override
    public LanguageDO convertToDO(LanguageDTO languageDTO) {
        return convert.convertToDO(languageDTO);
    }
}