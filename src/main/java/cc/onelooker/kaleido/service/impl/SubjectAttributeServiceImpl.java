package cc.onelooker.kaleido.service.impl;

import cc.onelooker.kaleido.convert.SubjectAttributeConvert;
import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.entity.SubjectAttributeDO;
import cc.onelooker.kaleido.enums.AttributeType;
import cc.onelooker.kaleido.mapper.SubjectAttributeMapper;
import cc.onelooker.kaleido.service.SubjectAttributeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjjcnt.common.core.service.impl.AbstractBaseServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
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
public class SubjectAttributeServiceImpl extends AbstractBaseServiceImpl<SubjectAttributeMapper, SubjectAttributeDO, SubjectAttributeDTO> implements SubjectAttributeService {

    SubjectAttributeConvert convert = SubjectAttributeConvert.INSTANCE;

    @Override
    protected Wrapper<SubjectAttributeDO> genQueryWrapper(SubjectAttributeDTO dto) {
        LambdaQueryWrapper<SubjectAttributeDO> query = new LambdaQueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(dto.getAttributeId()), SubjectAttributeDO::getAttributeId, dto.getAttributeId());
        query.eq(StringUtils.isNotEmpty(dto.getSubjectId()), SubjectAttributeDO::getSubjectId, dto.getSubjectId());
        query.in(CollectionUtils.isNotEmpty(dto.getSubjectIdList()), SubjectAttributeDO::getSubjectId, dto.getSubjectIdList());
        return query;
    }

    @Override
    public SubjectAttributeDTO convertToDTO(SubjectAttributeDO subjectAttributeDO) {
        return convert.convert(subjectAttributeDO);
    }

    @Override
    public SubjectAttributeDO convertToDO(SubjectAttributeDTO subjectAttributeDTO) {
        return convert.convertToDO(subjectAttributeDTO);
    }

    @Override
    @Transactional
    public void deleteBySubjectIdAndAttributeType(String subjectId, AttributeType type) {
        baseMapper.deleteBySubjectIdAndAttributeType(subjectId, type.name());
    }

    @Override
    @Transactional
    public void insert(String subjectId, String attributeId) {
        SubjectAttributeDTO dto = new SubjectAttributeDTO();
        dto.setSubjectId(subjectId);
        dto.setAttributeId(attributeId);
        insert(dto);
    }

    @Override
    public List<SubjectAttributeDTO> listBySubjectIdAndAttributeType(String subjectId, AttributeType type) {
        List<SubjectAttributeDO> subjectAttributeDOList = baseMapper.listBySubjectIdAndAttributeType(subjectId, type.name());
        return convertToDTO(subjectAttributeDOList);
    }

}