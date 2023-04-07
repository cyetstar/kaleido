package cc.onelooker.kaleido.service.business.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import cc.onelooker.kaleido.service.business.TagService;
import cc.onelooker.kaleido.entity.business.TagDO;
import cc.onelooker.kaleido.dto.business.TagDTO;
import cc.onelooker.kaleido.convert.business.TagConvert;
import cc.onelooker.kaleido.mapper.business.TagMapper;

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
public class TagServiceImpl extends AbstractBaseServiceImpl<TagMapper, TagDO, TagDTO> implements TagService {

    TagConvert convert = TagConvert.INSTANCE;

    @Override
    protected Wrapper<TagDO> genQueryWrapper(TagDTO dto) {
        LambdaQueryWrapper<TagDO> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(dto.getCategory()), TagDO::getCategory, dto.getCategory());
        query.eq(Objects.nonNull(dto.getValue()), TagDO::getValue, dto.getValue());
        return query;
    }

    @Override
    public TagDTO convertToDTO(TagDO tagDO) {
        return convert.convert(tagDO);
    }

    @Override
    public TagDO convertToDO(TagDTO tagDTO) {
        return convert.convertToDO(tagDTO);
    }
}