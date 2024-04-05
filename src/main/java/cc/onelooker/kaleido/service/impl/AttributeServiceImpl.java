package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.AttributeConvert;
import cc.onelooker.kaleido.dto.AttributeDTO;
import cc.onelooker.kaleido.entity.AttributeDO;
import cc.onelooker.kaleido.mapper.AttributeMapper;
import cc.onelooker.kaleido.service.AttributeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 属性ServiceImpl
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
@Service
public class AttributeServiceImpl extends AbstractBaseServiceImpl<AttributeMapper, AttributeDO, AttributeDTO> implements AttributeService {

    AttributeConvert convert = AttributeConvert.INSTANCE;

    @Override
    protected Wrapper<AttributeDO> genQueryWrapper(AttributeDTO dto) {
        LambdaQueryWrapper<AttributeDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getValue()), AttributeDO::getValue, dto.getValue());
        query.eq(StringUtils.isNotEmpty(dto.getType()), AttributeDO::getType, dto.getType());
        return query;
    }

    @Override
    public AttributeDTO convertToDTO(AttributeDO attributeDO) {
        return convert.convert(attributeDO);
    }

    @Override
    public AttributeDO convertToDO(AttributeDTO attributeDTO) {
        return convert.convertToDO(attributeDTO);
    }

    @Override
    public AttributeDTO findByValueAndType(String value, String type) {
        AttributeDTO param = new AttributeDTO();
        param.setValue(value);
        param.setType(type);
        return find(param);
    }

    @Override
    @Transactional
    public AttributeDTO insert(String value, String type) {
        AttributeDTO attributeDTO = new AttributeDTO();
        attributeDTO.setValue(value);
        attributeDTO.setType(type);
        return insert(attributeDTO);
    }

    @Override
    public List<AttributeDTO> listBySubjectId(String subjectId) {
        List<AttributeDO> attributeDOList = baseMapper.listBySubjectId(subjectId);
        return convertToDTO(attributeDOList);
    }
}