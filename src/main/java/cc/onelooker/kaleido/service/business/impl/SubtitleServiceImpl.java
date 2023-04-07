package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.SubtitleService;
import cc.onelooker.kaleido.entity.business.SubtitleDO;
import cc.onelooker.kaleido.dto.business.SubtitleDTO;
import cc.onelooker.kaleido.convert.business.SubtitleConvert;
import cc.onelooker.kaleido.mapper.business.SubtitleMapper;

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
public class SubtitleServiceImpl extends AbstractBaseServiceImpl<SubtitleMapper, SubtitleDO, SubtitleDTO> implements SubtitleService {

    SubtitleConvert convert = SubtitleConvert.INSTANCE;

    @Override
    protected Wrapper<SubtitleDO> genQueryWrapper(SubtitleDTO dto) {
        LambdaQueryWrapper<SubtitleDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getLanguage()), SubtitleDO::getLanguage, dto.getLanguage());
        query.eq(Objects.nonNull(dto.getFileinfoId()), SubtitleDO::getFileinfoId, dto.getFileinfoId());
        return query;
    }

    @Override
    public SubtitleDTO convertToDTO(SubtitleDO subtitleDO) {
        return convert.convert(subtitleDO);
    }

    @Override
    public SubtitleDO convertToDO(SubtitleDTO subtitleDTO) {
        return convert.convertToDO(subtitleDTO);
    }
}