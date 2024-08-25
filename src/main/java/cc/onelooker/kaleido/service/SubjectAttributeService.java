package cc.onelooker.kaleido.service;

import cc.onelooker.kaleido.dto.SubjectAttributeDTO;
import cc.onelooker.kaleido.enums.AttributeType;
import com.zjjcnt.common.core.service.IBaseService;

import java.util.List;

/**
 * 项目属性Service
 *
 * @author cyetstar
 * @date 2024-03-12 17:48:21
 */
public interface SubjectAttributeService extends IBaseService<SubjectAttributeDTO> {

    void deleteBySubjectIdAndAttributeType(String subjectId, AttributeType attributeType);

    void deleteBySubjectId(String subjectId);

    void insert(String subjectId, String attributeId);

    List<SubjectAttributeDTO> listBySubjectIdAndAttributeType(String subjectId, AttributeType attributeType);

    List<SubjectAttributeDTO> listByAttributeValueAndAttributeType(String attributeValue, AttributeType attributeType);

    void updateAttributeIds(List<String> attributeIdList, String subjectId, AttributeType attributeType);

    void updateAttribute(List<String> attributeValueList, String subjectId, AttributeType attributeType);
}